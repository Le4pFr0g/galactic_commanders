package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import bullets.Bullet;
import entity.Enemy;
import entity.Player;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import pickups.AmmoPU;
import pickups.PickUp;
import pickups.WeaponPU;
import weapons.Gun;

public class Main extends Application
{
	//system controls
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth() * 0.5;
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight() * 0.5;

	//user input
	private Set<KeyCode> keysPressed = new HashSet<>();
	private double mouseX, mouseY;
	private boolean isShooting = false;
	
	//game objects
	private Player player;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<AmmoPU> ammoPUs = new ArrayList<>();
	private ArrayList<WeaponPU> weaponPUs = new ArrayList<>();
	


	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Galactic Commanders");

		StackPane pane = new StackPane();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		canvas.setFocusTraversable(true);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		pane.getChildren().add(canvas);

		this.player = new Player(50, 50, 1000);

		// Game loop using AnimationTimer
		AnimationTimer gameLoop = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update(gc);
			}
		};
		gameLoop.start();

		spawnEnemies();
		addAmmo();
		addWeapons();

		Scene scene = new Scene(pane, WIDTH, HEIGHT);

		// Add key listeners to track pressed keys
		scene.setOnKeyPressed(this::handleKeyPressed);
		scene.setOnKeyReleased(this::handleKeyReleased);

		// Add mouse listeners to track mouse clicks
		scene.setOnMousePressed(this::handleMousePressed);
		scene.setOnMouseDragged(this::handleMouseDragged);
		scene.setOnMouseReleased(this::handleMouseReleased);

		primaryStage.setTitle("Galactic Commanders");
		//primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	private void addAmmo()
	{
//		ammoPUs.add(new AmmoPU(100, 100, 0, 10, Color.MEDIUMPURPLE));
//		ammoPUs.add(new AmmoPU(100, 100, 1, 10, Color.MEDIUMPURPLE));
//		ammoPUs.add(new AmmoPU(100, 100, 2, 10, Color.MEDIUMPURPLE));
		ammoPUs.add(new AmmoPU(100, 100, 3, 10, Color.MEDIUMPURPLE));


	}
	
	private void addWeapons()
	{
//		weaponPUs.add(new WeaponPU(WIDTH/2, HEIGHT/2, 0, 10, Color.rgb(150, 20, 150)));
//		weaponPUs.add(new WeaponPU(WIDTH/3 + 200, HEIGHT/3 + 200, 1, 10, Color.rgb(0, 0, 0)));
//		weaponPUs.add(new WeaponPU(WIDTH/3, HEIGHT/3, 2, 10, Color.rgb(255, 255, 255)));
		weaponPUs.add(new WeaponPU(100, 100, 3, 10, Color.rgb(255, 255, 255)));



		
	}

	private void spawnEnemies()
	{
		Random random = new Random();
		double x = random.nextDouble() * WIDTH;
		double y = random.nextDouble() * HEIGHT;
		this.enemies.add(new Enemy(this.player, x, y, 100, 5, Color.rgb(255, 0, (int)(Math.random()*255))));
		x = random.nextDouble() * WIDTH;
		y = random.nextDouble() * HEIGHT;
		this.enemies.add(new Enemy(this.player, x, y, 100, 5, Color.rgb(255, (int)(Math.random()*255), 0)));
	}

	

	private void update(GraphicsContext gc)
	{
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.TAN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		this.player.render(gc);
		
		for (AmmoPU a : ammoPUs)
		{
			a.checkCollision(player);
			a.render(gc);
		} 
		
		List<WeaponPU> weaponPUsToRemove = new ArrayList<>();
		for (WeaponPU w : weaponPUs)
		{
			if (w.checkCollision(player))
			{
				weaponPUsToRemove.add(w);
			}
			
			w.render(gc);
		}
		weaponPUs.removeAll(weaponPUsToRemove);

		List<Bullet> bulletsToRemove = new ArrayList<>();
		List<Enemy> enemiesToRemove = new ArrayList<>();
		List<Bullet> projectilesToRemove = new ArrayList<>();
		
		//check if any player bullet hits enemy
		for (Gun g : player.getGuns())
		{
			if (g.isPickedUp())
			{
				
				for (Bullet b : g.getBullets())
				{
					if (g.isSplashGun())
					{
						if (b.damageEnemies(enemies));
						bulletsToRemove.add(b);

						
					}
					else
					{
						for (Enemy e : enemies)
						{
							if (b.checkCollision(e))
							{
								System.out.println("HIT ENEMY");
								bulletsToRemove.add(b);
								b.damageEnemy(e);	
			
							}
						}
					}
				}	
			}
			g.getBullets().removeAll(bulletsToRemove);
		}

		//check if enemy projectile hits player
		for (Enemy e : enemies)
		{
			for (Bullet p : e.getProjectiles())
			{
				if (p.checkCollision(player))
				{
					System.out.println("HIT PLAYER");
					player.setHp(player.getHp() - 5);
					projectilesToRemove.add(p);
				}

			}
			e.getProjectiles().removeAll(projectilesToRemove);
			
			//checks if enemy is dead, if so remov them
			if (e.getHp() <= 0)
			{
				enemiesToRemove.add(e);
			}
		}

		
		enemies.removeAll(enemiesToRemove);
		

		for (Enemy e : enemies)
		{
			
			if (player.isAlive())
			{
				e.move(player);
				e.attack(player);
			}
			e.render(gc);
		}
		
		if (player.getEquippedWeapon() != null)
		{
			player.getEquippedWeapon().fireCooldown();
			player.getEquippedWeapon().render(gc, player, Color.LIGHTGRAY);
		}
		
		
		
		if (player.getHp() <= 0)
		{
			player.kill(gc, HEIGHT, WIDTH);

		}
		else
		{
			
			//player movement
			if (keysPressed.contains(KeyCode.W))
			{
				this.player.move(0, -player.getSpeed());
			}
			if (keysPressed.contains(KeyCode.S))
			{
				this.player.move(0, player.getSpeed());
			}
			if (keysPressed.contains(KeyCode.A))
			{
				this.player.move(-player.getSpeed(), 0);
			}
			if (keysPressed.contains(KeyCode.D))
			{
				this.player.move(player.getSpeed(), 0);
			}
			
			//player sprint
			if (keysPressed.contains(KeyCode.SHIFT))
			{
				player.setSpeed(5);
			}
			else
			{
				player.setSpeed(3);
			}
			
			//weapon switching
			if(keysPressed.contains(KeyCode.DIGIT1))
			{
				if (this.player.getGuns().get(0).isPickedUp())
				this.player.swapWeapon(0);
			}
			if(keysPressed.contains(KeyCode.DIGIT2))
			{
				if (this.player.getGuns().get(1).isPickedUp())
				this.player.swapWeapon(1);
			}
			if(keysPressed.contains(KeyCode.DIGIT3))
			{
				if (this.player.getGuns().get(3).isPickedUp())
				this.player.swapWeapon(3);
			}
			if(keysPressed.contains(KeyCode.DIGIT4))
			{
				if (this.player.getGuns().get(4).isPickedUp())
				this.player.swapWeapon(4);
			}
			
			//player shooting
			if (isShooting && player.getEquippedWeapon() != null)
			{
				this.player.getEquippedWeapon().shoot(player, mouseX, mouseY);
			}
		}

	}
	
	private void handleKeyPressed(KeyEvent event)
	{
		keysPressed.add(event.getCode());
	}

	private void handleKeyReleased(KeyEvent event)
	{
		keysPressed.remove(event.getCode());
	}

	private void handleMousePressed(MouseEvent event)
	{
		isShooting = true;
		if (player.getEquippedWeapon() != null)
		player.getEquippedWeapon().setShooting(true);
		updateMousePosition(event);
	}

	private void handleMouseDragged(MouseEvent event)
	{
		isShooting = true;
		updateMousePosition(event);
	}

	private void handleMouseReleased(MouseEvent event)
	{
		isShooting = false;
		if (player.getEquippedWeapon() != null)
		player.getEquippedWeapon().setShooting(false);

	}

	private void updateMousePosition(MouseEvent event)
	{
		mouseX = event.getSceneX();
		mouseY = event.getSceneY();
	}

}

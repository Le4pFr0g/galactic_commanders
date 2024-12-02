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
import entity.Wall;
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
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();// * 0.8;
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();// * 0.8;
    private AnimationTimer gameLoop;
    private AnimationTimer inputHandler;
	private int cameraDistance = 150;


	//user input
	private Set<KeyCode> keysPressed = new HashSet<>();
	private double mouseX, mouseY;
	private boolean isShooting = false;
	
	//game objects
	private Player player;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<AmmoPU> ammoPUs = new ArrayList<>();
	private ArrayList<WeaponPU> weaponPUs = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();
	


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

		this.player = new Player(200, 200, 1000);

		// Game loop using AnimationTimer
		inputHandler = new AnimationTimer()
		{

			@Override
			public void handle(long arg0)
			{
				handleControls(gc);
			}
	
		};
		inputHandler.start();
		gameLoop = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update(gc);
				cameraUpdate(gc);
				player.render(gc);
			}
		};
		gameLoop.start();

		spawnEnemies();
		addAmmo();
		addWeapons();
		addWalls();
		
	    System.out.println(WIDTH);
	    System.out.println(HEIGHT);

		Scene scene = new Scene(pane, WIDTH, HEIGHT);

		// Add key listeners to track pressed keys
		scene.setOnKeyPressed(this::handleKeyPressed);
		scene.setOnKeyReleased(this::handleKeyReleased);

		// Add mouse listeners to track mouse clicks
		scene.setOnMousePressed(this::handleMousePressed);
		scene.setOnMouseDragged(this::handleMouseDragged);
		scene.setOnMouseReleased(this::handleMouseReleased);

		primaryStage.setTitle("Galactic Commanders");
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	private void addWalls()
	{
//		for (int i = 0; i < HEIGHT; i += 50)
//			walls.add(new Wall(0, i, 50, 50, Color.GRAY));
		
		//for (int i = 200; i < HEIGHT-200; i += 50)
		//	walls.add(new Wall(WIDTH/4, 200, 50, 150, Color.GRAY));
		
		//for (int i = 400; i < WIDTH-200; i += 50)
			walls.add(new Wall(400, HEIGHT/2, 350, 50, Color.GRAY));
		
		
		

		
	}

	private void addAmmo()
	{
//	 	ammoPUs.add(new AmmoPU(100, 100, 0, 10, Color.MEDIUMPURPLE));
//		ammoPUs.add(new AmmoPU(100, 100, 1, 10, Color.MEDIUMPURPLE));
		ammoPUs.add(new AmmoPU(100, 100, 2, 10, Color.MEDIUMPURPLE));
//		ammoPUs.add(new AmmoPU(100, 100, 3, 10, Color.MEDIUMPURPLE));


	}
	
	private void addWeapons()
	{
//		weaponPUs.add(new WeaponPU(WIDTH/2, HEIGHT/2, 0, 10, Color.rgb(150, 20, 150)));
//		weaponPUs.add(new WeaponPU(WIDTH/3 + 200, HEIGHT/3 + 200, 1, 10, Color.rgb(0, 0, 0)));
		weaponPUs.add(new WeaponPU(400, 200, 2, 10, Color.rgb(255, 255, 255)));
//		weaponPUs.add(new WeaponPU(100, 100, 3, 10, Color.rgb(255, 255, 255)));



		
	}

	private void spawnEnemies()
	{
		Random random = new Random();
//		double x = random.nextDouble() * WIDTH;
//		double y = random.nextDouble() * HEIGHT;
		double x = WIDTH /3;
		double y = HEIGHT /3;
		this.enemies.add(new Enemy(this.player, x, y, 100, 5, Color.RED));// Color.rgb(255, 0, (int)(Math.random()*255))));
		//x = random.nextDouble() * WIDTH;
		//y = random.nextDouble() * HEIGHT;
//		x += 20;
//		y += 10;
//		this.enemies.add(new Enemy(this.player, x, y, 100, 5, Color.PURPLE));//  Color.rgb(255, (int)(Math.random()*255), 0)));
//		
//		x += 200;
//		y += 100;
//		this.enemies.add(new Enemy(this.player, x, y, 100, 5, Color.BLUE));//  Color.rgb(255, (int)(Math.random()*255), 0)));
//		x += 20;
//		y += 10;
//		this.enemies.add(new Enemy(this.player, x, y, 100, 5, Color.GREEN));//  Color.rgb(255, (int)(Math.random()*255), 0)));
	}

	

	private void update(GraphicsContext gc)
	{
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.TAN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		
		for (Wall w : walls)
		{
			w.render(gc);
			w.checkCollision(player);
			
			for (Enemy e : enemies)
			{
				//System.out.println("w.collide");

				//TO DO: improve path finding. check collision returns an int that can be used to determine where to go
				w.checkCollision(e);

			}
		}
		
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
					b.updatePos();
					
					if (g.isSplashGun())
					{

						if (b.damageEnemies(enemies))
						{
							System.out.println("Rocket");


							bulletsToRemove.add(b);
						}

						
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
			//System.out.println(bulletsToRemove);

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
				//e.attack(player);
			}
			e.render(gc);
			e.isTouchingWall();
		}
		
		if (player.getEquippedWeapon() != null)
		{
			player.getEquippedWeapon().fireCooldown();
			player.getEquippedWeapon().render(gc, player, Color.LIGHTGRAY);
		}
		

	}
	
	private void cameraUpdate(GraphicsContext gc)
	{
		System.out.println("TEST");
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, cameraDistance, HEIGHT);
		gc.fillRect(WIDTH-cameraDistance, 0, cameraDistance, HEIGHT);
		
		gc.fillRect(0, 0, WIDTH, cameraDistance);
		gc.fillRect(0, HEIGHT-cameraDistance, WIDTH, cameraDistance);


		if (player.getX() < cameraDistance && keysPressed.contains(KeyCode.A))
		{
			player.setX(cameraDistance);
			//weapon pick ups
			//ammo pick ups
			//enemies
			//walls
			
			for (WeaponPU w : weaponPUs)
			{
				w.setX(w.getX() + player.getSpeed());
			}
			for (AmmoPU a : ammoPUs)
			{
				a.setX(a.getX() + player.getSpeed());
			}
			for (Enemy e : enemies)
			{
				e.setX(e.getX() + player.getSpeed());
			}
			for (Wall w : walls)
			{
				w.setX(w.getX() + player.getSpeed());
			}
			
			
		}
		else if (player.getX() > WIDTH - cameraDistance - player.getWidth() && keysPressed.contains(KeyCode.D))
		{
			player.setX(WIDTH - cameraDistance - player.getWidth());

			//weapon pick ups
			//ammo pick ups
			//enemies
			//walls
			
			for (WeaponPU w : weaponPUs)
			{
				w.setX(w.getX() - player.getSpeed());
			}
			for (AmmoPU a : ammoPUs)
			{
				a.setX(a.getX() - player.getSpeed());
			}
			for (Enemy e : enemies)
			{
				e.setX(e.getX() - player.getSpeed());
			}
			for (Wall w : walls)
			{
				w.setX(w.getX() - player.getSpeed());
			}
			
		}
		
		if (player.getY() < cameraDistance && keysPressed.contains(KeyCode.W))
		{
			player.setY(cameraDistance);
			
			for (WeaponPU w : weaponPUs)
			{
				w.setY(w.getY() - player.getSpeed());
			}
			for (AmmoPU a : ammoPUs)
			{
				a.setY(a.getY() - player.getSpeed());
			}
			for (Enemy e : enemies)
			{
				e.setY(e.getY() - player.getSpeed());
			}
			for (Wall w : walls)
			{
				w.setY(w.getY() - player.getSpeed());
			}
		}
		
		else if (player.getY() > HEIGHT - cameraDistance - player.getWidth() && keysPressed.contains(KeyCode.S))
		{
			player.setY(HEIGHT - cameraDistance - player.getWidth());
			for (WeaponPU w : weaponPUs)
			{
				w.setY(w.getY() + player.getSpeed());
			}
			for (AmmoPU a : ammoPUs)
			{
				a.setY(a.getY() + player.getSpeed());
			}
			for (Enemy e : enemies)
			{
				e.setY(e.getY() + player.getSpeed());
			}
			for (Wall w : walls)
			{
				w.setY(w.getY() + player.getSpeed());
			}
		}
		
		
	}

	private void handleControls(GraphicsContext gc)
	{
		if (player.getHp() <= 0)
		{
			player.kill(gc, HEIGHT, WIDTH);

		}
		else
		{
			
			//player movement
			if (keysPressed.contains(KeyCode.W))
			{
				//System.out.println(player.getX() + ", " + player.getY());
				this.player.move(0, -player.getSpeed(), WIDTH, HEIGHT, cameraDistance, enemies);

			}
			if (keysPressed.contains(KeyCode.S))
			{
				//System.out.println(player.getX() + ", " + player.getY());

				this.player.move(0, player.getSpeed(), WIDTH, HEIGHT, cameraDistance, enemies);

			}
			if (keysPressed.contains(KeyCode.A))
			{
				//System.out.println(player.getX() + ", " + player.getY());
				this.player.move(-player.getSpeed(), 0, WIDTH, HEIGHT, cameraDistance, enemies);


			}
			if (keysPressed.contains(KeyCode.D))
			{
				//System.out.println(player.getX() + ", " + player.getY());

				this.player.move(player.getSpeed(), 0, WIDTH, HEIGHT, cameraDistance, enemies);

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
			
			if (keysPressed.contains(KeyCode.ESCAPE))
			{
				gameLoop.stop();
			}
			if (keysPressed.contains(KeyCode.BACK_SPACE))
			{
				gameLoop.start();
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

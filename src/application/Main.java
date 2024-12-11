package application;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import bullets.Bullet;
import entity.Player;
import entity.Wall;
import entity.enemies.Enemy;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import maps.Maps;
import pickups.AmmoPU;
import pickups.HealthPU;
import pickups.WeaponPU;
import weapons.Gun;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;

public class Main extends Application
{
	//system controls
//    private final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();// * 0.5;
//    private final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();// * 0.5;
    private final double SCREEN_WIDTH = 1600;
    private final double SCREEN_HEIGHT = 900;
    private AnimationTimer gameLoop;
    private AnimationTimer inputHandler;
	private int cameraDistance = 100;
	private int mapNumber = 2;
	

	//user input
	private Set<KeyCode> keysPressed = new HashSet<>();
	private double mouseX, mouseY;
	private boolean isShooting = false;
	
	//game objects
	private Player player;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<AmmoPU> ammoPUs = new ArrayList<>();
	private ArrayList<WeaponPU> weaponPUs = new ArrayList<>();
	private ArrayList<HealthPU> healthPUs = new ArrayList<>();
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
		Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
		canvas.setFocusTraversable(true);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
//		 // Load an image
//        Image backgroundImage = new Image(getClass().getResource("/desert_sand.jpg").toExternalForm()); // Adjust path as necessary
//
//        // Create a BackgroundImage with the loaded image
//        BackgroundImage background = new BackgroundImage(
//            backgroundImage,
//            BackgroundRepeat.NO_REPEAT,   // Don't repeat the image
//            BackgroundRepeat.NO_REPEAT,   // Don't repeat vertically
//            BackgroundPosition.CENTER,    // Center the image
//            BackgroundSize.DEFAULT        // Use the default size of the image
//        );
//		
//        pane.setBackground(new Background(background));

		pane.setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.getChildren().add(canvas);

		this.player = new Player(200, 200, 90);

		// Game loop using AnimationTimer
		inputHandler = new AnimationTimer()
		{

			@Override
			public void handle(long arg0)
			{
				handleControls(gc);
			}
	
		};
		
		loadMap(mapNumber);

		inputHandler.start();
		gameLoop = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update(gc);
				cameraUpdate(gc);
				player.render(gc, SCREEN_WIDTH, SCREEN_HEIGHT);
			}
		};
		gameLoop.start();

	    System.out.println(SCREEN_WIDTH);
	    System.out.println(SCREEN_HEIGHT);

		Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);

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
	
	

	

	private void loadMap(int i)
	{
		if (i == 1 || i == 0)
			Maps.createMap1(SCREEN_WIDTH, SCREEN_HEIGHT, player, walls, enemies, healthPUs, weaponPUs, ammoPUs);
		if (i == 2)
		{
			Maps.createMap2(SCREEN_WIDTH, SCREEN_HEIGHT, player, walls, enemies, healthPUs, weaponPUs, ammoPUs);
		}
		
	}


	private void update(GraphicsContext gc)
	{
		gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		List<Bullet> bulletsToRemove = new ArrayList<>();
		List<Enemy> enemiesToRemove = new ArrayList<>();
		List<Bullet> projectilesToRemove = new ArrayList<>();
		
		
		for (Wall w : walls)
		{
			w.render(gc, SCREEN_WIDTH, SCREEN_HEIGHT);
			w.checkCollision(player);
			
			for (Gun g : player.getGuns())
			{
				for (Bullet b : g.getBullets())
				{
					if (w.checkCollision(b) != 0)
					{
						bulletsToRemove.add(b);
						//System.out.println("BYE BULLET");
					}
				}
				if (bulletsToRemove.size() > 0)
				g.getBullets().remove(bulletsToRemove);
			}
			
			for (Enemy e : enemies)
			{

				//TO DO: improve path finding. check collision returns an int that can be used to determine where to go
				w.checkCollision(e);
				for (Bullet p : e.getProjectiles())
				{
					if (w.checkCollision(p) != 0)
					{
						projectilesToRemove.add(p);
						//System.out.println("BYE PROJECTILE");
					}
				}
				if (projectilesToRemove.size() > 0)
					e.getProjectiles().remove(projectilesToRemove);

			}
		}
		
		List<HealthPU> healthPUsToRemove = new ArrayList<>();
		for (HealthPU h : healthPUs)
		{
			if (h.checkCollision(player))
			{
				healthPUsToRemove.add(h);
			}
			h.render(gc);
		}
		healthPUs.removeAll(healthPUsToRemove);
		
		List<AmmoPU> ammoPUsToRemove = new ArrayList<>();
		for (AmmoPU a : ammoPUs)
		{
			if (a.checkCollision(player))
			{
				ammoPUsToRemove.add(a);
			}
			a.render(gc);
		}
		ammoPUs.removeAll(ammoPUsToRemove);

		
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
								//System.out.println("HIT ENEMY");
								bulletsToRemove.add(b);
								b.damageEnemy(e, g.getDmg());	
			
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
					//System.out.println("HIT PLAYER");
					player.setHp(player.getHp() - e.getDmg());
					projectilesToRemove.add(p);
					gc.setFill(new Color(.3, 0.0, 0.0, 0.15));
					gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
				}

			}
			e.getProjectiles().removeAll(projectilesToRemove);
			
			//checks if enemy is dead, if so remove them
			if (e.getHp() <= 0)
			{
				enemiesToRemove.add(e);
			}
		}

		
		enemies.removeAll(enemiesToRemove);
		

		for (Enemy e : enemies)
		{
			if (!e.isAwake())
			{
				e.setAwake(!e.isWallBetween(player, walls));
			}
			if (player.isAlive())
			{
				e.move(player, SCREEN_WIDTH, SCREEN_HEIGHT, enemies, walls);
				e.attack(player, SCREEN_WIDTH, SCREEN_HEIGHT, walls);
			}
			else
			{
				e.setAwake(false);
			}
			e.render(gc, SCREEN_WIDTH, SCREEN_HEIGHT);
			e.isTouchingWall();
		}
		
		if (player.getEquippedWeapon() != null)
		{
			player.getEquippedWeapon().fireCooldown();
			player.getEquippedWeapon().render(gc, player, Color.LIGHTGRAY);
		}
		
		if (walls.get(0).checkCollision(player) != 0)
		{
			endLevel(gc);
		}

	}
	
	
	
	
	private void cameraUpdate(GraphicsContext gc)
	{
//		gc.setFill(Color.BLACK);
//		
//		gc.fillRect(0, 0, cameraDistance, SCREEN_HEIGHT);
//		gc.fillRect(SCREEN_WIDTH-cameraDistance, 0, cameraDistance, SCREEN_HEIGHT);
//		
//		gc.fillRect(0, 0, SCREEN_WIDTH, cameraDistance);
//		gc.fillRect(0, SCREEN_HEIGHT-cameraDistance, SCREEN_WIDTH, cameraDistance);


		if (player.getX() < 2*cameraDistance && keysPressed.contains(KeyCode.A))
		{
			player.setX(2*cameraDistance);
			//health pick ups
			//weapon pick ups
			//ammo pick ups
			//enemies
			//walls
			
			for (HealthPU h : healthPUs)
			{
				h.setX(h.getX() + player.getSpeed());
			}
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
		else if (player.getX() > SCREEN_WIDTH - 2*cameraDistance - player.getWidth() && keysPressed.contains(KeyCode.D))
		{
			player.setX(SCREEN_WIDTH - 2*cameraDistance - player.getWidth());

			//health pick ups
			//weapon pick ups
			//ammo pick ups
			//enemies
			//walls
			
			for (HealthPU h : healthPUs)
			{
				h.setX(h.getX() - player.getSpeed());
			}
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
			
			for (HealthPU h : healthPUs)
			{
				h.setY(h.getY() + player.getSpeed());
			}
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
		
		else if (player.getY() > SCREEN_HEIGHT - cameraDistance - player.getWidth() && keysPressed.contains(KeyCode.S))
		{
			player.setY(SCREEN_HEIGHT - cameraDistance - player.getWidth());
			
			for (HealthPU h : healthPUs)
			{
				h.setY(h.getY() - player.getSpeed());
			}
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
		
		
	}

	private void handleControls(GraphicsContext gc)
	{
		if (player.getHp() <= 0)
		{
			player.kill(gc, SCREEN_WIDTH, SCREEN_HEIGHT);

		}
		else
		{
			
			//player movement
			if (keysPressed.contains(KeyCode.W))
			{
				//System.out.println(player.getX() + ", " + player.getY());
				this.player.move(0, -player.getSpeed(), SCREEN_WIDTH, SCREEN_HEIGHT, cameraDistance, enemies);

			}
			if (keysPressed.contains(KeyCode.S))
			{
				//System.out.println(player.getX() + ", " + player.getY());

				this.player.move(0, player.getSpeed(), SCREEN_WIDTH, SCREEN_HEIGHT, cameraDistance, enemies);

			}
			if (keysPressed.contains(KeyCode.A))
			{
				//System.out.println(player.getX() + ", " + player.getY());
				this.player.move(-player.getSpeed(), 0, SCREEN_WIDTH, SCREEN_HEIGHT, cameraDistance, enemies);


			}
			if (keysPressed.contains(KeyCode.D))
			{
				//System.out.println(player.getX() + ", " + player.getY());

				this.player.move(player.getSpeed(), 0, SCREEN_WIDTH, SCREEN_HEIGHT, cameraDistance, enemies);

			}
			
			//player sprint
			if (keysPressed.contains(KeyCode.SHIFT))
			{
				player.setSpeed(30);
			}
			else
			{
				player.setSpeed(Player.getDefaultspeed());
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
				if (this.player.getGuns().get(2).isPickedUp())
				this.player.swapWeapon(2);
			}
			if(keysPressed.contains(KeyCode.DIGIT4))
			{
				if (this.player.getGuns().get(3).isPickedUp())
				this.player.swapWeapon(3);
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
//		System.out.println(mouseX + ", " + mouseY);
	}
	
	private void endLevel(GraphicsContext gc)
	{
		gameLoop.stop();
		gc.setFont(Font.getDefault());
		gc.setFont(Font.font(60));
		gc.setFill(Color.RED);
		String gameOver = "YOU WIN!";
		double offset = gameOver.length() / 2;
		gc.fillText(gameOver, SCREEN_WIDTH / 2 - offset*20, SCREEN_HEIGHT / 2);
//		gc.setFill(Color.CORNFLOWERBLUE);
//		gc.fillRect(50, 50, 300, 300);
		gc.setFont(Font.getDefault());
		String text = "Hello, World!";
        String fileName = "saveFile.txt";
        
        int playerHP = player.getHp();
        boolean[] weaponsObtained = new boolean[player.getGuns().size()];
        int[] ammo = new int[player.getGuns().size()];
        for (int i = 0; i < player.getGuns().size(); i++)
        {
        	weaponsObtained[i] = player.getGuns().get(i).isPickedUp();
        	ammo[i] = player.getGuns().get(i).getAmmo();
        }

        // Create a JSON object
        JSONObject saveData = new JSONObject();
        saveData.put("weaponsObtained", weaponsObtained);
        saveData.put("ammo", ammo);
        saveData.put("playerHealth", playerHP);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
        {
            writer.write(saveData.toString(4));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        System.out.println(saveData.get("ammo"));
//        JSONArray ammo1 = saveData.getJSONArray("weaponsObtained");
//        
//        System.out.println(ammo1.getInt(0));


        loadMap(++mapNumber);
        gameLoop.start();
	}

}



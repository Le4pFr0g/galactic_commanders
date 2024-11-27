package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import entity.Bullet;
import entity.Enemy;
import entity.Player;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import entity.PickUp;

public class Main extends Application
{
	private static final int HEIGHT = 800;
	private static final int WIDTH = 800;
	private static final int SPEED = 4;
	private double mouseX, mouseY;

	private Player player;
	//private Map<KeyCode, Boolean> keys = new HashMap<>();
	private Set<KeyCode> keysPressed = new HashSet<>();
	private boolean isShooting = false;

	private List<Enemy> enemies = new ArrayList<>();

	private PickUp ammoPU = new PickUp(100, 100);

	public static void main(String[] args)
	{
		launch(args);
	}

	public static void schedule(long time, Runnable r)
	{
		new Thread(() ->
		{
			try
			{
				Thread.sleep(time);
				r.run();
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}).start();
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

		this.player = new Player(50, 50);

//		Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0 / 40), e -> update(gc)));
//		loop.setCycleCount(Animation.INDEFINITE);
//		loop.play();
		
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


		Scene scene = new Scene(pane, WIDTH, HEIGHT);

		// Add key listeners to track pressed keys
		scene.setOnKeyPressed(this::handleKeyPressed);
		scene.setOnKeyReleased(this::handleKeyReleased);

		// Add mouse listeners to track mouse clicks
		scene.setOnMousePressed(this::handleMousePressed);
		scene.setOnMouseDragged(this::handleMouseDragged);
		scene.setOnMouseReleased(this::handleMouseReleased);

		primaryStage.setTitle("Galactic Commanders");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void spawnEnemies()
	{
		Random random = new Random();
		double x = random.nextDouble() * WIDTH;
		double y = random.nextDouble() * HEIGHT;
		this.enemies.add(new Enemy(this.player, x, y, 100));
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
	}

	private void updateMousePosition(MouseEvent event)
	{
		mouseX = event.getSceneX();
		mouseY = event.getSceneY();
	}

	private void update(GraphicsContext gc)
	{
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.TAN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		this.player.render(gc);
		this.ammoPU.checkCollision(player);
		this.ammoPU.render(gc);
		
		List<Bullet> bulletsToRemove = new ArrayList<>();
		List<Enemy> enemiesToRemove = new ArrayList<>();
		for (Bullet b : player.getGun().getBullets())
		{
			for (Enemy e : enemies)
			{
				if (b.checkCollision(e))
				{
					System.out.println("HIT");
					bulletsToRemove.add(b);
					e.setHp(e.getHp() - 10);
					if (e.getHp() <= 0)
					{
						enemiesToRemove.add(e);
					}
					
				}
			}
		}
		player.getGun().getBullets().removeAll(bulletsToRemove);
		enemies.removeAll(enemiesToRemove);
		
		for (Enemy e : enemies)
		{
			//e.move(player);
			e.attack(player);
			e.render(gc);
		}

		if (keysPressed.contains(KeyCode.W))
		{
			this.player.move(0, -SPEED);
		}
		if (keysPressed.contains(KeyCode.S))
		{
			this.player.move(0, SPEED);
		}
		if (keysPressed.contains(KeyCode.A))
		{
			this.player.move(-SPEED, 0);
		}
		if (keysPressed.contains(KeyCode.D))
		{
			this.player.move(SPEED, 0);
		}
		if (isShooting)
		{
			this.player.getGun().shoot(player, mouseX, mouseY);
		}
		
//		for (KeyCode keypress : keysPressed)
//		{
//			System.out.println(keypress.getChar());
//		}
		if (isShooting)
		System.out.println(isShooting);


	}

}

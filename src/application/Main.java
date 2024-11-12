package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import entity.Player;
import entity.Enemy;

public class Main extends Application
{
	private static final int HEIGHT = 800;
	private static final int WIDTH = 800;
	private static final int SPEED = 4;
	
	private Player player;
	private Map<KeyCode, Boolean> keys = new HashMap<>();
	
	private List<Enemy> enemies = new ArrayList<>();
	
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static void schedule(long time, Runnable r)
	{
		new Thread(() -> {
			try
			{
				Thread.sleep(time);
				r.run();
			}
			catch (InterruptedException ex)
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
		
		Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/40), e -> update(gc)));
		loop.setCycleCount(Animation.INDEFINITE);
		loop.play();
		
		spawnEnemies();
		
		canvas.setOnKeyPressed(e ->  this.keys.put(e.getCode(), true));
		canvas.setOnKeyReleased(e ->  this.keys.put(e.getCode(), false));
		canvas.setOnMousePressed(e -> this.player.shoot(e.getX(), e.getY()));
		canvas.setOnMouseDragged(e -> this.player.shoot(e.getX(), e.getY()));

		Scene scene = new Scene(pane, WIDTH, HEIGHT);
		primaryStage.setTitle("Galactic Commanders");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void spawnEnemies()
	{
		Random random = new Random();
		double x = random.nextDouble()*WIDTH;
		double y = random.nextDouble()*HEIGHT;
		this.enemies.add(new Enemy(this.player , x, y));
//		Thread spawner = new Thread(() -> {
//			try
//			{
//				Random random = new Random();
//				while(true)
//				{
//					double x = random.nextDouble()*WIDTH;
//					double y = random.nextDouble()*HEIGHT;
//					this.enemies.add(new Enemy(this.player , x, y));
//					Thread.sleep(1000);
//				}
//			}
//			catch (InterruptedException ex)
//			{
//				ex.printStackTrace();
//			}
//		});
//		spawner.setDaemon(true);
//		spawner.start();
	}
	
	private void update(GraphicsContext gc)
	{
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.TAN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		this.player.render(gc);
		
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			e.render(gc);
		}
		
		if (this.keys.getOrDefault(KeyCode.W,  false))
		{
			this.player.move(0, -SPEED);
		}
		else if (this.keys.getOrDefault(KeyCode.A,  false))
		{
			this.player.move(-SPEED, 0);
		}
		else if (this.keys.getOrDefault(KeyCode.S,  false))
		{
			this.player.move(0, SPEED);
		}
		else if (this.keys.getOrDefault(KeyCode.D,  false))
		{
			this.player.move(SPEED, 0);
		}
	}
				
}

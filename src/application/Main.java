package application;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.stream.Collectors;

import entity.Sprite;

public class Main extends Application
{
	private Pane root = new Pane();
	
	private double t = 0;
			
	Sprite player = new Sprite(400, 400, 40, 40, "player", Color.GREEN);
	
	private Parent createContent()
	{
		int screenX = 800, screenY = 800;
//		Background background = new Background(new BackgroundFill(Color.TAN,  
//                CornerRadii.EMPTY, Insets.EMPTY));
		root.setPrefSize(screenX, screenY);
		
		root.getChildren().add(player);
		
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update();
			}
		}; 
		
		timer.start();
		
		return root;
		
	}

	
	
	private List<Sprite> sprites() 
	{
        return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
    }

	private void update()
	{
		//t += .016;
		
		sprites().forEach(s -> 
		{
			switch (s.getType())
			{
				
			
				case "enemybullet":
					s.move(5, 270);
					
					if (s.getBoundsInParent().intersects(player.getBoundsInParent()))
					{
						player.setDead(true);
						s.setDead(true);
					}
					break;
				
				
				 case "playerbullet":
	                    s.move(5, 90);
	
	                    sprites().stream().filter(e -> e.getType().equals("enemy")).forEach(enemy -> {
	                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
	                            enemy.setDead(true);
	                            s.setDead(true);
	                        }
	                    });
	
	                    break;
					
				case "enemy":
					if (t > 2)
					{
						t = 0;
						if (Math.random() < .3)
						{
							shoot(s);
							//s.shoot(root);
						}
					}
					break;
				
			}
				
		});
		
		root.getChildren().removeIf(n -> 
		{
			Sprite s = (Sprite) n;
			return s.isDead();
		});
	}
	
	void shoot(Sprite who)
	{
		root.getChildren().add(who.shoot());
	}
	
	private void tick()
	{
		player.tick();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(createContent());
		
		scene.setOnKeyPressed(e -> 
		{
//			
//			switch (e.getCode())
//			{
//			case A:
//				player.setVelX(5);
//				break;
//			case D:
//				player.setVelX(-5);
//				break;
//			case W:
//				player.setVelY(5);
//				break;
//			case S:
//				player.setVelY(-5);
//				break;
//			case SPACE:
//				//player.shoot(root);
//				shoot(player);
//				break;
//			}
			
			
			switch (e.getCode())
			{
			case A:
				player.move(10, calcAngle(player.getTranslateX(), (player.getTranslateX() - 10), player.getTranslateY(), player.getTranslateY()));
				break;
			case D:
				player.move(10, calcAngle(player.getTranslateX(), (player.getTranslateX() + 10), player.getTranslateY(), player.getTranslateY()));
				break;
			case W:
				player.move(10, calcAngle(player.getTranslateX(), (player.getTranslateX()), player.getTranslateY(), player.getTranslateY() - 10));
				break;
			case S:
				player.move(10, calcAngle(player.getTranslateX(), (player.getTranslateX()), player.getTranslateY(), player.getTranslateY() + 10));
				break;
			case SPACE:
				//player.shoot(root);
				shoot(player);
				break;
			}
				
		});
		
//		scene.setOnKeyReleased(e -> 
//		{
//			switch (e.getCode())
//			{
//			case A:
//				player.setVelX(5);
//				break;
//			case D:
//				player.setVelX(-5);
//				break;
//			case W:
//				player.setVelY(5);
//				break;
//			case S:
//				player.setVelY(-5);
//				break;
//			case SPACE:
//				//player.shoot(root);
//				shoot(player);
//				break;
//			}
//				
//		});
		
		primaryStage.setTitle("Galactic Commanders");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
		

	public static void main(String[] args)
	{
		launch(args);
	}
	
	public double calcAngle(double obj1X, double obj2X, double obj1Y, double obj2Y)
	{
		System.out.println("X's: " + obj1X + "| 2: " + obj2X);
		double xDis = obj2X - obj1X;
		double yDis = obj2Y - obj1Y;
//		System.out.println("X: " + xDis);
//		System.out.println("Y: " + yDis);
//		System.out.println("Y/X: " + yDis/xDis);
		
		double angle = Math.atan2(yDis, xDis);
		System.out.println("Radians: " + angle);

		
		angle = Math.toDegrees(angle);
		
		System.out.println(angle);
		return angle;
	}
}

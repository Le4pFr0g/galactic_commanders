package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet 
{
	private double x, y, angle;
	private static final double SPEED = 5;
	private static final double WIDTH = 10;
	
	public Bullet(double angle, double x, double y)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public void render(GraphicsContext gc){
		gc.setFill(Color.GRAY);
		gc.fillOval(this.x, this.y, WIDTH, WIDTH);
		
		this.x += Math.cos(this.angle)*SPEED;
		this.y += Math.sin(this.angle)*SPEED;
	}
	
}

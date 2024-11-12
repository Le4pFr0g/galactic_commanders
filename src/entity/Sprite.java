package entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle
{
	private boolean dead = false;
	private final String type;
	private double velX = 0;
	private double velY = 0;
	
	public Sprite(int x, int y, int w, int h, String type, Color color)
	{
		super(w, h, color);
		
		this.type = type;
		setTranslateX(x);
		setTranslateY(y);
				
	}
	
	public void tick()
	{
		setTranslateX(getTranslateX() + velX);
		setTranslateX(getTranslateY() + velY);
	}
	
	public void move(int amount, double angle)
	{
		angle = Math.toRadians(angle);
		
		//0 is right
		//90 is down
		//180 is left
		//270 is up		
		
		double xDirection = amount * (Math.cos(angle));
		moveX(xDirection);
		double yDirection = amount * (Math.sin(angle));
		moveY(yDirection);
	}
	
	public void moveX(double amount)
	{
		setTranslateX(getTranslateX() + amount);
	}
	public void moveY(double amount)
	{
		setTranslateY(getTranslateY() + amount);
	}
	
	public void setVelX(double velX)
	{
		this.velX = velX;
	}
	public void setVelY(double velY)
	{
		this.velY = velY;
	}
	
	public Sprite shoot()
	{
		
		
		Sprite s = new Sprite((int) (this.getTranslateX() + 20), (int) (this.getTranslateY()), 5, 20, this.type + "bullet", Color.BLACK);
		
		
		return s;
	}

	public boolean isDead()
	{
		return dead;
	}

	public void setDead(boolean dead)
	{
		this.dead = dead;
	}

	public String getType()
	{
		return type;
	}
	
	
	
}

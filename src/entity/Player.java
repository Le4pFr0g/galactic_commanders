package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.*;

import application.Main;

public class Player
{
	private double x, y;
	private static final double WIDTH = 50;
	private  List<Bullet> bullets = new ArrayList<>();
	private boolean shooting = false;
	
	public Player(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public void move(double x, double y)
	{
		this.x += x;
		this.y += y;
	}
	
	public void shoot(double x, double y)
	{
		if (shooting)
		{
			return;
		}
		shooting = true;
		Main.schedule(150, () -> this.shooting = false);
		double angle = Math.atan2(y-this.y, x-this.x); // Radians
		Bullet b = new Bullet(angle, this.x+WIDTH/2, this.y+WIDTH/2);
		this.bullets.add(b);
	}
	
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.GREEN);
		gc.fillRect(this.x, this.y, WIDTH, WIDTH);
		
		for (int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).render(gc);
		}
		
	}
	
	public double getX() 
	{
		return x;
	}
	
	public void setX(double x) 
	{
		this.x = x;
	}
	
	public double getY() 
	{
		return y;
	}
	
	public void setY(double y) 
	{
		this.y = y;
	}
	
}

//public double calcAngle(double obj1X, double obj2X, double obj1Y, double obj2Y)
//{
//	double xDis = obj2X - obj1X;
//	double yDis = obj2Y - obj1Y;
//
//	
//	double angle = Math.atan2(yDis, xDis);
//
//	
//	angle = Math.toDegrees(angle);
//	
//	return angle;
//}

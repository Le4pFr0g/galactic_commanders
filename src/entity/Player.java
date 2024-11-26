package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player
{
	private double x, y;
	private static final double WIDTH = 50;
	Gun gun = new Gun();

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

	public void render(GraphicsContext gc)
	{

		gc.setFill(Color.GREEN);
		gc.fillRect(this.x, this.y, WIDTH, WIDTH);
		gc.fillText(String.valueOf(gun.getAmmo()), x + WIDTH, y);

		for (Bullet element : gun.getBullets())
		{
			element.render(gc, Color.GRAY);
		}

	}

	public static double getWidth()
	{
		return WIDTH;
	}

	public Gun getGun()
	{
		return gun;
	}

	public void setGun(Gun gun)
	{
		this.gun = gun;
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

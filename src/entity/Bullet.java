package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet
{
	private double x, y, angle;
	private static final double SPEED = 5;
	private static final double WIDTH = 10;

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

	public boolean checkCollision(Enemy e)
	{
		double dx = this.x - e.getX();
		double dy = this.y - e.getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance < (Player.getWidth() + WIDTH))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public Bullet(double angle, double x, double y)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public void render(GraphicsContext gc, Color color)
	{
		gc.setFill(color);
		gc.fillOval(this.x, this.y, WIDTH, WIDTH);

		this.x += Math.cos(this.angle) * SPEED;
		this.y += Math.sin(this.angle) * SPEED;
	}

}

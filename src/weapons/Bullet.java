package weapons;

import entity.Enemy;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet
{
	private double x, y, angle;
	private double speed;
	private final double width = 10;
	



	private Color color;
	
	public Bullet(double angle, double x, double y, double speed, Color color)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
		this.color = color;
	}
	
	public double getWidth()
	{
		return width;
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

	public boolean checkCollision(Enemy e)
	{
		double dx = this.x - e.getX();
		double dy = this.y - e.getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance < (e.getWidth() + width))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkCollision(Player p)
	{
		double dx = this.x - p.getX();
		double dy = this.y - p.getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance < (p.getWidth() + width))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	

	public void render(GraphicsContext gc)
	{
		gc.setFill(this.color);
		gc.fillOval(this.x, this.y, width, width);

		this.x += Math.cos(this.angle) * speed;
		this.y += Math.sin(this.angle) * speed;
	}

}

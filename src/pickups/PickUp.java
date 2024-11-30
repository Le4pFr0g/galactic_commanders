package pickups;

import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PickUp
{
	private double x, y;
	private double width = 30;
	private Color color;
	
	public PickUp(double x, double y, Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void render(GraphicsContext gc)
	{
		
		gc.setFill(color);
		gc.fillRect(this.getX(), this.getY(), getWidth(), getWidth());
	}

	protected void onCollide(Player p)
	{
		System.out.println("TEST: DONT PRINT THIS");
	}

	public boolean checkCollision(Player p)
	{
		double dx = this.getX() - p.getX();
		double dy = this.getY() - p.getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance < ( getWidth()))
		{
			onCollide(p);
			System.out.println("TEST");

			return true;
		}
		else
		{
			return false;
		}
	}

	public void tempSpawn(double x, double y)
	{
		this.setX(x);
		this.setY(y);
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

}

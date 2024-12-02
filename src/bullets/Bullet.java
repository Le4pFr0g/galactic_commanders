package bullets;

import java.util.ArrayList;
import java.util.List;

import entity.Enemy;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet
{
	protected double x;
	protected double y;
	protected double angle;
	protected double speed;
	protected final double width = 10;
	



	protected Color color;
	
	public Bullet(double angle, double x, double y, double speed, Color color)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
		this.color = color;
	}


	public boolean checkCollision(Enemy e)
	{
		double dx = this.x - e.getX();
		double dy = this.y - e.getY();
		double distance = (double) Math.sqrt(dx * dx + dy * dy);
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
		double distance = (double) Math.sqrt(dx * dx + dy * dy);
		if (distance < (p.getWidth() + width))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void damageEnemy(Enemy e)
	{
		e.setHp(e.getHp() - 10);
		
	}
	
	public void updatePos()
	{
		this.x += Math.cos(this.angle) * speed;
		this.y += Math.sin(this.angle) * speed;
	}

	public void render(GraphicsContext gc)
	{
		gc.setFill(this.color);
		gc.fillOval(this.x, this.y, width, width);
		

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


	public boolean damageEnemies(ArrayList<Enemy> enemies)
	{
		//stub to override
		System.out.println("TEST: DON'T PRINT Bullet.damageEnemies(List<Enemy> enemies)");
		return false;
		
	}



}

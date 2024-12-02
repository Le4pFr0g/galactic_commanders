package bullets;

import java.util.ArrayList;
import java.util.List;

import entity.MovingObject;
import entity.Player;
import entity.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends MovingObject
{
	protected double angle;
	protected static final double defaultWidth = 10;
	protected Color color;
	
	public Bullet(double angle, double x, double y, double speed, Color color)
	{
		super(x, y, defaultWidth, speed);
		this.angle = angle;
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

	public void damageEnemy(Enemy e, int dmg)
	{
		e.setHp(e.getHp() - dmg);
		
	}
	
	public void updatePos()
	{
		this.x += Math.cos(angle) * speed;
		this.y += Math.sin(angle) * speed;

	}

	public void render(GraphicsContext gc, double sW, double sH)
	{
		boolean shouldRender = 	this.x > 0 &&
				this.x < sW &&
				this.y > 0 &&
				this.y < sH;
				
		if (shouldRender)
		{
			gc.setFill(this.color);
			gc.fillOval(this.x, this.y, width, width);
			gc.setLineWidth(.75);
			gc.setFill(color.rgb(50, 50, 50));
			gc.strokeOval(this.x, this.y, width, width);
		}

	}
	
	public double getWidth()
	{
		return width;
	}

//	public double getX()
//	{
//		return x;
//	}
//
//	public void setX(double x)
//	{
//		this.x = x;
//	}
//
//	public double getY()
//	{
//		return y;
//	}
//
//	public void setY(double y)
//	{
//		this.y = y;
//	}


	public boolean damageEnemies(ArrayList<Enemy> enemies)
	{
		//stub to override
		System.out.println("TEST: DON'T PRINT Bullet.damageEnemies(List<Enemy> enemies)");
		return false;
		
	}



}

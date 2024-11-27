package entity;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy
{
	private double x, y;
	private int hp;
	private double speed = 2;
	private int shootingCooldown = 50;
	List<Bullet> projectiles = new ArrayList<>();
	
	//private Player player;
	private static final double WIDTH = 50;

	public Enemy(Player p, double x, double y, int hp)
	{
		//this.player = p;
		this.x = x;
		this.y = y;
		this.hp = hp;
	}
	
	public void move(Player p)
	{
		double tolerence = 2;
		if (Math.abs(p.getX() - this.x) < tolerence)
		{}
		else if (p.getX() > this.x)
		{
			this.x += speed;
		}
		else
		{
			this.x -= speed;
		}
		
		if (Math.abs(p.getY() - this.y) < tolerence)
		{}
		else if (p.getY() > this.y)
		{
			this.y += speed;
		}
		else
		{
			this.y -= speed;		
		}
	}
	
	public void attack(Player p)
	{
		if (shootingCooldown == 0)
		{
			double angle = calcAngle(this.x, p.getX(), this.y, p.getY());
			projectiles.add(new Bullet(angle, this.x + WIDTH/2, this.y + WIDTH/2));
			shootingCooldown = 50;
		}
		shootingCooldown--;
		
	}

	private void onHit()
	{
		this.setHp(hp - 10);
	}

	public void checkCollision(Bullet b)
	{
		double dx = this.x - b.getX();
		double dy = this.y - b.getY();
		double distance = (double) Math.sqrt(dx * dx + dy * dy);
		if (distance < (Player.getWidth() + WIDTH))
		{
			onHit();
		}
	}

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
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
	
	public double calcAngle(double obj1X, double obj2X, double obj1Y, double obj2Y)
	{
		double xDis = obj2X - obj1X;
		double yDis = obj2Y - obj1Y;
	
	
		double angle = Math.atan2(yDis, xDis);
	
	
		//angle = Math.toDegrees(angle);
	
		return angle;
	}

	public void render(GraphicsContext gc)
	{
		//enemy
		gc.setFill(Color.RED);
		gc.fillRect(this.x, this.y, WIDTH, WIDTH);

		//double distance = Math.sqrt(Math.pow(this.x - player.getX(), 2) + Math.pow(this.y - player.getY(), 2));
		
		//projectiles
		for (Bullet element : projectiles)
		{
			element.render(gc, Color.DARKRED);
		}
		
		//health
		gc.fillText(String.valueOf(this.hp), x + WIDTH, y);

	}
}

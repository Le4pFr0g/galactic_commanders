package entity;

import java.util.ArrayList;
import java.util.List;

import bullets.Bullet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Enemy
{
	private double x, y;
	private int hp;
	private double speed = 2;
	private int shootingCooldown = 50;
	private List<Bullet> projectiles = new ArrayList<>();
	private int projectileSpeed;
	private Color color;
	

	// private Player player;
	private final double width = 50;




	public Enemy(Player p, double x, double y, int hp, int projectileSpeed, Color color)
	{
		// this.player = p;
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.projectileSpeed = projectileSpeed;
		this.color = color;
		
	}

	
	public void move(Player p)
	{
		double tolerence = 2;
		if (Math.abs(p.getX() - this.x) < tolerence)
		{
		}
		else if (p.getX() > this.x)
		{
			this.x += speed;
		}
		else
		{
			this.x -= speed;
		}

		if (Math.abs(p.getY() - this.y) < tolerence)
		{
		}
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
			projectiles.add(new Bullet(angle, this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
			shootingCooldown = 25;
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
		//if the distance from the bullet to the enemy is less than the width of the enemy
		if (distance < (b.getWidth() + this.width))
		{
			onHit();
		}
	}
	
	public List<Bullet> getProjectiles()
	{
		return projectiles;
	}

	public void setProjectiles(List<Bullet> projectiles)
	{
		this.projectiles = projectiles;
	}
	
	public double getWidth()
	{
		return width;
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


		return angle;
	}

	public void render(GraphicsContext gc)
	{
		// enemy
		gc.setFill(this.color);
		gc.fillRect(this.x, this.y, width, width);

		// double distance = Math.sqrt(Math.pow(this.x - player.getX(), 2) +
		// Math.pow(this.y - player.getY(), 2));

		// projectiles
		for (Bullet element : projectiles)
		{
			element.render(gc);
		}

		// health
		gc.setFont(Font.getDefault());
		gc.fillText(String.valueOf(this.hp), x + width, y);

	}
}

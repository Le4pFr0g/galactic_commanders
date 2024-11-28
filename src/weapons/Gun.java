package weapons;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gun
{
	protected static final double WIDTH = 50;
	protected List<Bullet> bullets = new ArrayList<>();
	protected int shootingCooldown;
	protected int bulletSpeed;
	protected int fireRate;

	protected int ammo;

	public Gun(int fireRate, int bulletSpeed, int ammo)
	{
		this.fireRate = fireRate;
		this.shootingCooldown = fireRate;
		this.bulletSpeed = bulletSpeed;
		this.ammo = ammo;
	}
	
	public void render(GraphicsContext gc, Player p, Color color)
	{
		gc.setFill(color);
		gc.fillRect(p.getX()+p.getWidth(), p.getY()+p.getWidth()/2, 10, 20);
	}

	public List<Bullet> getBullets()
	{
		return bullets;
	}

	public void setBullets(List<Bullet> bullets)
	{
		this.bullets = bullets;
	}

	public void shoot(Player p, double x, double y)
	{
		if (shootingCooldown <= 0)
		{
			if (ammo > 0)
			{
				ammo -= 1;
				shootingCooldown = fireRate;
				double angle = Math.atan2(y - p.getY(), x - p.getX()); // Radians
				Bullet b = new Bullet(angle, p.getX() + WIDTH / 2, p.getY() + WIDTH / 2, bulletSpeed, Color.GRAY);
				this.bullets.add(b);
			}
		}
		else
		{
			shootingCooldown--;
		}
		// shooting = true;

	}

	public int getAmmo()
	{
		return ammo;
	}

	public void setAmmo(int ammo)
	{
		this.ammo = ammo;
	}
}

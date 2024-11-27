package entity;

import java.util.ArrayList;
import java.util.List;

import application.Main;

public class Gun
{
	private static final double WIDTH = 50;
	private List<Bullet> bullets = new ArrayList<>();
	private int shootingCooldown;

	private int ammo = 10;

	Gun()
	{
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
				shootingCooldown = 5;
				//Main.schedule(150, () -> this.shooting = false);
				double angle = Math.atan2(y - p.getY(), x - p.getX()); // Radians
				Bullet b = new Bullet(angle, p.getX() + WIDTH / 2, p.getY() + WIDTH / 2);
				this.bullets.add(b);
			}
		}
		else
		{
			shootingCooldown--;
		}
		//shooting = true;
		

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

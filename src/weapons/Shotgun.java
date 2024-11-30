package weapons;

import entity.Player;
import javafx.scene.paint.Color;

public class Shotgun extends Gun
{
	static int fireRate = 25;
	static int bSpeed = 2;
	static int defaultAmmo = 10;
	static int numOfShells = 5;

	public Shotgun()
	{
		super(fireRate, bSpeed, defaultAmmo);
		
	}
	
	@Override
	public void shoot(Player p, double x, double y)
	{
		if (shootingCooldown <= 0)
		{
			if (ammo > 0)
			{
				ammo -= 1;
				shootingCooldown = fireRate;
				double angle = Math.atan2(y - p.getY(), x - p.getX()); // Radians
				double a = angle - Math.toRadians(10);
				for (int i = 0; i < numOfShells; i++)
				{
					//bulletSpeed = (int)(Math.random() * 3) + 3;
					a = a + Math.toRadians(5);
					Bullet b = new Bullet(a, p.getX() + WIDTH / 2, p.getY() + WIDTH / 2, bulletSpeed, Color.GRAY);
					this.bullets.add(b);
				}
			}
		}
		else
		{
			shootingCooldown--;
		}
		// shooting = true;

	}

}

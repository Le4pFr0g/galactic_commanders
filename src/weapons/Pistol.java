package weapons;

import bullets.Bullet;
import entity.Player;
import javafx.scene.paint.Color;

public class Pistol extends Gun
{
	static final int dmg = 5;
	
	static int fireRate = -1;
	static int bSpeed = 5;
	static int defaultAmmo = 10;
	

	public Pistol()
	{
		super(dmg, fireRate, bSpeed, defaultAmmo, false);

	}
	
	@Override
	public void shoot(Player p, double x, double y)
	{
		
		if (checkMouseClick())
		{
			this.setShooting(false);
			if (ammo > 0)
			{
				ammo -= 1;

				double angle = Math.atan2(y - p.getY(), x - p.getX()); // Radians
				Bullet b = new Bullet(angle, p.getX() + WIDTH / 2, p.getY() + WIDTH / 2, bulletSpeed, Color.GRAY);
				this.bullets.add(b);
			}
		}

	}
	


}

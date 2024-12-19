package weapons;

import bullets.Bullet;
import bullets.Rocket;
import entity.Player;
import javafx.scene.paint.Color;

public class RocketLauncher extends Gun
{
	//full auto
	static final int dmg = 20;
	
	static int fireRate = 25;
	static int bSpeed = 3;
	static int defaultAmmo = 10;
	
	public RocketLauncher()
	{
		super(dmg, fireRate, bSpeed, defaultAmmo, true);

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
				
				double angle = Math.atan2((y - (p.getY() + p.getWidth()/2)), (x - (p.getX() + p.getWidth()/2 ))); // Radians
				Rocket r = new Rocket(angle, p.getX() + WIDTH / 2, p.getY() + WIDTH / 2, bulletSpeed, Color.GRAY);
				this.bullets.add(r);
			}
		}

	}

}

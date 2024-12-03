package entity.enemies;

import java.util.ArrayList;

import bullets.Bullet;
import entity.Player;
import entity.Wall;
import javafx.scene.paint.Color;

public class AssaultTrooper extends Enemy
{
	static int defaultHp = 200;
	static int defaultProjectileSpeed = 5;
	static Color defaultColor = Color.BROWN;
	public AssaultTrooper(double x, double y)
	{
		super(x, y, defaultHp, defaultProjectileSpeed, defaultColor);
	}
	
	@Override
	public void attack(Player p, double sW, double sH, ArrayList<Wall> walls)
	{
		if (awake)
		{
			if (!isWallBetween(p, walls) || !isCloseToScreen(sW, sH))
			{
				if (shootingCooldown == 0)
				{
					double angle = calcAngle(this.x, p.getX(), this.y, p.getY());
					projectiles.add(new Bullet(angle, this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
					shootingCooldown = 25;
				}
				if (shootingCooldown > 0)
				shootingCooldown--;
			}
		}
		
	}

}

package entity.enemies;

import java.util.ArrayList;

import bullets.Bullet;
import entity.Player;
import entity.Wall;
import javafx.scene.paint.Color;

public class Blob extends Enemy
{
	private int delay = 25;
	private final int defaultFireCooldown = 200;
	
	static int defaultHp = 150;
	static int defaultProjectileSpeed = 5;
	static Color defaultColor = Color.YELLOW;
	
	public Blob(double x, double y)
	{
		super(x, y, defaultHp, defaultProjectileSpeed, defaultColor);
		this.setDmg(10);
	}
	
	@Override
	public void attack(Player p, double sW, double sH, ArrayList<Wall> walls)
	{
		if (awake)
		{
			if (!isWallBetween(p, walls) || !isCloseToScreen(sW, sH))
			{
				double angle = calcAngle(this.x, p.getX(), this.y, p.getY());
		
				if (shootingCooldown == 0 || shootingCooldown == delay || shootingCooldown == 2*delay)
				{
					projectiles.add(new Bullet(angle, this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
					projectiles.add(new Bullet(angle - Math.toRadians(10), this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
					projectiles.add(new Bullet(angle + Math.toRadians(10), this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
		
					if (shootingCooldown == 0)
					{
						shootingCooldown = defaultFireCooldown;
					}
				}
			}
			if (shootingCooldown > 0)
			shootingCooldown--;
		}
	}

}

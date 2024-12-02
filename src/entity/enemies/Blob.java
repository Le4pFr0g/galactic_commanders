package entity.enemies;

import bullets.Bullet;
import entity.Player;
import javafx.scene.paint.Color;

public class Blob extends Enemy
{
	private int delay = 25;
	private final int defaultFireCooldown = 200;
	public Blob(Player p, double x, double y, int hp, int projectileSpeed, Color color)
	{
		super(p, x, y, hp, projectileSpeed, color);
	}
	
	public void attack(Player p)
	{
		double angle = calcAngle(this.x, p.getX(), this.y, p.getY());

		if (shootingCooldown == 0 || shootingCooldown == delay || shootingCooldown == 2*delay)
		{
			System.out.println(shootingCooldown);
			projectiles.add(new Bullet(angle, this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
			projectiles.add(new Bullet(angle - Math.toRadians(10), this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
			projectiles.add(new Bullet(angle + Math.toRadians(10), this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));

			if (shootingCooldown == 0)
			{
				shootingCooldown = defaultFireCooldown;
			}
		}
		shootingCooldown--;
	}

}

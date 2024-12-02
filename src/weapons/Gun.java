package weapons;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import bullets.Bullet;
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
	protected boolean pickedUp = false;
	protected boolean isSplashGun;
	
	public boolean isSplashGun()
	{
		return isSplashGun;
	}

	public void setSplashGun(boolean isSplashGun)
	{
		this.isSplashGun = isSplashGun;
	}

	//this is used by semi auto weapon(s)
	protected boolean isShooting;

	protected int ammo;

	public Gun(int fireRate, int bulletSpeed, int ammo, boolean isSplashGun)
	{
		this.fireRate = fireRate;
		this.shootingCooldown = fireRate;
		this.bulletSpeed = bulletSpeed;
		this.ammo = ammo;
		this.isSplashGun = isSplashGun;
	}
	
	public void render(GraphicsContext gc, Player p, Color color)
	{
		gc.setFill(color);
		gc.fillRect(p.getX()+p.getWidth(), p.getY()+p.getWidth()/2, 10, 20);
	}
	
	public boolean checkMouseClick()
	{
		return this.isShooting;
		
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
				System.out.println(x + ", " + y + ", " + Math.toDegrees(angle));

				Bullet b = new Bullet(angle, p.getX() + WIDTH / 2, p.getY() + WIDTH / 2, bulletSpeed, Color.GRAY);
				this.bullets.add(b);
			}
		}

	}
	
	//is used to decrement shooting cooldown
	public void fireCooldown()
	{
		if (shootingCooldown > 0)
		shootingCooldown--;
	}
	
	public boolean isShooting()
	{
		return isShooting;
	}

	public void setShooting(boolean isShooting)
	{
		this.isShooting = isShooting;
	}
	
	public List<Bullet> getBullets()
	{
		return bullets;
	}

	public void setBullets(List<Bullet> bullets)
	{
		this.bullets = bullets;
	}

	public int getAmmo()
	{
		return ammo;
	}

	public void setAmmo(int ammo)
	{
		this.ammo = ammo;
	}
	
	public boolean isPickedUp()
	{
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp)
	{
		this.pickedUp = pickedUp;
	}
}

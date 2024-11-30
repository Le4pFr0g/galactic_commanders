package bullets;

import java.util.ArrayList;

import entity.Enemy;
import javafx.scene.paint.Color;

public class Rocket extends Bullet
{
	private static double splashRadius = 100;

	public Rocket(double angle, double x, double y, double speed, Color color)
	{
		super(angle, x, y, speed, color);
	}
	
	@Override
	public boolean damageEnemies(ArrayList<Enemy> enemies)
	{
		
		
//		for (int i = 0; i < enemies.size(); i++)
//		{
//			Enemy e1 = enemies.get(i);
//			Enemy e2 = enemies.get(1);
//
//		
		Enemy e1 = enemies.get(0);
		Enemy e2 = enemies.get(1);
			if (this.checkCollision(e1))
			{
				if (checkSplashDmg(e1, e2));
				{
					this.damageEnemy(e2);
				}
				System.out.println("Rocket Hit");
				this.damageEnemy(e1);
				return true;
			}
			else if (this.checkCollision(e2))
			{
				if (checkSplashDmg(e2, e1));
				{
					this.damageEnemy(e1);
				}
				System.out.println("Rocket Hit");
				this.damageEnemy(e2);
				return true;
			}
			else
			{
				return false;
			}
//		}
	}
	
	/*
	 i am a bullet
	 i have a coordinate
	 i am a rocket
	 i have a radius around the bullet
	 i know where all enemies are
	 i can check if an enemy is touching splash radius
	  
	  
	  
	  
	  
	  
	  
	 
	 */
	public boolean checkSplashDmg(Enemy e1, Enemy e2)
	{
		double xAroundE = (e1.getX() + splashRadius );
		double yAroundE = (e1.getY() + splashRadius );
		
//		if e.posX < eneX
//		if e.posY < eneY
		if (e2.getX() < xAroundE)
		{
			if (e2.getY() < yAroundE || e2.getY() < (yAroundE - 2*splashRadius))
			{
			System.out.println("splash damage occured");
			return true;
			}
			else return false;
		}
		else if (e2.getX() < (xAroundE - 2*splashRadius))
		{
			if (e2.getY() < yAroundE || e2.getY() < (yAroundE - 2*splashRadius))
			{
			System.out.println("splash damage occured");
			return true;
			}
			else return false;
		}
		else
		{
			return false;
		}
			
		
//		double dx = this.x - e.getX();
//		double dy = this.y - e.getY();
//		double distance = (double) Math.sqrt(dx * dx + dy * dy);
//		if (distance < (e.getWidth() + width + splashRadius))
//		{
//			//return true;
//		}
//		else
//		{
//			//return false;
//		}
	}

}

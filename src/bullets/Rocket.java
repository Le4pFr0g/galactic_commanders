package bullets;

import java.util.ArrayList;

import entity.enemies.Enemy;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rocket extends Bullet
{
	private static double splashRadius = 100;
	private boolean explode = false;

	public Rocket(double angle, double x, double y, double speed, Color color)
	{
		super(angle, x, y, speed, color);
	}
	
	@Override
	public void render(GraphicsContext gc, double sW, double sH)
	{
		boolean shouldRender = 	this.x > 0 &&
				this.x < sW &&
				this.y > 0 &&
				this.y < sH;
		if (shouldRender)
		{
			gc.setFill(this.color);
			gc.fillOval(this.x, this.y, width, width);
			
			if (explode)
			{
				explosion(gc);
			
			}
		}
		
	}
	
	protected void explosion(GraphicsContext gc)
	{
		gc.setFill(Color.RED);
		gc.setGlobalAlpha(.5);
		double centerX = this.x + this.width/2;
		double centerY = this.y + this.width/2;
		//width of the inner square is width/2, divide that by 2 to get the location of origin
		double inX = centerX - ((splashRadius/2)/2);
		double inY = centerY - ((splashRadius/2)/2);
		gc.fillRect(inX, inY , splashRadius/2, splashRadius/2);
		gc.setGlobalAlpha(1);
		
	}

	@Override
	public boolean damageEnemies(ArrayList<Enemy> enemies)
	{
		
		boolean hit = false;
		for (int i = 0; i < enemies.size()-1; i++)
		{
			Enemy e1 = enemies.get(i);
			
			if (enemies.size() == 1)
			{
				if (this.checkCollision(e1))
				{
					this.damageEnemy(e1, 20);
				}
			}
			else
			{
				for (int j = 1; j < enemies.size();)
				{
					Enemy e2 = enemies.get(j);
				
					if (this.checkCollision(e1))
					{
						if (checkSplashDmg(e1, e2));
						{
							this.damageEnemy(e2, 20);
						}
						System.out.println("Rocket Hit 1");
						this.damageEnemy(e1, 20);
						this.explode = true;
						hit = true;
						break;
					}
	//				else if (this.checkCollision(e2))
	//				{
	//					if (checkSplashDmg(e2, e1));
	//					{
	//						this.damageEnemy(e1);
	//					}
	//					System.out.println("Rocket Hit 2");
	//					this.damageEnemy(e2);
	//					this.explode = true;
	//					hit = true;
	//					break;
	//					
	//				}
					else
					{
						hit = false;
						break;
					}
				}
			}
			
		}
		return hit;
//		}
	}

	public boolean checkSplashDmg(Enemy e1, Enemy e2)
	{
		double xAroundE = (e1.getX() + splashRadius );
		double yAroundE = (e1.getY() + splashRadius );
		
		//check if enemy 2 is within +x blast radius
		//if so check if they are in + or - y blast radius
		//if so return true
		
		//also check the -x blast radius and repeat
		if (e2.getX() < xAroundE)
		{
			if (e2.getY() < yAroundE || e2.getY() < (yAroundE - 2*splashRadius))
			{
				System.out.println("splash damage occured");
				return true;
			}
			else 
			{
				return false;
			}
			
		}
		else if (e2.getX() < (xAroundE - 2*splashRadius))
		{
			if (e2.getY() < yAroundE || e2.getY() < (yAroundE - 2*splashRadius))
			{
				System.out.println("splash damage occured");
				return true;
			}
			else 
			{
				return false;
			}
		}
		else
		{
			return false;
		}

	}

}

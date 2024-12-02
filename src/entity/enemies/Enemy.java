package entity.enemies;

import java.util.ArrayList;
import java.util.List;

import bullets.Bullet;
import entity.MovingObject;
import entity.Player;
import entity.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Enemy extends MovingObject
{
	protected int shootingCooldown = 50;
	protected List<Bullet> projectiles = new ArrayList<>();
	protected int projectileSpeed;
	protected Color color;
    private int hp;
	

	public int getHp()
	{
		return hp;
	}


	public void setHp(int hp)
	{
		this.hp = hp;
	}

	// private Player player;
	private static final double defaultWidth = 50;
	private static final double defaultSpeed = 2;
	
	private boolean touchingWall = false;





	public Enemy(Player p, double x, double y, int hp, int projectileSpeed, Color color)
	{
		super(x, y, defaultWidth, defaultSpeed);
		this.projectileSpeed = projectileSpeed;
		this.color = color;
		this.hp = hp;
		
	}

	
	public void move(Player p)
	{
		if (!touchingWall)
		{
			//System.out.println("move(): " + x + ", " + y);

			//tolerence is how many pixels away the enemy will move to
			double tolerence = 75;
			if (Math.abs(p.getX() - this.x) < tolerence)
			{
			}
			else if (p.getX() > this.x)
			{
				this.x += speed;
			}
			else
			{
				this.x -= speed;
			}
	
			if (Math.abs(p.getY() - this.y) < tolerence)
			{
			}
			else if (p.getY() > this.y)
			{
				this.y += speed;
			}
			else
			{
				this.y -= speed;
			}
		}
	}


	public void moveAlongWall(int side, Wall w)
	{
		// 1 for top
		// 2 for right
		// 3 for bottom
		// 4 for left
		
		//System.out.println("moveAwayFromWall(): " + side);
		
		if (side == 2 || side == 4)
		{
			if (w.getY() + w.getHeight()/2 > this.y + this.width)
			{
				moveAlongVerticalWall(w, -1);
			}
			else
			{
				System.out.println("Move down");
				moveAlongVerticalWall(w, -1);
			}
				
		}
		else if (side == 1 || side == 3)
		{
			if (w.getX() + w.getWidth()/2 > this.x + this.width)
			{
				moveAlongHorizontalWall(w, -1);
			}
			else
			{
				moveAlongHorizontalWall(w, 1);
			}
		}
		else
		{
			System.out.println("BIG OOPS");
		}
	}
	


	private void moveAlongVerticalWall(Wall w, int direction)
	{
		//this.printPos();
				
		double amount = (w.getY() - this.y - w.getHeight());
		
		System.out.println("Y: " + amount*direction);

		System.out.println("Y = " + this.y);
		this.y += direction * speed;
		System.out.println("Y = " + this.y);

		
//		double tolerence = 50;
//
//		System.out.println("2: " + (Math.abs(w.getY() - this.y) < tolerence));
//		System.out.println("2: " + (w.getX() - this.x));
//		
//
//		System.out.println();
//
//		if (Math.abs(w.getY() - this.y) < tolerence)
//		{
//		}
//		else if (w.getY() > this.y)
//		{
//			this.y += speed;
//		}
//		else
//		{
//			this.y -= speed;
//		}
		
		
		
	}
	
	private void moveAlongHorizontalWall(Wall w, int direction)
	{
		
		double amount = ((w.getWidth() + this.width) - w.getWidth() + w.getX() - this.x - this.width);
		
		System.out.println("X: " + amount*direction);

		
		this.x += direction * amount;
//		double tolerence = 50;
//		
//		System.out.println("1: " + (Math.abs(w.getX() - this.x) < tolerence));
//		System.out.println("1: " + (w.getX() - this.x));
//		
//
//		
//		System.out.println();
//		if (Math.abs(w.getX() - this.x) < tolerence)
//		{
//		}
//		else if (w.getX() > this.x)
//		{
//			this.x += speed;
//		}
//		else
//		{
//			this.x -= speed;
//		}
	
		
	}
	
	private void printPos()
	{
		System.out.println(x + ", " + y + "\t");
	}


	public void attack(Player p)
	{
		if (shootingCooldown == 0)
		{
			double angle = calcAngle(this.x, p.getX(), this.y, p.getY());
			projectiles.add(new Bullet(angle, this.x + width / 2, this.y + width / 2, projectileSpeed, Color.DARKRED));
			shootingCooldown = 25;
		}
		shootingCooldown--;

	}

	public void checkCollision(Player p)
	{
		int side = sideFinder(p);
		System.out.println(side);

		
		switch (side)
		{
			case 0: 
				break;
			case 1:
				p.setY(p.getY() - p.getSpeed());
				break;
			case 2:
				p.setX(p.getX() - p.getSpeed());
				break;
			case 3: 
				p.setY(p.getY() + p.getSpeed());
				break;
			case 4:
				p.setX(p.getX() + p.getSpeed());
				break;
			default: break;
		}
		
	}
	
	
	//same as in player class
//returns 1 for top
//returns 2 for right
//returns 3 for bottom
//returns 4 for left
	private int sideFinder(Player p)
	{
		boolean isColliding = 	this.x < p.getX() + p.getWidth() &&
								this.x + this.width > p.getX() && 
								this.y < p.getY() + p.getWidth() &&
								this.y + this.width > p.getY();
		if (!isColliding)
		{
			return 0;
		}
		
		
		// Calculate overlap distances
		double topOverlap = Math.abs(this.y + this.width - p.getY());
		double bottomOverlap = Math.abs(p.getY() + p.getWidth() - this.y);
		double leftOverlap = Math.abs(this.x + this.width - p.getX());
		double rightOverlap = Math.abs(p.getX() + p.getWidth() - this.x);

		// Find the side with the smallest overlap
		double minOverlap = Math.min(Math.min(topOverlap, bottomOverlap), Math.min(leftOverlap, rightOverlap));

		if (minOverlap == topOverlap)
		{
			return 1; // Top
		}
		else if (minOverlap == rightOverlap)
		{
			return 2; // Right
		}
		else if (minOverlap == bottomOverlap)
		{
			return 3; // Bottom
		}
		else if (minOverlap == leftOverlap)
		{
			return 4; // Left
		}
		

		return 0; // Fallback (no side detected)

	}

	public List<Bullet> getProjectiles()
	{
		return projectiles;
	}

	public void setProjectiles(List<Bullet> projectiles)
	{
		this.projectiles = projectiles;
	}
	
	public boolean isTouchingWall()
	{
		return touchingWall;
	}


	public void setTouchingWall(boolean touchingWall)
	{
		this.touchingWall = touchingWall;
	}
	
	

	public double calcAngle(double obj1X, double obj2X, double obj1Y, double obj2Y)
	{
		double xDis = obj2X - obj1X;
		double yDis = obj2Y - obj1Y;

		double angle = Math.atan2(yDis, xDis);


		return angle;
	}

	public void render(GraphicsContext gc, double sW, double sH)
	{
		boolean shouldRender = 	this.x > 0 &&
				this.x < sW &&
				this.y > 0 &&
				this.y < sH;
				
		if (shouldRender)
		{
			// enemy
			gc.setFill(this.color);
			gc.fillRect(this.x, this.y, width, width);
	
			// double distance = Math.sqrt(Math.pow(this.x - player.getX(), 2) +
			// Math.pow(this.y - player.getY(), 2));
	
			// health
			gc.setFont(Font.getDefault());
			gc.fillText(String.valueOf(this.hp), x + width, y);
		}
		
		// projectiles
		for (Bullet p : projectiles)
		{
			p.updatePos();
			p.render(gc, sW, sH);
		}

	}
}

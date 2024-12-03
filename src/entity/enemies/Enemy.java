package entity.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bullets.Bullet;
import entity.MovingObject;
import entity.Player;
import entity.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Enemy extends MovingObject
{
	protected int shootingCooldown = 50;
	protected List<Bullet> projectiles = new ArrayList<>();
	protected int projectileSpeed;
	protected Color color;
    private int hp;
    protected double tolerence = 100;
    protected boolean awake = false;


	// private Player player;
	private static final double defaultWidth = 50;
	private static final double defaultSpeed = 2;
	
	private boolean touchingWall = false;





	public Enemy(double x, double y, int hp, int projectileSpeed, Color color)
	{
		super(x, y, defaultWidth, defaultSpeed);
		this.projectileSpeed = projectileSpeed;
		this.color = color;
		this.hp = hp;		
	}
	
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
					shootingCooldown = 150;
				}
			}
			if (shootingCooldown > 0)
			{
				shootingCooldown--;
			}
		}

	}
	

	
	public void move(Player p, double sW, double sH, ArrayList<Wall> walls) {
	    if (awake) {
	        if (isCloseToScreen(sW, sH)) {
	            double newX = this.x;
	            double newY = this.y;

	            // Move horizontally towards the player
	            if (Math.abs(p.getX() - this.x) >= tolerence) {
	                if (p.getX() > this.x) {
	                    newX += speed; // Move right
	                } else {
	                    newX -= speed; // Move left
	                }
	            }

	            // Move vertically towards the player
	            if (Math.abs(p.getY() - this.y) >= tolerence) {
	                if (p.getY() > this.y) {
	                    newY += speed; // Move down
	                } else {
	                    newY -= speed; // Move up
	                }
	            }

	            // Check for wall collisions
	            if (!isColliding(newX, this.y, walls)) {
	                this.x = newX; // Update X if no horizontal collision
	            }
	            if (!isColliding(this.x, newY, walls)) {
	                this.y = newY; // Update Y if no vertical collision
	            }
	        }
	    }
	}
	
	private boolean isColliding(double newX, double newY, ArrayList<Wall> walls) {
	    // Create a bounding box for the enemy at the new position
	    javafx.geometry.Bounds enemyBounds = new javafx.geometry.BoundingBox(newX, newY, this.width, this.width);

	    // Check against each wall's bounding box
	    for (Wall wall : walls) {
	        if (enemyBounds.intersects(wall.getBounds())) {
	            return true; // Collision detected
	        }
	    }
	    return false; // No collision
	}

	
	private int getOffWall() 
	{
		Random random = new Random();
		moveInDirection(random.nextInt(4));
        return 60 + random.nextInt(20); // Move for 30-50 ticks
    }
	
	private void moveInDirection(int direction) {
        switch (direction) {
            case 0 -> y -= speed; // Up
            case 1 -> x += speed; // Right
            case 2 -> y += speed; // Down
            case 3 -> x -= speed; // Left
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
		
	}
	
	private void moveAlongHorizontalWall(Wall w, int direction)
	{
		
		double amount = ((w.getWidth() + this.width) - w.getWidth() + w.getX() - this.x - this.width);
		
		System.out.println("X: " + amount*direction);

		
		this.x += direction * amount;

		
	}
	
	private void printPos()
	{
		System.out.println(x + ", " + y + "\t");
	}


	
	public boolean isWallBetween(Player p, ArrayList<Wall> walls) 
	{
	    // Create a line between the player and the enemy
	    Line lineOfSight = new Line(p.getX(), p.getY(), this.x, this.y);

	    // Check if the line intersects with any wall
	    for (Wall w : walls) 
	    {
	    	
	        if (lineOfSight.intersects(w.getBounds())) {
	            return true; // Wall is blocking the view
	        }
	    }
	    return false; // No wall is blocking the view
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
	

	

	public int getHp()
	{
		return hp;
	}


	public void setHp(int hp)
	{
		this.hp = hp;
	}


	public boolean isAwake()
	{
		return awake;
	}


	public void setAwake(boolean awake)
	{
		this.awake = awake;
	}


	public void render(GraphicsContext gc, double sW, double sH)
	{
		boolean shouldRender = isCloseToScreen(sW, sH);
				
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
	
	


	protected boolean isCloseToScreen(double sW, double sH)
	{
		int buffer = 100;
		return	this.x > -buffer - width &&
				this.x < sW + buffer + width &&
				this.y > -buffer - width &&
				this.y < sH + buffer + width;
	}
}

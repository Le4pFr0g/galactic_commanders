package entity;

import java.util.ArrayList;
import java.util.List;

import bullets.Bullet;
import entity.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import weapons.*;

public class Player extends MovingObject
{

	private static final double defaultWidth = 50;
	private static final double defaultSpeed = 5;

	private int hp;
	private List<Gun> guns = new ArrayList<>();
	private Gun equippedWeapon;
	private boolean isAlive = true;

	public Player(double x, double y, int hp)
	{
		//default speed is 3
		super(x, y, defaultWidth, defaultSpeed);
		this.hp = hp;
		
		addGunsToInventory();
	}
	
	private void addGunsToInventory()
	{
		guns.add(0, new Pistol());
		guns.add(1, new Shotgun());
		guns.add(2, new Chaingun());
		guns.add(3, new RocketLauncher());
	}

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public void move(double x, double y, double sW, double sH, int cameraDistance, ArrayList<Enemy> enemies)
	{
		int side = this.enemeySideFinder(enemies);
		//System.out.println(side);
		
		moveX(x, sW, cameraDistance, side);
		moveY(y, sH, cameraDistance, side);


	}
	
	private void moveX(double x, double sW, int cameraDistance, int side)
	{
		//moving right
		//border
		if (this.x + x + width > sW)
		{
			this.x = sW - width;
		}
		//enemy
		if (side == 4)
		{
			this.x = this.x - this.speed;
		}
		
		//moving left
		//border
		if (this.x + x < 0)
		{
			this.x = 0;
		}
		
		//enemy
		if (side == 2)
		{
			this.x = this.x + this.speed;
		}
		
		// all checks fail, move player like normal
		else
		{
			this.x += x;
		}
	}
	
	private void moveY(double y, double sH, int cameraDistance, int side)
	{
		//moving down
		//border
		if (this.y + width > sH)
		{
			this.y = sH - width;
		}
		//enemy
		if (side == 1)
		{
			this.y = this.y - this.speed;
		}
		
		//moving up
		//border
		if (this.y < 0)
		{
			this.y = 0;
		}
		
		//enemy
		if (side == 3)
		{
			this.y = this.y + this.speed;
		}
		
		// all checks fail, move player like normal
		else
		{
			this.y += y;
		}
	}

//returns 1 for top
//returns 2 for right
//returns 3 for bottom
//returns 4 for left
	private int enemeySideFinder(ArrayList<Enemy> enemies)
	{

		//Enemy enemy = enemies.get(0);

		for (Enemy enemy : enemies)
		{
			boolean isColliding = 	this.x < enemy.getX() + enemy.getWidth() &&
									this.x + this.width > enemy.getX() && 
									this.y < enemy.getY() + enemy.getWidth() &&
									this.y + this.width > enemy.getY();
			if (!isColliding)
			{
				continue;
			}
			
			
			// Calculate overlap distances
			double topOverlap = Math.abs(this.y + this.width - enemy.getY());
			double bottomOverlap = Math.abs(enemy.getY() + enemy.getWidth() - this.y);
			double leftOverlap = Math.abs(this.x + this.width - enemy.getX());
			double rightOverlap = Math.abs(enemy.getX() + enemy.getWidth() - this.x);
	
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
		}

		return 0; // Fallback (no side detected)

	}

	public void swapWeapon(int index)
	{
		if (guns.get(index).isPickedUp())
		{
			equippedWeapon = guns.get(index);
		}

	}

	public void kill(GraphicsContext gc, double sW, double sH)
	{
		gc.setFont(Font.getDefault());
		gc.setFont(Font.font(60));
		gc.setFill(Color.RED);
		String gameOver = "GAME OVER";
		double offset = gameOver.length() / 2;
		gc.fillText(gameOver, sW / 2 - 30 * offset, sH / 2);
		gc.setFont(Font.getDefault());
		isAlive = false;
	}

	public void render(GraphicsContext gc, double sW, double sH)
	{
		
		gc.setFill(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRect(this.x, this.y, width, width);
		
		// display player box
		gc.setFill(Color.WHITE);
		gc.fillRect(this.x, this.y, width, width);

		// display health
		gc.fillText(String.valueOf(this.hp), x + width, y);

		// render smaller red square on player if dead
		if (!isAlive)
		{
			gc.setFill(Color.RED);
			double centerX = x + width / 2;
			double centerY = y + width / 2;
			// width of the inner square is width/2, divide that by 2 to get the location of
			// origin
			double inX = centerX - ((width / 2) / 2);
			double inY = centerY - ((width / 2) / 2);
			gc.fillRect(inX, inY, width / 2, width / 2);
		}

		if (equippedWeapon != null)
		{
			// render ammo for equipped weapon
			gc.fillText(String.valueOf(equippedWeapon.getAmmo()), x - 15, y + width);
			// render every bullet of every gun.
			for (Gun g : guns)
			{
				for (Bullet b : g.getBullets())
				{
					b.render(gc, sW, sH);
				}
			}
		}

	}

	

	public boolean isAlive()
	{
		return isAlive;
	}

	public void setAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}

	public Gun getEquippedWeapon()
	{
		return equippedWeapon;
	}

	public void setEquippedWeapon(Gun equippedWeapon)
	{
		this.equippedWeapon = equippedWeapon;
	}

	public List<Gun> getGuns()
	{
		return guns;
	}

	public void setGuns(List<Gun> guns)
	{
		this.guns = guns;
	}
	

    public static double getDefaultspeed()
	{
		return defaultSpeed;
	}

}

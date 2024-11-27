package entity;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import weapons.Bullet;
import weapons.Gun;
import weapons.Pistol;

public class Player
{
	private double x, y;
	private final double width = 50;
	private List<Gun> guns = new ArrayList<>();
	private Gun equippedWeapon;
	private int hp;
	private boolean isAlive = true;
	
	public Player(double x, double y, int hp)
	{
		this.x = x;
		this.y = y;
//		guns.add(new Gun(15, 2, 10));
//		guns.add(new Gun(40, 7, 10));
//
//		equippedWeapon = guns.get(0);
		this.hp = hp;
	}

	public void move(double x, double y)
	{
		this.x += x;
		this.y += y;
	}
	
	public void swapWeapon(Gun g)
	{
		if (guns.contains(g))
		{
			equippedWeapon = g;
		}
		
	}

	public void kill(GraphicsContext gc, double screenH, double screenW)
	{
		gc.setFont(Font.getDefault());
		gc.setFont(Font.font(60));
		gc.setFill(Color.RED);
		String gameOver = "GAME OVER";
		double offset = gameOver.length()/2;
		gc.fillText(gameOver, screenW/2 - 30*offset, screenH/2);
		gc.setFont(Font.getDefault());
		isAlive = false;
	}
	

	public void render(GraphicsContext gc)
	{

		//display player box
		gc.setFill(Color.GREEN);
		gc.fillRect(this.x, this.y, width, width);

		//display health
		gc.fillText(String.valueOf(this.hp), x + width, y);
		
		//render smaller red square on player if dead
		if (!isAlive)
		{
			gc.setFill(Color.RED);
			double centerX = x + width/2;
			double centerY = y + width/2;
			//width of the inner square is width/2, divide that by 2 to get the location of origin
			double inX = centerX - ((width/2)/2);
			double inY = centerY - ((width/2)/2);
			gc.fillRect(inX, inY , width/2, width/2);
		}

		
		if (equippedWeapon != null)
		{
			//render ammo for equipped weapon
			gc.fillText(String.valueOf(equippedWeapon.getAmmo()), x - 15, y + width);
			//render every bullet of every gun.
			for (Gun g : guns)
			{
				for (Bullet element : g.getBullets())
				{
					element.render(gc);
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

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public double getWidth()
	{
		return width;
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

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

}

//public double calcAngle(double obj1X, double obj2X, double obj1Y, double obj2Y)
//{
//	double xDis = obj2X - obj1X;
//	double yDis = obj2Y - obj1Y;
//
//
//	double angle = Math.atan2(yDis, xDis);
//
//
//	angle = Math.toDegrees(angle);
//
//	return angle;
//}

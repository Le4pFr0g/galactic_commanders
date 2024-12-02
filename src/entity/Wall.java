package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall
{
	protected double x, y;
	protected double width;
	protected double height;
	protected Color color;

	public Wall(double x, double y, double width, double height, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		
		
	}
	
	public void render(GraphicsContext gc, double sW, double sH)
	{
		boolean shouldRender = 	this.x > 0 &&
								this.x < sW &&
								this.y > 0 &&
								this.y < sH;
		if (shouldRender)
		{
			gc.setFill(Color.BLACK);
			gc.setLineWidth(2);
			gc.strokeRect(this.x, this.y, width, height);
			gc.setFill(this.color);
			gc.fillRect(this.x, this.y, width, height);
		}

	}
	
	
	public int checkCollision(MovingObject p)
	{
		int side = sideFinder(p);
		//System.out.println(side);

		
		switch (side)
		{
			case 0: 
				break;
			case 1:
				p.setY(p.getY() + p.getSpeed());
				break;
			case 2:
				p.setX(p.getX() - p.getSpeed());
				break;
			case 3: 
				p.setY(p.getY() - p.getSpeed());
				break;
			case 4:
				p.setX(p.getX() + p.getSpeed());
				break;
			default: break;
		}
		return side;
		
	}
	
	
	
	//same as in player class
//returns 1 for top
//returns 2 for right
//returns 3 for bottom
//returns 4 for left
	private int sideFinder(MovingObject p)
	{
		boolean isColliding = 	this.x < p.getX() + p.getWidth() &&
								this.x + this.width > p.getX() && 
								this.y < p.getY() + p.getWidth() &&
								this.y + this.height > p.getY();
		if (!isColliding)
		{
			return 0;
		}
		
		
		// Calculate overlap distances
		double topOverlap = Math.abs(this.y + this.height - p.getY());
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

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

}

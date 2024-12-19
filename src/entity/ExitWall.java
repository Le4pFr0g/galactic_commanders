package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExitWall extends Wall
{
	static Color exitColor = Color.RED;

	public ExitWall(double x, double y, double width, double height)
	{
		super(x, y, width, height, exitColor);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int checkCollision(MovingObject p)
	{
		int side = sideFinder(p);
		
		return side;
	}
	
	@Override
	public void render(GraphicsContext gc, double sW, double sH)
	{
		int buffer = 100;
		boolean shouldRender = 	this.x > -buffer - width &&
								this.x < sW + buffer + width &&
								this.y > -buffer - height &&
								this.y < sH + buffer + height;
								
		if (shouldRender)
		{
			gc.setFill(this.color);
			gc.fillRect(this.x, this.y, width, height);
		}

	}

}

package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy 
{
	private double x, y;
	private Player player;
	private static final double WIDTH = 50;
	
	public Enemy(Player p, double x, double y)
	{
		this.player = p;
		this.x = x;
		this.y = y;
	}
	
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.RED);
		gc.fillRect(this.x, this.y, WIDTH, WIDTH);
		
		double distance = Math.sqrt(Math.pow(this.x-player.getX(), 2) + Math.pow(this.y-player.getY(), 2));
	}
}

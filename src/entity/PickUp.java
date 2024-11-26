package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PickUp
{
	private double x, y;
	private static final double WIDTH = 30;

	public PickUp(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.MEDIUMPURPLE);
		gc.fillRect(this.x, this.y, WIDTH, WIDTH);
	}

	private void onCollide(Player p)
	{
		p.getGun().setAmmo(p.getGun().getAmmo() + 10);

		tempSpawn(Math.random() * 200, Math.random() * 200);
	}

	public void checkCollision(Player p)
	{
		double dx = this.x - p.getX();
		double dy = this.y - p.getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance < (Player.getWidth() + WIDTH))
		{
			onCollide(p);
		}
	}

	private void tempSpawn(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

}

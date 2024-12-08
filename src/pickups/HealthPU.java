package pickups;

import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HealthPU extends PickUp
{
	private int value;
	private Image img = new Image(getClass().getResource("red_cross.png").toExternalForm());
	
	public HealthPU(double x, double y, int value)
	{
		
		super(x, y, 50, Color.WHITE);
		this.value = value;
		
	}
	
	@Override
	protected void onCollide(Player p)
	{
		p.setHp(p.getHp() + value);
	}
	
	@Override
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRect(this.x, this.y, width, width);
		
		gc.setFill(color);
		gc.fillRect(this.getX(), this.getY(), getWidth(), getWidth());
		gc.drawImage(img, this.x, this.y);
//		gc.setFill(Color.RED);
//		gc.fillRect(this.x + this.width/4, this.y + this.width/4, this.width/3, width/2);
	}

}

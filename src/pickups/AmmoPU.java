package pickups;

import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class AmmoPU extends PickUp
{
	private int weaponID;
	private int value;
	private Image img;


	public AmmoPU(double x, double y, int weaponID, int value, Color color)
	{
		super(x, y, 30, color);
		this.weaponID = weaponID;
		this.value = value;
		
		switch (weaponID)
		{
			case 0:
				img = new Image(getClass().getResource("/images/pistol_mag.png").toExternalForm(), 
						50,  // Desired width
			            50,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
			case 1:
				img = new Image(getClass().getResource("/images/shells.png").toExternalForm(), 
						50,  // Desired width
			            50,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
			case 2:
				img = new Image(getClass().getResource("/images/assault_rifle_mag.png").toExternalForm(), 
						50,  // Desired width
			            50,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
			case 3:
				img = new Image(getClass().getResource("/images/rocket.png").toExternalForm(), 
						50,  // Desired width
			            50,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
		}
		
	}
	
	@Override
	protected void onCollide(Player p)
	{
		p.getGuns().get(weaponID).setAmmo(p.getGuns().get(weaponID).getAmmo() + value);
		
	}

//	@Override
//	public void render(GraphicsContext gc)
//	{
//		gc.drawImage(img, this.x, this.y);
//
//	}
//	

}

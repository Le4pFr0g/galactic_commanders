package pickups;

import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class WeaponPU extends PickUp
{
	private int weaponID;
	private int value;
	private Image img;

	
	public WeaponPU(double x, double y, int weaponID, int value, Color color)
	{
		
		super(x, y, 50, color);
		this.weaponID = weaponID;
		this.value = value;
		
		switch (weaponID)
		{
			case 0:
				img = new Image(getClass().getResource("/images/pistol.png").toExternalForm(), 
						50,  // Desired width
			            50,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
			case 1:
				img = new Image(getClass().getResource("/images/shotgun.png").toExternalForm(), 
						75,  // Desired width
			            75,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
			case 2:
				img = new Image(getClass().getResource("/images/assault_rifle.png").toExternalForm(), 
						100,  // Desired width
			            100,  // Desired height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				break;
			case 3:
				img = new Image(getClass().getResource("/images/rocket_launcer.png").toExternalForm(), 
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
		if (p.getGuns().get(weaponID).isPickedUp())
		{
			p.getGuns().get(weaponID).setAmmo(p.getGuns().get(weaponID).getAmmo() + value);
		}
		else
		{
			p.getGuns().get(weaponID).setPickedUp(true);
			p.swapWeapon(weaponID);
		}
		
	}
	
//	@Override
//	public void render(GraphicsContext gc)
//	{
//		gc.drawImage(img, this.x, this.y);
//
//	}
	
	
	
}

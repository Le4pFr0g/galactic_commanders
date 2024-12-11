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
	//this is only used for creating the supertype, it gets overridden when we know what weapon it is
	private static Color defaultColor = Color.BLACK;

	
	public WeaponPU(double x, double y, int weaponID, int value)
	{
		
		super(x, y, 50, defaultColor);
		this.weaponID = weaponID;
		this.value = value;
		
		switch (weaponID)
		{
			case 0:
				img = new Image(getClass().getResource("/images/pistol.png").toExternalForm(), 
						50,  //width
			            50,  //height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				this.color = Color.DARKSLATEGRAY;
				break;
			case 1:
				img = new Image(getClass().getResource("/images/shotgun.png").toExternalForm(), 
						75,  //width
			            75,  //height
			            true,  //Preserve aspect ratio
			            true   //Smooth scaling
			            );
				this.color = Color.PURPLE;

				break;
			case 2:
				img = new Image(getClass().getResource("/images/assault_rifle.png").toExternalForm(), 
						100,  //width
			            100,  //height
			            true,  //Preserve aspect ratio
			            true   //Smooth scaling
			            );
				this.color = Color.DARKBLUE;

				break;
			case 3:
				img = new Image(getClass().getResource("/images/rocket_launcer.png").toExternalForm(), 
						50,  //width
			            50,  //height
			            true,  // Preserve aspect ratio
			            true   // Smooth scaling
			            );
				this.color = Color.DARKORANGE;

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

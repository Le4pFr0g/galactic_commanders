package pickups;

import entity.Player;
import javafx.scene.paint.Color;

public class AmmoPU extends PickUp
{
	private int weaponID;
	private int value;

	public AmmoPU(double x, double y, int weaponID, int value, Color color)
	{
		super(x, y, 30, color);
		this.weaponID = weaponID;
		this.value = value;
		
	}
	
	@Override
	protected void onCollide(Player p)
	{
		p.getGuns().get(weaponID).setAmmo(p.getGuns().get(weaponID).getAmmo() + value);
		
	}

	
	

}

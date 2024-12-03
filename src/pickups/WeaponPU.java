package pickups;

import entity.Player;
import javafx.scene.paint.Color;
import weapons.*;

public class WeaponPU extends PickUp
{
	private int weaponID;
	private int value;
	
	public WeaponPU(double x, double y, int weaponID, int value, Color color)
	{
		
		super(x, y, 50, color);
		this.weaponID = weaponID;
		this.value = value;
		
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
	
	
	
}

package pickups;

import entity.Player;
import javafx.scene.paint.Color;
import weapons.*;

public class WeaponPU extends PickUp
{
	private Gun weapon;
	
	
	public WeaponPU(double x, double y, int weaponID, Color color)
	{
		
		super(x, y, color);
		assignWeapon(weaponID);
		
	}
	
	private void assignWeapon(int ID)
	{
		if (ID == 1)
		{
			weapon = new Pistol();
		}
		
//		if (ID == 1)
//		{
//			weapon = new Pistol();
//		}
		
		
	}

	protected void onCollide(Player p)
	{
		
		p.getGuns().add(weapon);
		p.swapWeapon(p.getGuns().get(1));

	}
	
	
	
}

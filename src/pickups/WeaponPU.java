package pickups;

import entity.Player;
import javafx.scene.paint.Color;
import weapons.*;

public class WeaponPU extends PickUp
{
	private Gun weapon;
	private int weaponID;
	
	
	public WeaponPU(double x, double y, int weaponID, Color color)
	{
		
		super(x, y, color);
		this.weaponID = weaponID;

		assignWeapon(weaponID);
		
	}
	
	private void assignWeapon(int ID)
	{
		if (ID == 1)
		{
			weapon = new Pistol();
		}
		if (ID == 2)
		{
			weapon = new Shotgun();
		}
		
//		if (ID == 1)
//		{
//			weapon = new Pistol();
//		}
		
		
	}

	protected void onCollide(Player p)
	{
		
		//p.getGuns().add(weaponID, weapon);
		p.getGuns().add(weapon);
		if (p.getGuns().contains(weapon))
		{
			p.swapWeapon(weapon);
		}
	}
	
	
	
}

package pickups;

import entity.Player;
import javafx.scene.paint.Color;

public class AmmoPU extends PickUp
{
	private int weaponID;
	private int value;

	public AmmoPU(double x, double y, int weaponID, int value, Color color)
	{
		super(x, y, color);
		this.weaponID = weaponID;
		this.value = value;
		
	}
	
	protected void onCollide(Player p)
	{
		if (p.getEquippedWeapon() != null)
		p.getGuns().get(weaponID).setAmmo(p.getGuns().get(weaponID).getAmmo() + value);
		
		tempSpawn(Math.random() * 200, Math.random() * 200);

	}

	
	

}

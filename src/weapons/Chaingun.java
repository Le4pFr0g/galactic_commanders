package weapons;

public class Chaingun extends Gun
{
	//full auto
	static int dmg = 20;
	static int fireRate = 10;
	static int bSpeed = 5;
	static int defaultAmmo = 10;

	public Chaingun()
	{
		super(dmg, fireRate, bSpeed, defaultAmmo, false);
	}

}

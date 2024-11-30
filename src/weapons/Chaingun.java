package weapons;

public class Chaingun extends Gun
{
	//full auto
	static int fireRate = 10;
	static int bSpeed = 20;
	static int defaultAmmo = 10;

	public Chaingun()
	{
		super(fireRate, bSpeed, defaultAmmo, false);
	}

}

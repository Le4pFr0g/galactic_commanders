package weapons;

public class Pistol extends Gun
{
	static final int damage = 5;
	
	//this should allow the pistol to fire every 15 frames
	static int fireRate = 15;
	static int bSpeed = 2;
	static int defaultAmmo = 10;
	

	public Pistol()
	{
		super(fireRate, bSpeed, defaultAmmo);

	}

}

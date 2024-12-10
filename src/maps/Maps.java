package maps;

import java.util.ArrayList;

import entity.ExitWall;
import entity.Player;
import entity.Wall;
import entity.enemies.AssaultTrooper;
import entity.enemies.Blob;
import entity.enemies.Enemy;
import javafx.scene.paint.Color;
import pickups.AmmoPU;
import pickups.HealthPU;
import pickups.WeaponPU;

public class Maps
{

	private static void addMapBorder(ArrayList<Wall> walls, double sW, double sH)
	{
		//Vertical
		//left
		walls.add(new Wall(0, 50, 50, 2*sH, Color.GRAY));
		
		//right
		walls.add(new Wall(2*sW, 0, 50, 2*sH, Color.GRAY));
		
		//Horizontal
		//top
		walls.add(new Wall(0, 0, 2*sW, 50, Color.GRAY));
		//bottom
		walls.add(new Wall(0, 2*sH, 2*sW, 50, Color.GRAY));
		
	}
	
	
	public static void createMap1(double sW, double sH, Player player, ArrayList<Wall> walls, ArrayList<Enemy> enemies, ArrayList<HealthPU> healthPUs, ArrayList<WeaponPU> weaponPUs, ArrayList<AmmoPU> ammoPUs)
	{
		addMapBorder(walls, sW, sH);
		
		//Vertical
		walls.add(new Wall(sW + 300, 50, 50, 2*sH, Color.GRAY));
		
		
		walls.add(new Wall(sW/2 - 200, 0, 50, sH, Color.GRAY));
		walls.add(new Wall(sW/2 - 200, sH+300, 50, sH-300, Color.GRAY));
		
		walls.add(new Wall(sW - 50, sH/2 - 200, 50, 400, Color.GRAY));
		
		
		//Horizontal
		walls.add(new Wall(sW-500, sH+250, 800, 50, Color.GRAY));
		
		
		walls.add(new Wall(sW/2 - 300, sH, 150, 50, Color.GRAY));
		walls.add(new Wall(sW/2 - 200, sH+250, 150, 50, Color.GRAY));
		
		
		walls.add(new Wall(sW/2-150, sH/2, 150, 50, Color.GRAY));
		walls.add(new Wall(sW, sH/2, 300, 50, Color.GRAY));
		
		
		
		//weapons
		int offset = 75;
		ammoPUs.add(new AmmoPU(sW/2 - 200 + offset, sH+300 + offset, 0, 10, Color.PURPLE));
		ammoPUs.add(new AmmoPU(680, 330, 1, 10, Color.DARKBLUE));
		ammoPUs.add(new AmmoPU(sW/2 - 300 + offset/2, sH - 2*offset, 2, 10, Color.GREEN));
		ammoPUs.add(new AmmoPU(100 + offset, sH*2 - 100, 3, 10, Color.MEDIUMPURPLE));
		
		//ammo
		weaponPUs.add(new WeaponPU(sW/2 - 200 + offset, sH+300 + offset/4, 0, 10, Color.PURPLE));
		weaponPUs.add(new WeaponPU(680, 380, 1, 10, Color.DARKBLUE));
		weaponPUs.add(new WeaponPU(sW/2 - 300 + offset/2, sH - offset, 2, 10, Color.GREEN));
		weaponPUs.add(new WeaponPU(100, sH*2 - 100, 3, 10, Color.rgb(255, 255, 255)));
		
		enemies.add(new Enemy(900, 150, 100, 5, Color.RED));// Color.rgb(255, 0, (int)(Math.random()*255))));
		
		
		enemies.add(new AssaultTrooper(900, sH - 100));// Color.rgb(255, 0, (int)(Math.random()*255))));
		
		enemies.add(new Blob(900, sH*1.5));// Color.rgb(255, 0, (int)(Math.random()*255))));
			
			
			
	}
	
	



	
	public static void createMap2(double sW, double sH, Player player, ArrayList<Wall> walls, ArrayList<Enemy> enemies, ArrayList<HealthPU> healthPUs, ArrayList<WeaponPU> weaponPUs, ArrayList<AmmoPU> ammoPUs)
	{
		addMapBorder(walls, 1950, 1100);
		walls.add(0, new ExitWall(450, 50, 200, 200));
		player.setX(250);
		player.setY(100);
		
		//Vertical all x pos values offset around map border
		walls.add(new Wall(500, 50, 100, 600, Color.GRAY));
		walls.add(new Wall(850, 200, 100, 600, Color.GRAY));
		walls.add(new Wall(1250, 200, 100, 300, Color.GRAY));
		walls.add(new Wall(1550, 50, 100, 1350, Color.GRAY));
		walls.add(new Wall(50, 1500, 300, 350, Color.GRAY));
		walls.add(new Wall(1550, 1600, 100, 600, Color.GRAY));
		walls.add(new Wall(2050, 1300, 100, 400, Color.GRAY));
		walls.add(new Wall(1750, 1050, 100, 300, Color.GRAY));
		walls.add(new Wall(2350, 1050, 100, 650, Color.GRAY));
		walls.add(new Wall(2850, 1350, 100, 600, Color.GRAY));
		walls.add(new Wall(2650, 750, 100, 300, Color.GRAY));
		walls.add(new Wall(2950, 550, 100, 300, Color.GRAY));
		walls.add(new Wall(3450, 450, 100, 800, Color.GRAY));

		

		
		
		//Horizontal
		walls.add(new Wall(50, 800, 900, 100, Color.GRAY));
		walls.add(new Wall(950, 400, 300, 100, Color.GRAY));
		walls.add(new Wall(1150, 800, 400, 100, Color.GRAY));
		walls.add(new Wall(50, 1850, 1500, 350, Color.GRAY));
		walls.add(new Wall(1650, 1600, 400, 100, Color.GRAY));
		walls.add(new Wall(1650, 1300, 200, 100, Color.GRAY));
		walls.add(new Wall(1750, 1050, 600, 100, Color.GRAY));
		walls.add(new Wall(1850, 1950, 1300, 250, Color.GRAY));
		walls.add(new Wall(2950, 1500, 600, 100, Color.GRAY));
		walls.add(new Wall(2650, 1050, 300, 100, Color.GRAY));
		walls.add(new Wall(2650, 1050, 300, 100, Color.GRAY));
		walls.add(new Wall(2450, 250, 600, 300, Color.GRAY));
		walls.add(new Wall(3450, 1950, 450, 250, Color.GRAY));





		//fill in gaps
		walls.add(new Wall(50, 900, 500, 600, Color.GRAY));
		walls.add(new Wall(1650, 50, 800, 800, Color.GRAY));


		int weaponOffset = 10; //used to align ammo with weapons in a line
		int offset = 25;
		//health
		healthPUs.add(new HealthPU(750 + offset, 700 + offset, 20));
		healthPUs.add(new HealthPU(1450 + offset, 750 - offset, 20));
		healthPUs.add(new HealthPU(2100 - offset, 1200, 20));
		healthPUs.add(new HealthPU(2800 - offset, 950 + offset, 20));
		healthPUs.add(new HealthPU(2200, 950, 20));
		healthPUs.add(new HealthPU(2750 + offset, 1850 + offset, 20));
		healthPUs.add(new HealthPU(3650, 650 + offset, 20));
		healthPUs.add(new HealthPU(3650, 850 + offset, 20));


		
		
		//ammo
		//pistol
		ammoPUs.add(new AmmoPU(250, 300, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(250, 350, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(250, 500, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(250, 550, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(700 + 10, 300, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(1000, 300 + offset + weaponOffset, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(1200, 300 + offset + weaponOffset, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(1050, 300 + offset + weaponOffset, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(1150, 300 + offset + weaponOffset, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(1150, 300 + offset + weaponOffset, 0, 10, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(1700, 2100, 0, 30, Color.DARKSLATEGRAY));
		ammoPUs.add(new AmmoPU(3850, 50 + offset, 0, 30, Color.DARKSLATEGRAY));

		
		
		//shotgun
		ammoPUs.add(new AmmoPU(400, 1550, 1, 10, Color.PURPLE));
		ammoPUs.add(new AmmoPU(400, 1750, 1, 10, Color.PURPLE));
		ammoPUs.add(new AmmoPU(1800, 2100, 1, 20, Color.PURPLE));
		
		//assault rifle
		ammoPUs.add(new AmmoPU(2950 + offset, 1650, 2, 20, Color.DARKBLUE));
		ammoPUs.add(new AmmoPU(2950 + offset, 1850, 2, 20, Color.DARKBLUE));
		ammoPUs.add(new AmmoPU(1700 - weaponOffset, 1250 - offset, 2, 50, Color.DARKBLUE));




		

//		//weapons
		weaponPUs.add(new WeaponPU(250 - weaponOffset, 400, 0, 20, Color.DARKSLATEGRAY));
		weaponPUs.add(new WeaponPU(400 - weaponOffset, 1650, 1, 10, Color.PURPLE));
		weaponPUs.add(new WeaponPU(2950 + offset - weaponOffset, 1750 - weaponOffset, 2, 20, Color.DARKBLUE));

		
		//enemies
		//simple enemy
		enemies.add(new Enemy(700, 400, 100, 5, Color.RED));
		enemies.add(new Enemy(650, 1000, 100, 5, Color.RED));
		enemies.add(new Enemy(1400, 1000, 100, 5, Color.RED));
		enemies.add(new Enemy(1400, 1800, 100, 5, Color.RED));
		enemies.add(new Enemy(1400, 1800, 100, 5, Color.RED));
		enemies.add(new Enemy(1800, 1450, 100, 5, Color.RED));
		enemies.add(new Enemy(2650, 1800, 100, 5, Color.RED));
		enemies.add(new Enemy(1900, 950, 100, 5, Color.RED));
		enemies.add(new Enemy(3650, 550, 100, 5, Color.RED));
		enemies.add(new Enemy(3650, 1050, 100, 5, Color.RED));		





//		
////		rapid fire enemy
		enemies.add(new AssaultTrooper(1100 - weaponOffset, 200));
		enemies.add(new AssaultTrooper(450, 1700));
		enemies.add(new AssaultTrooper(2250, 1400));
		enemies.add(new AssaultTrooper(2750, 100));
		enemies.add(new AssaultTrooper(3200, 950));
		enemies.add(new AssaultTrooper(2750, 100));
		enemies.add(new AssaultTrooper(3700, 800));



//		
////		multishot enemy
		enemies.add(new Blob(3250, 2100));
			
			
			
	}

	

}

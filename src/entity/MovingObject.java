package entity;

public class MovingObject
{
    protected double x, y;
    protected int hp;
    protected double speed;
    protected double width;

    // Constructor
    public MovingObject(double x, double y, double width, int hp, double speed) 
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.hp = hp;
        this.speed = speed;
    }
    
    
    
    
    public double getWidth()
	{
		return width;
	}
    
    public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}
	
	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}


}

package rougelikeLibrary;

public class Player extends LivingObject {https://github.com/benaveld/INTE_Roglike/pull/10/conflict?name=src%252FrougelikeLibrary%252FPlayer.java&ancestor_oid=ff4d697f54af6eab1785cde8cff7606fbf4732da&base_oid=e53339e7fe68426ed7c59e2665833b08b39bdc4a&head_oid=d06ae1d9a81903bca59bf3d342e1008a3a5aad58

	private int posX;
	private int posY;
  
	public Player(int speed, int health){
		super(speed, health);
		
	}
  
  public void setPosistion(int x, int y)
	{
		if (x < 0 || y < 0)
		{
			throw new IllegalArgumentException("Can't have negative posistion on either X-axis or Y-axis");
		}
		this.posX = x;
		this.posY = y;
	}
	
	public int getPosX()
	{
		return posX;
	}
	
	public int getPosY()
	{
		return posY;
	}
}

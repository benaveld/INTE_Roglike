package rougelikeLibrary;

public class Player extends LivingObject {

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

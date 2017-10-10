package rougelikeLibrary;

public class Player {

	private int health;
	private int speed;
	private int posX;
	private int posY;
	
	public Player(int speed, int health){
		
		if(speed < 0 || health < 0){
			
			throw new IllegalArgumentException("Speed and health both need to be 0 or more");			
		}
		else{
		this.speed = speed;
		this.health = health;
		}
	}
	
	
	
	public void takeDamage(int damge) {
		if(damge < 0) throw new IllegalArgumentException("Can't take negativ damge.");
		health -= damge;
	}

  
  public int getSpeed(){
		
		return speed;
		
	}
  
	public int getHealth(){
		
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
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

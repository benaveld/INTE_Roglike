package rougelikeLibrary;

public class Player {

	private int health = 0;
	private int speed = 0;
	
	public Player(int speed){
		if(speed < 0){
			
			throw new IllegalArgumentException("Speed needs to both be more than 0");			
		}
		else{
		this.speed = speed;
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
}

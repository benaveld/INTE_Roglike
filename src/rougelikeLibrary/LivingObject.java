package rougelikeLibrary;

public class LivingObject {

	private int health;
	private int speed;
	
	public LivingObject(int speed, int health){
		
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
}

	
	
}

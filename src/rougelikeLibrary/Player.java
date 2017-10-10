package rougelikeLibrary;

public class Player extends LivingObject {

<<<<<<< HEAD
	
	
	public Player(int speed, int health){
		
=======
	private int speed;
	private int health;
	
	
	public Player(int speed, int health){
		
		if(speed < 0 || health < 0){
			
			throw new IllegalArgumentException("Speed and health both need to be 0 or more");			
		}
		else{
		this.speed = speed;
		this.health = health;
		}
	}
	
	
	
	public void takeDamage(int damage) {
		if(damage < 0){
			throw new IllegalArgumentException("Can't take negative damage.");
		}
		health -= damage;
		if(health < 0){
			health = 0;
		}
	}

  
  public int getSpeed(){
		
		return speed;
		
	}
  
	public int getHealth(){
		
		return health;
>>>>>>> refs/remotes/origin/master
	}
}

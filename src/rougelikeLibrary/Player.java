package rougelikeLibrary;

public class Player {

	private int health = 0;
	
	
	public Player(){}
	
	public void takeDamage(int damge) {
		if(damge < 0) throw new IllegalArgumentException("Can't take negativ damge.");
		health -= damge;
	}
	
	public int getHealth(){
		
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
}

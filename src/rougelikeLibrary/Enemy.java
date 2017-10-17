package rougelikeLibrary;

public class Enemy extends Character {

	public Enemy(int speed, int health, int damage, TurnSystem ts) {
		super(speed, health, damage, ts);
	}
	public Enemy(int speed, int health, int damage, Position position, TurnSystem ts) {
		super(speed, health, damage, position, ts);
		
		
	}
	public Enemy(int speed, int health, int damage, int x, int y, TurnSystem ts) {
		super(speed, health, damage, x, y, ts);		
	}
	
}

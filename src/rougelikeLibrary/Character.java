package rougelikeLibrary;

public class Character {

	private int speed;
	private int health;
	private int damage;

	public Character(int speed, int health, int damage) {
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		
		this.speed = speed;
		this.health = health;
		this.damage = damage;

	}

	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Can't take negative damage.");
		}
		health -= damage;
		if (health < 0) {
			health = 0;
		}
	}

	public int getSpeed() {
		return speed;

	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}
	
}

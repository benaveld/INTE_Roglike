package rougelikeLibrary;

import java.awt.Point;

public class Character implements Mappable {
	private Point point;
	private int speed;
	private int health;
	private int damage;
	private TurnSystem ts;

	public Character(int speed, int health, int damage, TurnSystem ts) {
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		point = new Point();
	}
	
	public Character(int speed, int health, int damage, Point point, TurnSystem ts) {
		this(speed, health, damage, ts);
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		this.point = point;
	}
	
	public Character(int speed, int health, int damage, int x, int y, TurnSystem ts) {
		this(speed, health, damage, new Point(x,y), ts);
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
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

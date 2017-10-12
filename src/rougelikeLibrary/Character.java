package rougelikeLibrary;

import java.awt.Point;

public class Character implements Mappable {
	private Point point;
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
		point = new Point();
	}
	
	public Character(int speed, int health, int damage, Point point) {
		this(speed, health, damage);
		this.point = point;
	}
	
	public Character(int speed, int health, int damage, int x, int y) {
		this(speed, health, damage, new Point(x,y));
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
	
	@Override
	public void setPoint(Point point) {
		this.point.setLocation(point);
	}

	@Override
	public void setPoint(int x, int y) {
		point.setLocation(x, y);
	}

	@Override
	public Point getPoint() {
		return point;
	}

}

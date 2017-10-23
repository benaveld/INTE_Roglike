package rougelikeLibrary;


import java.util.HashMap;
import java.util.List;

public class Character implements Mappable {
	private Position pos;
	private int speed;
	private int health;
	private int damage;
	private final TurnSystem ts;
	private final Inventory inv;
	private boolean isDead = true;

	
	
	public Character(int speed, int health, int damage, Position Position, TurnSystem ts) {
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		this.pos = Position;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.ts = ts;
		inv = new Inventory();
		if(health > 0) {
			isDead = false;
		}
	}
	
	
	
	public boolean startTurn(Room room)
	{
		return ts.startTurn(this, speed, room);
	}

	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Can't take negative damage.");
		}
		health -= damage;
		if (health <= 0) {
			health = 0;
			isDead = true;
		}
	}

	public int getSpeed() {
		return speed;
	}

	public int getHealth() {
		return health;
	}
	public void setHealth(int h) {
		health = h;
	}
	public int getDamage() {
		return damage;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public void setPosition(int x, int y) {
		pos = new Position(x,y);
	}

	public Position getPosition() {
		return pos;
	}
	public Inventory getInventory() {
		return inv;
	}
	public boolean isDead() {
		return isDead;
	}
	public void kill() {
		isDead = true;
		health = 0;
	}
	@Override
	public String toString() {
		
		return speed + " " + health + " "+ damage;
	}
}

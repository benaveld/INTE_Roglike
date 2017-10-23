package rougelikeLibrary;


import java.util.HashMap;
import java.util.List;

public class Character implements Mappable {
	private Position pos;
	private int speed;
	private int originalHealth;
	private int totalHealth;
	private int currentHealth;
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
		this.originalHealth = health;
		this.damage = damage;
		this.ts = ts;
		inv = new Inventory();
		if(health > 0) {
			isDead = false;
		}
	}
	
	public void calculateHealth() {
		
		int diff = totalHealth - currentHealth;
		totalHealth = originalHealth + inv.getTotalValues().get(Item.Effect.HEALTH);
		currentHealth = totalHealth - diff;
		if(currentHealth <= 0) {
			isDead = true;
		}
	}
	
	public boolean startTurn(Room room)
	{
		calculateHealth();
		if(isDead) {
			
			return false;
		}
		else {
			return ts.startTurn(this, getSpeed(), room);
		}
	}

	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Can't take negative damage.");
		}
		currentHealth -= damage;
		if (currentHealth <= 0) {
			currentHealth = 0;
			isDead = true;
		}
	}

	public int getSpeed() {
		return speed + inv.getTotalValues().get(Item.Effect.SPEED);
	}

	public int getHealth() {
		calculateHealth();
		return currentHealth;
	}
	public void setHealth(int h) {
		originalHealth = h;
		totalHealth = h;
		currentHealth = h;
	}
	public int getDamage() {
		return damage + inv.getTotalValues().get(Item.Effect.DAMAGE);
		
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
		calculateHealth();
		return isDead;
	}
	
	@Override
	public String toString() {
		
		return speed + " " + currentHealth + " "+ damage;
	}
}

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

	
	 /**
     * Constructor
     *
     * @param The Speed of the Character.
     * @param The Health of the Character.
     * @param The Damage of the Character.
     * @param The Position of the Character.
     * @param The TurnSystem associated with the Character.
     */
	public Character(int speed, int health, int damage, Position Position, TurnSystem ts) {
		
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		this.pos = Position;
		this.speed = speed;
		this.currentHealth = health;
		this.totalHealth = health;
		this.originalHealth = health;
		this.damage = damage;
		this.ts = ts;
		inv = new Inventory();
		if(health > 0) {
			isDead = false;
		}
	}
	/**
	 * Calculates the health of the Character based on what items are currently on it.
	 */
	private void calculateHealth() {
		
		int diff = totalHealth - currentHealth;
		if(inv.getTotalValues().get(Item.Effect.HEALTH) != null) {
			totalHealth = originalHealth + inv.getTotalValues().get(Item.Effect.HEALTH);
			
		}
		else {
			totalHealth = originalHealth;
		}
		currentHealth = totalHealth - diff;
		if(currentHealth <= 0) {
			isDead = true;
		}
	}
	/**
	 * Starts the turn for the Character
	 * @param The room the Character is in
	 * @return True if the TurnSystem returns true.
	 */
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
	/**
	 * Decreases the Characters health by the damage taken
	 * @param The amount of damage taken
	 */

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

	/**
	 * 
	 * @return The Character's speed 
	 */
	public int getSpeed() {
		if(inv.getTotalValues().get(Item.Effect.SPEED) != null) {
			
			return speed + inv.getTotalValues().get(Item.Effect.SPEED);
		}
		else {
			return speed;
		}
		
	}

	/**
	 * 
	 * @return The Character's health
	 */
	public int getHealth() {
		calculateHealth();
		return currentHealth;
	}
	/**
	 * Sets all health related variables to the paramater
	 * @param The health you want to set to the Character
	 */
	public void setHealth(int h) {
		originalHealth = h;
		totalHealth = h;
		currentHealth = h;
	}
	/**
	 * 
	 * @return The Characters damage
	 */
	public int getDamage() {
		if(inv.getTotalValues().get(Item.Effect.DAMAGE) != null) {
			return damage + inv.getTotalValues().get(Item.Effect.DAMAGE);
			
		}
		else {
			return damage;
		}
		
	}
	/**
	 * Sets the Character's Position to the paramater
	 * @param A position.
	 */
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	/**
	 * 
	 * @param x value
	 * @param y value
	 */
	public void setPosition(int x, int y) {
		pos = new Position(x,y);
	}
	/**
	 * 
	 * @return The Character's position
	 */
	public Position getPosition() {
		return pos;
	}
	/**
	 * 
	 * @return The Character's Inventory
	 */
	public Inventory getInventory() {
		return inv;
	}
	/**
	 * 
	 * @return True if the Character is dead.
	 */
	public boolean isDead() {
		calculateHealth();
		return isDead;
	}
	
	@Override
	public String toString() {
		
		return speed + " " + currentHealth + " "+ damage;
	}
}

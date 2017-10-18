package rougelikeLibrary;

import java.util.*;

public class Enemy extends Character {

	private Room currentRoom;
	
	public Enemy(int speed, int health, int damage, TurnSystem ts) {
		super(speed, health, damage, ts);
		
	}
	public Enemy(int speed, int health, int damage, Position position, TurnSystem ts) {
		super(speed, health, damage, position, ts);
		
		
	}
	public Enemy(int speed, int health, int damage, int x, int y, TurnSystem ts) {
		super(speed, health, damage, x, y, ts);		
		
	}	
	public void setRoom(Room r) {
		currentRoom = r;
	}
	private void dropItems() {
		Position here = this.getPosition();
		
		ArrayList<Item> items = this.getInventory().getItems();
		List<Mappable> m = currentRoom.getFromPosition(here);
		m.remove(this);
		if(!items.isEmpty()) {
			for(Item i : items) {
				currentRoom.addItem(here, i);
			}
		}
	}
	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Can't take negative damage.");
		}
		int health = getHealth() - damage;
		setHealth(health);
		if (health <= 0) {
			health = 0;
			dropItems();
			kill();
		}
	}
	
}

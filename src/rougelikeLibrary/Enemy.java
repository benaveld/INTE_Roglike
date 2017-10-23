package rougelikeLibrary;

import java.util.*;

public class Enemy extends Character {

	private Room currentRoom;
	
	
	public Enemy(int speed, int health, int damage, Position position, TurnSystem ts) {
		super(speed, health, damage, position, ts);
		
		
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
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		if (getHealth() <= 0) {
			dropItems();
		}
	}
	
}

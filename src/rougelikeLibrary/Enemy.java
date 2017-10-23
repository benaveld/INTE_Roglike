package rougelikeLibrary;

import java.util.*;

public class Enemy extends Character {

	private Room currentRoom;
	
	
	public Enemy(int speed, int health, int damage, Position position, TurnSystem ts) {
		super(speed, health, damage, position, ts);
		
		
	}
		/**
		 * 
		 * @param Sets what Room the Enemy exists in
		 */
	public void setRoom(Room r) {
		currentRoom = r;
	}
	/**
	 * Drops all items on the position that the enemy was on when it died and also removes the enemy from that position.
	 */
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
	/**
	 * If the enemy dies when taking damage it drops all it's items
	 */
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		if (getHealth() <= 0) {
			dropItems();
		}
	}
	
}

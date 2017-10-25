package rougelikeLibrary;

import java.util.ArrayList;
import java.util.TreeMap;
public class Inventory {

	private final TreeMap<Item.Effect, Integer> values = new TreeMap<Item.Effect, Integer>();
	private final ArrayList<Item> items = new ArrayList<Item>();
	private final TreeMap<Item.Effect, ArrayList<Item>> itemsByStat = new TreeMap<Item.Effect, ArrayList<Item>>();
	
	/**
	 * Constructor
	 * 
	 */
	public Inventory(){
		
		
	}
	/**
	 * 
	 * Calculates the total values for all items in the inventory combined
	 * and sets them to the values TreeMap so that they can be accessed by stat.
	 */
	private void calculateItemValues(){
		
		ArrayList<Item> speedItems = getItemsByStat(Item.Effect.SPEED);
		ArrayList<Item> healthItems = getItemsByStat(Item.Effect.HEALTH);
		ArrayList<Item> damageItems = getItemsByStat(Item.Effect.DAMAGE);
		int value = 0;
		if(speedItems != null) {
			for(Item i : speedItems){
				value += i.getValue();
				
			}
			values.put(Item.Effect.SPEED, value);
		}
		value = 0;
		if(healthItems != null) {
		for(Item i : healthItems){
			value += i.getValue();
			
		}
		values.put(Item.Effect.HEALTH, value);
		}
		value = 0;
		if(damageItems != null) {
			for(Item i : damageItems){
				value += i.getValue();
				
			}
			values.put(Item.Effect.DAMAGE, value);
		}
	}
	/**
	 * 
	 * @return A TreeMap of the values of each stat mapped against that stat.
	 */
	public TreeMap<Item.Effect, Integer> getTotalValues(){
		
		calculateItemValues();
		
		return values;
	}
	/**
	 * Removes an item from the inventory
	 * @param item to remove.
	 */
	public void remove(Item i){
		items.remove(i);
		
	}
	/**
	 * Adds an item to the inventory
	 * @param Item to add
	 */
	public void add(Item i){
		
		items.add(i);
		
		if(itemsByStat.containsKey(i.getEffect())){
			
			ArrayList<Item> temp = itemsByStat.get(i.getEffect());
			temp.add(i);
			itemsByStat.put(i.getEffect(), temp);
			
			
		}
		else{
			ArrayList<Item> temp = new ArrayList<Item>();
			temp.add(i);
			itemsByStat.put(i.getEffect(), temp);
		}
	}
	/**
	 * 
	 * @return An ArrayList of all Items in the Inventory.
	 */
	public ArrayList<Item> getItems(){
		
		return items;
	}
	/**
	 * 
	 * @param The index of an item in the ArrayList of Items
	 * @return An Item with the index specified
	 */
	public Item getItem(int index){
		
		return items.get(index);
	}
	/**
	 * Removes all Items from the Inventory
	 */
	public void removeAllItems(){
		
		items.clear();
	}
	/**
	 * 
	 * @param An Item effect
	 * @return All items with the specified effect
	 */
	public ArrayList<Item> getItemsByStat(Item.Effect effect){
		
		ArrayList<Item> temp = itemsByStat.get(effect);
		
		return temp;
		
	}
	
	
	@Override
	public String toString(){
		String out = "";
		for(Item i : items){
			
			out += i + "\n";
		}
		return out;
	}
	
}

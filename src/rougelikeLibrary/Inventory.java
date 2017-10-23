package rougelikeLibrary;

import java.util.ArrayList;
import java.util.TreeMap;
public class Inventory {

	private final TreeMap<Item.Effect, Integer> values = new TreeMap<Item.Effect, Integer>();
	private final ArrayList<Item> items = new ArrayList<Item>();
	private final TreeMap<Item.Effect, ArrayList<Item>> itemsByStat = new TreeMap<Item.Effect, ArrayList<Item>>();
	
	public Inventory(){
		
		
	}
	
	private void calculateItemValues(){
		
		ArrayList<Item> speedItems = getItemsByStat(Item.Effect.SPEED);
		ArrayList<Item> healthItems = getItemsByStat(Item.Effect.HEALTH);
		ArrayList<Item> damageItems = getItemsByStat(Item.Effect.DAMAGE);
		int value = 0;
		for(Item i : speedItems){
			value += i.getValue();
			
		}
		values.put(Item.Effect.SPEED, value);
		value = 0;
		for(Item i : healthItems){
			value += i.getValue();
			
		}
		values.put(Item.Effect.HEALTH, value);
		value = 0;
		for(Item i : damageItems){
			value += i.getValue();
			
		}
		values.put(Item.Effect.DAMAGE, value);
	}
	public TreeMap<Item.Effect, Integer> getTotalValues(){
		
		calculateItemValues();
		
		return values;
	}
	
	public void remove(Item i){
		items.remove(i);
		
	}
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
	
	public ArrayList<Item> getItems(){
		
		return items;
	}
	public Item getItem(int index){
		
		return items.get(index);
	}
	public void removeAllItems(){
		
		items.clear();
	}
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

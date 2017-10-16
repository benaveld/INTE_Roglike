package rougelikeLibrary;

import java.util.ArrayList;
import java.util.TreeMap;
public class Inventory {

	private TreeMap<Item.Effect, Integer> percentages = new TreeMap<Item.Effect, Integer>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private TreeMap<Item.Effect, ArrayList<Item>> itemsByStat = new TreeMap<Item.Effect, ArrayList<Item>>();
	
	public Inventory(){
		
		
	}
	
	private void calculateItemPercentages(){
		
		ArrayList<Item> speedItems = getItemsByStat(Item.Effect.SPEED);
		ArrayList<Item> healthItems = getItemsByStat(Item.Effect.HEALTH);
		ArrayList<Item> damageItems = getItemsByStat(Item.Effect.DAMAGE);
		int percent = 0;
		for(Item i : speedItems){
			percent += i.getPercentage();
			
		}
		percentages.put(Item.Effect.SPEED, percent);
		percent = 0;
		for(Item i : healthItems){
			percent += i.getPercentage();
			
		}
		percentages.put(Item.Effect.HEALTH, percent);
		percent = 0;
		for(Item i : damageItems){
			percent += i.getPercentage();
			
		}
		percentages.put(Item.Effect.DAMAGE, percent);
	}
	public TreeMap<Item.Effect, Integer> getTotalPercentages(){
		
		calculateItemPercentages();
		
		return percentages;
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
	
	
	
	public String toString(){
		String out = "";
		for(Item i : items){
			
			out += i + "\n";
		}
		return out;
	}
	
}

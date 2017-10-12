package rougelikeLibrary;

import java.util.ArrayList;

public class Inventory {

	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Inventory(){
		
		
	}
	public void remove(Item i){
		items.remove(i);
		
	}
	public void add(Item i){
		
		items.add(i);
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
	
	public String toString(){
		String out = "";
		for(Item i : items){
			
			out += i + "\n";
		}
		return out;
	}
	
}

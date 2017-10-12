package rougelikeLibrary;

import java.util.ArrayList;

public class Inventory {

	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Inventory(){
		
		
	}
	
	public void Add(Item i){
		
		items.add(i);
	}
	public String getItems(){
		String out = "";
		for(Item i : items){
			
			out += i + "\n";
		}
		return out;
	}
	
}

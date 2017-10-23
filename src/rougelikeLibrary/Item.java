package rougelikeLibrary;

public class Item implements Mappable{

	private final String name;
	private final Effect effect;
	private final int value;
	
	public enum Effect { 
	    
		SPEED, HEALTH, DAMAGE
		
	}
	
	public Item(String name, int value, Effect effect){
		
		this.name = name;
		this.value = value;
		this.effect = effect;
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getValue(){
		
		return value;
	}
	public Effect getEffect(){
		
		return effect;
	}
	
	
	@Override
	public String toString(){
		String out = "";
		if(value > 0){
			out = name  + " +" + value + " " + effect;
		}
		else{
			out = name + " " + value + " " + effect;
		}
		out = out.toLowerCase();
		
		return out;
	}
	
}

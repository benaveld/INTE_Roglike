package rougelikeLibrary;

public class Item implements Mappable{

	private final String name;
	private final Effect effect;
	private final int value;

	public enum Effect { 
	    
		SPEED, HEALTH, DAMAGE
		
	}
	/**
	 * Constructor
	 * @param Name of the Item
	 * @param The amount it changes the chosen stat
	 * @param The chosen stat to effect
	 */
	public Item(String name, int value, Effect effect){
		
		this.name = name;
		this.value = value;
		this.effect = effect;
	}
	
	/**
	 * 
	 * @return The Item's name
	 */
	public String getName(){
		
		return name;
	}
	/**
	 * 
	 * @return The value the Item changes it's chosen stat by.
	 */
	public int getValue(){
		
		return value;
	}
	/**
	 * 
	 * @return The stat that the Item effects.
	 */
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

package rougelikeLibrary;

public class Item implements Mappable{

	private String name;
	private Effect effect;
	private int percentage;
	
	public Item(String name, int percentage, Effect effect){
		
		this.name = name;
		this.percentage = percentage;
		this.effect = effect;
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getPercentage(){
		
		return percentage;
	}
	public Effect getEffect(){
		
		return effect;
	}
	
	
	public enum Effect { 
	    
		SPEED, HEALTH, DAMAGE
		
	}
	public String toString(){
		String out = "";
		if(percentage > 0){
			out = name + " +" + percentage + "% " + effect;
		}
		else{
			out = name + " " + percentage + "% " + effect;
		}
		out = out.toLowerCase();
		
		return out;
	}
	
}

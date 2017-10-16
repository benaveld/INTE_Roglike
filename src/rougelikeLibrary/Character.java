package rougelikeLibrary;


public class Character implements Mappable {
	private Position pos;
	private int speed;
	private int health;
	private int damage;
	private TurnSystem ts;
	private Inventory inv;

	public Character(int speed, int health, int damage, TurnSystem ts) {
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		pos = new Position(0,0);
		inv = new Inventory();
	}
	
	public Character(int speed, int health, int damage, Position Position, TurnSystem ts) {
		this(speed, health, damage, ts);
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		this.pos = Position;
		
	}
	
	public Character(int speed, int health, int damage, int x, int y, TurnSystem ts) {
		this(speed, health, damage, new Position(x,y), ts);
		if (speed < 0 || health < 0 || damage < 0) {
			throw new IllegalArgumentException("Speed, health and damage needs to be 0 or more");
		}
		
	}
	

	public void takeDamage(int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("Can't take negative damage.");
		}
		health -= damage;
		if (health < 0) {
			health = 0;
		}
	}

	public int getSpeed() {
		return speed;
	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public void setPosition(int x, int y) {
		pos = new Position(x,y);
	}

	public Position getPosition() {
		return pos;
	}
	public Inventory getInventory() {
		return inv;
	}
}

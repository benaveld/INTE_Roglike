package rougelikeLibrary;

import static org.junit.Assert.*;


import org.junit.*;

import rougelikeLibrary.Position.CardinalDirection;

import java.util.HashMap;
import java.util.List;

public class CharacterTests {
	
	@Test
	public void testCharacterHealth(){
		Character lO = new Character(0,0,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(0, lO.getHealth());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeCharacterHealth(){
		Character c = new Character(0,-1,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(0, c.getHealth());
		
	}
	@Test
	public void testCharacterSetHealth(){
		Character lO = new Character(0,0,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		lO.setHealth(10);
		assertEquals(10, lO.getHealth());
		
	}

	
	@Test
	public void testCharacterSpeed(){
		
		Character lO = new Character(1,0,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(1, lO.getSpeed());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterNegativeSpeed(){
		
		Character lO = new Character(-1,0,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(0, lO.getSpeed());
		
	}
	@Test
	public void testCharacterSpeedZero(){
		Character lO = new Character(0,0,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(0, lO.getSpeed());
	}
	@Test
	public void testCharacterTakeDamage() {
		Character lO = new Character(0,100,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(10);
		assertEquals(90, lO.getHealth());
	}
	@Test
	public void testCharacter0Damage() {
		Character lO = new Character(0,100,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(0);
		assertEquals(100, lO.getHealth());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterTakeNegativeDamage() {
		Character lO = new Character(0,100,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(-10);
	}
	@Test
	public void testCharacterNegativeHealthAfterDamage() {
		Character lO = new Character(0,100,0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(110);
		assertEquals(0, lO.getHealth());
	}
	@Test
	public void testCharacterDamage() {
		Character c = new Character(0, 0, 0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(0, c.getDamage());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterNegativeDamage() {
		new Character(0, 0, -1, new Position(0,0), new TurnSystem(new EnemyAI(1)));
	}
	@Test
	public void testCharacterPosition() {
		Character c = new Character(0, 0, 0, new Position(1, 2), new TurnSystem(new EnemyAI(1)));
		assertEquals(new Position(1, 2), c.getPosition());
		assertEquals(1, c.getPosition().getX());
		assertEquals(2, c.getPosition().getY());
	}
	@Test
	public void testCharacterPositionSet() {
		Character c = new Character(0, 0, 0, new Position(1,2),  new TurnSystem(new EnemyAI(1)));
		c.setPosition(3, 4);
		assertEquals(3, c.getPosition().getX());
		assertEquals(4, c.getPosition().getY());
	}
	@Test
	public void testCharacterInventory() {
		Character c0 = new Character(0, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		assertEquals("", c0.getInventory().toString());
	}
	@Test
	public void testCharacterAddToInventory() {
		Character c = new Character(0, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		
		Item i = new Item("test", 25, Item.Effect.SPEED);
		c.getInventory().add(i);
		Item itemReturned = c.getInventory().getItem(0);
		assertEquals("test +25 speed", itemReturned.toString());
	}
	@Test
	public void testCharacterStartTurnReturnTrue()
	{

		Player p = new Player(5, 1, 0, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(3, 3), new HashMap<Position, List<Mappable>>());
		r.setPlayer(p.getPosition(), p);
		r.addDoor(CardinalDirection.North);
		r.addDoor(CardinalDirection.West);
		assertTrue(p.startTurn(r));
	}
	@Test
	public void testCharacterStartTurnReturnFalse()
	{
		Character c = new Character(5, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(3, 3), new HashMap<Position, List<Mappable>>());
		r.setPlayer(c.getPosition(), c);
		assertFalse(c.startTurn(r));
	}
	@Test
	public void testCharacterIsDead() {
		
		Character c = new Character(0, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		
		assertTrue(c.isDead());
	}
	@Test
	public void testCharacterToString() {
		
		Character c = new Character(0, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		assertEquals("0 0 0", c.toString());
	}
	@Test
	public void testCharacterDeath() {
		
		Character c = new Character(1, 1, 1, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		c.takeDamage(1);
		assertTrue(c.isDead());
	}
	@Test
	public void testCharacterStatChanges() {
		
		Enemy e = new Enemy(1,1,1, new Position(1,1), new TurnSystem(new EnemyAI(1)));

		Item id = new Item("test", 1, Item.Effect.DAMAGE);
		Item iH = new Item("test", 1, Item.Effect.HEALTH);
		Item iS = new Item("test", 1, Item.Effect.SPEED);
		e.getInventory().add(id);
		e.getInventory().add(iH);
		e.getInventory().add(iS);
		Room r = new Room(new Position(1,1), new RoomSpace(10,10), new HashMap<Position, List<Mappable>>());
		r.addEnemy(new Position(1,1), e);
		assertFalse(e.startTurn(r));
		assertEquals(2, e.getDamage()); //All stats increased
		assertEquals(2, e.getSpeed());
		assertEquals(2, e.getHealth());
		Item id2 = new Item("test", -2, Item.Effect.DAMAGE);
		Item is2 = new Item("test", -2, Item.Effect.SPEED);
		e.getInventory().add(id2);
		e.getInventory().add(is2);
		e.getInventory().add(new Item("test", -1, Item.Effect.HEALTH));
		assertFalse(e.startTurn(r));
		assertEquals(0, e.getDamage()); //All stats decreased
		assertEquals(0, e.getSpeed());
		assertEquals(1, e.getHealth());
		e.getInventory().add(new Item("test", 2, Item.Effect.HEALTH));
		assertFalse(e.startTurn(r)); 
		assertEquals(3, e.getHealth()); //Health increased
		e.takeDamage(1); 
		assertFalse(e.startTurn(r));
		assertEquals(2, e.getHealth()); //Health decreased by attack
		e.getInventory().add(new Item("test", -1, Item.Effect.HEALTH));
		assertFalse(e.startTurn(r));
		assertEquals(1, e.getHealth()); //Health further decreased by item
		e.getInventory().add(new Item("test", 3, Item.Effect.HEALTH));
		assertFalse(e.startTurn(r));
		assertEquals(4, e.getHealth()); //Health increased by item again
		e.getInventory().add(new Item("test", 1, Item.Effect.HEALTH));
		e.takeDamage(2);
		assertEquals(3, e.getHealth()); //Health is changed when getHealth is called as well as when a turn starts
		assertFalse(e.startTurn(r));
		assertEquals(3, e.getHealth()); //Taking damage and increasing health at the same time
		e.getInventory().add(new Item("test", -1, Item.Effect.HEALTH));
		e.takeDamage(1);
		assertFalse(e.startTurn(r));
		assertEquals(1, e.getHealth()); //Removing item to kill player
		e.getInventory().remove(iH);
		assertFalse(e.startTurn(r));
		assertEquals(0, e.getHealth()); 
		assertTrue(e.isDead());
		e.getInventory().remove(id2);
		e.getInventory().remove(is2);
		e.getInventory().remove(id);
		e.getInventory().remove(iS);
		assertFalse(e.startTurn(r));
		assertEquals(1, e.getDamage()); //All stats changed by removing items
		assertEquals(1, e.getSpeed());
	}
}

package rougelikeLibrary;

import static org.junit.Assert.*;


import org.junit.*;

public class CharacterTests {
	
	@Test
	public void testCharacterHealth(){
		Character lO = new Character(0,0,0, new TurnSystem(new EnemyAI(1)));
		assertEquals(0, lO.getHealth());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeCharacterHealth(){
		Character c = new Character(0,-1,0, new TurnSystem(new EnemyAI(1)));
		assertEquals(0, c.getHealth());
		
	}
	@Test
	public void testCharacterSpeed(){
		
		Character lO = new Character(1,0,0, new TurnSystem(new EnemyAI(1)));
		assertEquals(1, lO.getSpeed());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterNegativeSpeed(){
		
		Character lO = new Character(-1,0,0, new TurnSystem(new EnemyAI(1)));
		assertEquals(0, lO.getSpeed());
		
	}
	@Test
	public void testCharacterSpeedZero(){
		Character lO = new Character(0,0,0, new TurnSystem(new EnemyAI(1)));
		assertEquals(0, lO.getSpeed());
	}
	@Test
	public void testCharacterTakeDamage() {
		Character lO = new Character(0,100,0, new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(10);
		assertEquals(90, lO.getHealth());
	}
	@Test
	public void testCharacter0Damage() {
		Character lO = new Character(0,100,0, new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(0);
		assertEquals(100, lO.getHealth());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterTakeNegativeDamage() {
		Character lO = new Character(0,100,0, new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(-10);
	}
	@Test
	public void testCharacterNegativeHealthAfterDamage() {
		Character lO = new Character(0,100,0, new TurnSystem(new EnemyAI(1)));
		lO.takeDamage(110);
		assertEquals(0, lO.getHealth());
	}
	@Test
	public void testCharacterDamage() {
		Character c = new Character(0, 0, 0, new TurnSystem(new EnemyAI(1)));
		assertEquals(0, c.getDamage());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterNegativeDamage() {
		new Character(0, 0, -1, new TurnSystem(new EnemyAI(1)));
	}
	@Test
	public void testCharacterPosition() {
		Character c = new Character(0, 0, 0, new Position(1, 2), new TurnSystem(new EnemyAI(1)));
		assertEquals(new Position(1, 2), c.getPosition());
		assertEquals(1, c.getPosition().getX());
		assertEquals(2, c.getPosition().getY());
	}
	@Test
	public void testCharacterXY() {
		Character c = new Character(0, 0, 0, 1, 2, new TurnSystem(new EnemyAI(1)));
		assertEquals(new Position(1,2), c.getPosition());
		assertEquals(1, c.getPosition().getX());
		assertEquals(2, c.getPosition().getY());
	}
	@Test
	public void testCharacterPositionSet() {
		Character c = new Character(0, 0, 0, 1, 2, new TurnSystem(new EnemyAI(1)));
		c.setPosition(3, 4);
		assertEquals(3, c.getPosition().getX());
		assertEquals(4, c.getPosition().getY());
	}
	@Test
	public void testCharacterInventory() {
		Character c0 = new Character(0, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		Character c1 = new Character(0, 0, 0, new TurnSystem(new EnemyAI(1)));
		Character c2 = new Character(0, 0, 0, 1, 2, new TurnSystem(new EnemyAI(1)));
		assertEquals("", c0.getInventory().toString());
		assertEquals("", c1.getInventory().toString());
		assertEquals("", c2.getInventory().toString());
	}
	@Test
	public void testCharacterAddToInventory() {
		Character c = new Character(0, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		
		Item i = new Item("test", 25, Item.Effect.SPEED);
		c.getInventory().add(i);
		Item itemReturned = c.getInventory().getItem(0);
		assertEquals("test +25% speed", itemReturned.toString());
	}
	@Test
	public void testCharacterStartTurnReturnTrue()
	{
		Character c = new Character(5, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(3, 3));
		assertTrue(c.startTurn(r));
	}
	@Test
	public void testCharacterStartTurnReturnFalse()
	{
		Character c = new Character(5, 0, 0, new Position(1,2), new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(3, 3));
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
	
}

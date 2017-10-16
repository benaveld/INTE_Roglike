package rougelikeLibrary;

import static org.junit.Assert.*;

import java.awt.Point;

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
	public void testCharacterPoint() {
		Character c = new Character(0, 0, 0, new Point(1, 2), new TurnSystem(new EnemyAI(1)));
		assertEquals(new Point(1, 2), c.getPoint());
		assertEquals(1, c.getPoint().x);
		assertEquals(2, c.getPoint().y);
	}
	@Test
	public void testCharacterXY() {
		Character c = new Character(0, 0, 0, 1, 2, new TurnSystem(new EnemyAI(1)));
		assertEquals(new Point(1,2), c.getPoint());
		assertEquals(1, c.getPoint().x);
		assertEquals(2, c.getPoint().y);
	}
	@Test
	public void testCharacterPointSet() {
		Character c = new Character(0, 0, 0, 1, 2, new TurnSystem(new EnemyAI(1)));
		c.setPoint(3, 4);
		assertEquals(3, c.getPoint().x);
		assertEquals(4, c.getPoint().y);
	}
}

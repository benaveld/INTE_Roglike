package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class CharacterTests {
	
	@Test
	public void testCharacterHealth(){
		Character lO = new Character(0,0,0);
		assertEquals(0, lO.getHealth());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeCharacterHealth(){
		Character c = new Character(0,-1,0);
		assertEquals(0, c.getHealth());
		
	}
	@Test
	public void testCharacterSpeed(){
		
		Character lO = new Character(1,0,0);
		assertEquals(1, lO.getSpeed());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterNegativeSpeed(){
		
		Character lO = new Character(-1,0,0);
		assertEquals(0, lO.getSpeed());
		
	}
	@Test
	public void testCharacterSpeedZero(){
		Character lO = new Character(0,0,0);
		assertEquals(0, lO.getSpeed());
	}
	@Test
	public void testCharacterTakeDamage() {
		Character lO = new Character(0,100,0);
		lO.takeDamage(10);
		assertEquals(90, lO.getHealth());
	}
	@Test
	public void testCharacter0Damage() {
		Character lO = new Character(0,100,0);
		lO.takeDamage(0);
		assertEquals(100, lO.getHealth());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterTakeNegativeDamage() {
		Character lO = new Character(0,100,0);
		lO.takeDamage(-10);
	}
	@Test
	public void testCharacterNegativeHealthAfterDamage() {
		Character lO = new Character(0,100,0);
		lO.takeDamage(110);
		assertEquals(0, lO.getHealth());
	}
	@Test
	public void testCharacterDamage() {
		Character c = new Character(0, 0, 0);
		assertEquals(0, c.getDamage());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCharacterNegativeDamage() {
		new Character(0, 0, -1);
	}
}

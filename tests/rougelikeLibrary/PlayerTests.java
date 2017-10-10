package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {

	@Test
	public void testPlayerHealth(){
		Player p = new Player(0,0);
		assertEquals(0, p.getHealth());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativePlayerHealth(){
		
		Player p = new Player(0,-1);
		assertEquals(0, p.getHealth());
		
	}
	
	
	@Test
	public void testPlayerSpeed(){
		
		Player p = new Player(1,0);
		assertEquals(1, p.getSpeed());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerNegativeSpeed(){
		
		Player p = new Player(-1,0);
		assertEquals(0, p.getSpeed());
		
	}
	
	
	@Test
	public void testPlayerSpeedZero(){
		Player p = new Player(0,0);
		assertEquals(0, p.getSpeed());
	}

	@Test
	public void testPlayerDamage() {
		Player p = new Player(0,100);
		p.takeDamage(10);
		assertEquals(90, p.getHealth());
	}
  
	@Test
	public void testPlayer0Damage() {
		Player p = new Player(0,100);
		p.takeDamage(0);
		assertEquals(p.getHealth(), 100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerNegativeDamage() {
		Player p = new Player(0,100);
		p.takeDamage(-10);
		assertEquals(p.getHealth(), 100);
	}
	@Test
	public void testPlayerNegativeHealthAfterDamage() {
		Player p = new Player(0,100);
		p.takeDamage(110);
		assertEquals(p.getHealth(), 0);
	}
	
	
	
}

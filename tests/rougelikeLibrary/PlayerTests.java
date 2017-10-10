package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {

	@Test
	public void playerHealthTest(){
		Player p = new Player(0);
		assertEquals(0, p.getHealth());
		
	}

	@Test
	public void playerDamageTest() {
		Player p = new Player(0);
		p.setHealth(100);
		p.takeDamage(10);
		assertEquals(90, p.getHealth());
	}
	
	
	@Test
	public void testPlayerSpeed(){
		
		Player p = new Player(1);
		assertEquals(1, p.getSpeed());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerNegativeSpeed(){
		
		Player p = new Player(-1);
		assertEquals(0, p.getSpeed());
		
	}
	
	
	@Test
	public void testPlayerSpeedZero(){
		Player p = new Player(0);
		assertEquals(0, p.getSpeed());
	}

  
	@Test
	public void player0Damage() {
		Player p = new Player(0);
		p.setHealth(100);
		p.takeDamage(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void playerNagativDamage() {
		Player p = new Player(0);
		p.setHealth(100);
		p.takeDamage(-10);
	}
	
	
	
}

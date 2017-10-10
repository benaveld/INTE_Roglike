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
  public void playerSpeedTest(){
		Player p = new Player(0);
		assertEquals(0f, p.getSpeed(), 0.00001);
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

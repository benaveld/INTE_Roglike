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
	public void playerDamageTest() {
		Player p = new Player(0,0);
		p.setHealth(100);
		p.takeDamage(10);
		assertEquals(90, p.getHealth());
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
	public void player0Damage() {
		Player p = new Player(0,0);
		p.setHealth(100);
		p.takeDamage(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void playerNagativDamage() {
		Player p = new Player(0,0);
		p.setHealth(100);
		p.takeDamage(-10);
	}
	
	@Test
	public void testPlayerPosistion()
	{
		Player p = new Player(5,5);
		p.setPosistion(15,25);
		assertEquals(15,p.getPosX());
		assertEquals(25,p.getPosY());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerPosistionInvalidX()
	{
		Player p = new Player(5,5);
		p.setPosistion(-5,25);
		assertEquals(-5,p.getPosX());
		assertEquals(25,p.getPosY());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPlayerPosistionInvalidY()
	{
		Player p = new Player(5,5);
		p.setPosistion(5,-25);
		assertEquals(5,p.getPosX());
		assertEquals(-25,p.getPosY());
	}
	
	
	
	
}

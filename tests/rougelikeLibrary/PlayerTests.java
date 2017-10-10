package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {
	
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

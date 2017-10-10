package rougelikeLibrary;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MapObjectTests {
	@Test
	public void testCheckSpotEmpty()
	{
		Map map = new Map(50,50);
		assertTrue(map.checkSpotEmpty(0,0));
	}
	
	
	@Test
	public void testAddPlayerToMap()
	{
		Map map = new Map(50,50);
		Player p  = new Player(1,100);
		map.addPlayer(p, 30, 24);
		assertTrue(map.getPlayer() != null);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddPlayerOutsideMapWidth()
	{
		Map map = new Map(50,50);
		Player p  = new Player(1,100);
		map.addPlayer(p, 60, 24);
		assertTrue(map.getPlayer() != null);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddPlayerOutsideMapHeight()
	{
		Map map = new Map(50,50);
		Player p  = new Player(1,100);
		map.addPlayer(p, 24, 64);
		assertTrue(map.getPlayer() != null);
	}
	
	@Test
	public void testCheckSpotOccupied()
	{
		Map map = new Map(50,50);
		Player p = new Player(1,100);
		map.addPlayer(p,30,24);
		assertTrue(!map.checkSpotEmpty(30,24));
	}
}

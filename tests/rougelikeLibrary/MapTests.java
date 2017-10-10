package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class MapTests {
	
	@Test
	public void testCreateMap256By256()
	{
		Map map = new Map(256,256);
		assertEquals(256, map.getWidth());
		assertEquals(256, map.getHeight());
	}
	
	@Test
	public void testCreateMap50by150()
	{
		Map map = new Map(50,150);
		assertEquals(50, map.getWidth());
		assertEquals(150, map.getHeight());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMap0by150()
	{
		Map map = new Map(0,150);
		assertEquals(0, map.getWidth());
		assertEquals(150, map.getHeight());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMapNegative50by150()
	{
		Map map = new Map(-50,150);
		assertEquals(-50, map.getWidth());
		assertEquals(150, map.getHeight());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMap50by0()
	{
		Map map = new Map(50,0);
		assertEquals(50, map.getWidth());
		assertEquals(0, map.getHeight());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMap50ByNegative150()
	{
		Map map = new Map(50,-150);
		assertEquals(50, map.getWidth());
		assertEquals(-150, map.getHeight());
	}
	
}

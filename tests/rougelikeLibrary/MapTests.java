package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class MapTests {
	
	@Test
	public void CreateMap256By256Test()
	{
		Map map = new Map(256,256);
		assertEquals(256, map.getWidth());
		assertEquals(256, map.getHeight());
	}
	
	@Test
	public void CreateMap50by150()
	{
		Map map = new Map(50,150);
		assertEquals(50, map.getWidth());
		assertEquals(150, map.getHeight());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CreateMap0by150()
	{
		Map map = new Map(0,150);
		assertEquals(0, map.getWidth());
		assertEquals(150, map.getHeight());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CreateMapNegative50by150()
	{
		Map map = new Map(-50,150);
		assertEquals(-50, map.getWidth());
		assertEquals(150, map.getHeight());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void CreateMap50by0()
	{
		Map map = new Map(50,0);
		assertEquals(50, map.getWidth());
		assertEquals(0, map.getHeight());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CreateMap50ByNegative150()
	{
		Map map = new Map(50,-150);
		assertEquals(50, map.getWidth());
		assertEquals(-150, map.getHeight());
	}
	
	
}

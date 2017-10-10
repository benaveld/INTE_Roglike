package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class MapTests {
	
	@Test
	public void Map256by256Test()
	{
		Map map = new Map();
		assertEquals(map.getWidth(),256);
		assertEquals(map.getHeight(), 256);
	}
	
	
	
}

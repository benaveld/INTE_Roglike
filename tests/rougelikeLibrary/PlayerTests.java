package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {

	@Test
	public void playerHealthTest(){
		Player p = new Player();
		assertEquals(0, p.getHealth());
		
	}
	
	
	
	
	
}

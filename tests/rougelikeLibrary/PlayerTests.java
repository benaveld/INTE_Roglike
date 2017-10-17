package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {
	
	@Test
	public void playerTest() {
		Player p = new Player(1, 2, 3, new TurnSystem(new TUI()));
		assertEquals(1, p.getSpeed());
		assertEquals(2, p.getHealth());
		assertEquals(3, p.getDamage());
	}
	
	
}

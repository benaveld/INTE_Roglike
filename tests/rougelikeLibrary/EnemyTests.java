package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class EnemyTests {

	@Test
	public void enemyTest() {
		Enemy e = new Enemy(1, 2, 3, new TurnSystem(new EnemyAI(1)));
		assertEquals(1, e.getSpeed());
		assertEquals(2, e.getHealth());
		assertEquals(3, e.getDamage());
	}
	
	
}

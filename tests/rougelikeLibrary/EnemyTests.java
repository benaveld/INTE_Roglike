package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
public class EnemyTests {

	@Test
	public void enemyTest() {
		Enemy e = new Enemy(1, 2, 3, new Position(0,0), new TurnSystem(new EnemyAI(1)));
		assertEquals(1, e.getSpeed());
		assertEquals(2, e.getHealth());
		assertEquals(3, e.getDamage());
	}
	
	@Test
	public void testEnemyDropItemOnDeath() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));

    Room r = new Room(new Position(0,0), new RoomSpace(10,10), new HashMap<Position, List<Mappable>>());
		r.addEnemy(new Position(2,2), new Enemy(1,1,1, new Position(0,0), ts));
		Enemy e = (Enemy)r.getFromPosition(new Position(2,2)).get(0);
		e.getInventory().add(new Item("test", 100, Item.Effect.DAMAGE));
		assertEquals("1 1 1", e.toString());
		e.takeDamage(1);
		assertEquals("1 0 1", e.toString());
		assertTrue(e.isDead());
		List<Mappable> items = r.getFromPosition(new Position(2,2));
		Inventory inv = new Inventory();
		for(Mappable m : items) {
			
			if(m instanceof Item) {
				Item i = (Item)m;
				inv.add(i);
			}
		}
		assertEquals("test +100% damage\n", inv.toString());		
	}
	@Test
	public void testEnemyDropsNoItemsOnDeath() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Room r = new Room(new Position(0,0), new RoomSpace(10,10), new HashMap<Position, List<Mappable>>());
		r.addEnemy(new Position(2,2), new Enemy(1,1,1, ts));
		r.addEnemy(new Position(2,2), new Enemy(1,1,1, new Position(0,0), ts));
		Enemy e = (Enemy)r.getFromPosition(new Position(2,2)).get(0);
		assertEquals("1 1 1", e.toString());
		e.takeDamage(1);
		assertEquals("1 0 1", e.toString());
		assertTrue(e.isDead());
		List<Mappable> items = r.getFromPosition(new Position(2,2));
		assertTrue(items.isEmpty());
		
	}
	@Test
	public void testEnemyDropsMultipleItemsOnDeath() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Room r = new Room(new Position(0,0), new RoomSpace(10,10), new HashMap<Position, List<Mappable>>());
		r.addEnemy(new Position(2,2), new Enemy(1,1,1, new Position(0,0), ts));
		Enemy e = (Enemy)r.getFromPosition(new Position(2,2)).get(0);
		e.getInventory().add(new Item("test0", 100, Item.Effect.DAMAGE));
		e.getInventory().add(new Item("test1", 10, Item.Effect.SPEED));
		e.getInventory().add(new Item("test2", 200, Item.Effect.HEALTH));
		e.getInventory().add(new Item("test3", 50, Item.Effect.DAMAGE));
		assertEquals("1 1 1", e.toString());
		e.takeDamage(1);
		assertEquals("1 0 1", e.toString());
		assertTrue(e.isDead());
		List<Mappable> items = r.getFromPosition(new Position(2,2));
		Inventory inv = new Inventory();
		for(Mappable m : items) {
			
			if(m instanceof Item) {
				Item i = (Item)m;
				inv.add(i);
			}
		}
		assertEquals("test0 +100% damage\ntest1 +10% speed\ntest2 +200% health\ntest3 +50% damage\n", inv.toString());
		
	}
}

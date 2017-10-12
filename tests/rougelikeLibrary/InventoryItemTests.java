package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;


public class InventoryItemTests {

	@Test
	public void testItemName(){
		Item i = new Item("test", 100, Item.Effect.DAMAGE);
		
		assertEquals("test", i.getName());
		
	}
	@Test
	public void testItemPercentage(){
		Item i = new Item("test", 100, Item.Effect.DAMAGE);
		
		assertEquals(100, i.getPercentage());
		
	}
	@Test 
	public void testItemEffect(){ //Kind of unnecessary
		Item i = new Item("test", 100, Item.Effect.DAMAGE);
		
		assertEquals(Item.Effect.DAMAGE, i.getEffect());
		
	}
	@Test
	public void testItem(){ 
		Item i = new Item("test", 100, Item.Effect.DAMAGE);
		
		assertEquals("test +100% damage", i.toString());
		
	}
	@Test
	public void testItemNegativePercentage(){ 
		Item i = new Item("test", -100, Item.Effect.DAMAGE);
		
		assertEquals("test -100% damage", i.toString());
		
	}
	
	@Test
	public void testAddItemToInventory(){
	
		Item i = new Item("test", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.Add(i);
		assertEquals("test +100% damage\n", inv.getItems());
		
	}
	@Test
	public void testAddItemsToInventory(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.Add(i0);
		inv.Add(i1);
		inv.Add(i2);
		assertEquals("test0 +25% speed\ntest1 +50% health\ntest2 +100% damage\n", inv.getItems());
		
	}
	
}

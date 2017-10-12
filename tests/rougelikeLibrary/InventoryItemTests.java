package rougelikeLibrary;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
		inv.add(i);
		assertEquals("test +100% damage\n", inv.toString());
		
	}
	@Test
	public void testAddItemsToInventory(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.add(i0);
		inv.add(i1);
		inv.add(i2);
		assertEquals("test0 +25% speed\ntest1 +50% health\ntest2 +100% damage\n", inv.toString());
		
	}
	@Test
	public void testRemoveItemsFromInventory(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.add(i0);
		inv.add(i1);
		inv.add(i2);
		inv.remove(i1);
		assertEquals("test0 +25% speed\ntest2 +100% damage\n", inv.toString());
		
	}
	@Test
	public void testGetItems(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.add(i0);
		inv.add(i1);
		inv.add(i2);
		ArrayList<Item> items = inv.getItems();
		String itemsString = "";
		for(Item i : items){
			
			itemsString += i.toString();
			
		}
		assertEquals("test0 +25% speedtest1 +50% healthtest2 +100% damage", itemsString);
		
	}
	@Test
	public void testGetItem(){
	
		Item i = new Item("test", 25, Item.Effect.SPEED);
		Inventory inv = new Inventory();
		inv.add(i);
		Item itemReturned = inv.getItem(0);
		assertEquals("test +25% speed", itemReturned.toString());
		
	}
	
	@Test
	public void testGetItemAfterItemRemoved(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.add(i0);
		inv.add(i1);
		inv.add(i2);
		inv.remove(i1);
		Item itemReturned = inv.getItem(1);
		assertEquals("test2 +100% damage", itemReturned.toString());
		
	}
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetNonExistentItem(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.add(i0);
		inv.add(i1);
		inv.add(i2);
		inv.remove(i1);
		Item itemReturned = inv.getItem(2);
		assertEquals("test2 +100% damage", itemReturned.toString());
		
	}
	
	@Test
	public void testRemoveAllItemsFromInventory(){
	
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Inventory inv = new Inventory();
		inv.add(i0);
		inv.add(i1);
		inv.add(i2);
		inv.removeAllItems();
		assertEquals("", inv.toString());
		
	}
	
}

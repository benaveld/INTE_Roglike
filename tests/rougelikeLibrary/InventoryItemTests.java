package rougelikeLibrary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

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
	
	@Test
	public void testGetItemsBySpeed(){
		
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Item i3 = new Item("test3", 25, Item.Effect.SPEED);
		Item i4 = new Item("test4", 50, Item.Effect.HEALTH);
		Item i5 = new Item("test5", 100, Item.Effect.DAMAGE);
		Item[] items = {i0,i1,i2,i3,i4,i5};
		Inventory inv = new Inventory();
		for(int i = 0; i<6; i++){
			
			inv.add(items[i]);
		}
		ArrayList<Item> itemsBySpeed = inv.getItemsByStat(Item.Effect.SPEED);
		
		String itemString = "";
		
		for(Item i :itemsBySpeed){
			
			itemString += i.toString();
		}
		
		assertEquals("test0 +25% speedtest3 +25% speed", itemString);
	}
	
	@Test
	public void testGetItemsByHealth(){
		
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Item i3 = new Item("test3", 25, Item.Effect.SPEED);
		Item i4 = new Item("test4", 50, Item.Effect.HEALTH);
		Item i5 = new Item("test5", 100, Item.Effect.DAMAGE);
		Item[] items = {i0,i1,i2,i3,i4,i5};
		Inventory inv = new Inventory();
		for(int i = 0; i<6; i++){
			
			inv.add(items[i]);
		}
		ArrayList<Item> itemsByHealth = inv.getItemsByStat(Item.Effect.HEALTH);
		
		
		String itemString = "";
		
		for(Item i :itemsByHealth){
			
			itemString += i.toString();
		}
		
		assertEquals("test1 +50% healthtest4 +50% health", itemString);
	}
	
	@Test
	public void testGetItemsByDamage(){
		
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Item i3 = new Item("test3", 25, Item.Effect.SPEED);
		Item i4 = new Item("test4", 50, Item.Effect.HEALTH);
		Item i5 = new Item("test5", 100, Item.Effect.DAMAGE);
		Item[] items = {i0,i1,i2,i3,i4,i5};
		Inventory inv = new Inventory();
		for(int i = 0; i<6; i++){
			
			inv.add(items[i]);
		}
		ArrayList<Item> itemsByDamage = inv.getItemsByStat(Item.Effect.DAMAGE);
	
		
		String itemString = "";
		
		for(Item i :itemsByDamage){
			
			itemString += i.toString();
		}
		
		assertEquals("test2 +100% damagetest5 +100% damage", itemString);
	}
	
	@Test
	public void testTotalPercentages(){
		
		Item i0 = new Item("test0", 25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 100, Item.Effect.DAMAGE);
		Item i3 = new Item("test3", 25, Item.Effect.SPEED);
		Item i4 = new Item("test4", 50, Item.Effect.HEALTH);
		Item i5 = new Item("test5", 100, Item.Effect.DAMAGE);
		Item[] items = {i0,i1,i2,i3,i4,i5};
		Inventory inv = new Inventory();
		for(int i = 0; i<6; i++){
			
			inv.add(items[i]);
		}
		
		TreeMap<Item.Effect, Integer> totalPercentByStat = inv.getTotalPercentages();
		
		int speedPercentage = totalPercentByStat.get(Item.Effect.SPEED);
		int healthPercentage = totalPercentByStat.get(Item.Effect.HEALTH);
		int damagePercentage = totalPercentByStat.get(Item.Effect.DAMAGE);
		
		assertEquals(50, speedPercentage);
		assertEquals(100, healthPercentage);
		assertEquals(200, damagePercentage);
	}
	
	@Test
	public void testTotalNegativePercentages(){
		
		Item i0 = new Item("test0", -25, Item.Effect.SPEED);
		Item i1 = new Item("test1", -50, Item.Effect.HEALTH);
		Item i2 = new Item("test2", -100, Item.Effect.DAMAGE);
		Item i3 = new Item("test3", -25, Item.Effect.SPEED);
		Item i4 = new Item("test4", -50, Item.Effect.HEALTH);
		Item i5 = new Item("test5", -100, Item.Effect.DAMAGE);
		Item[] items = {i0,i1,i2,i3,i4,i5};
		Inventory inv = new Inventory();
		for(int i = 0; i<6; i++){
			
			inv.add(items[i]);
		}
		
		TreeMap<Item.Effect, Integer> totalPercentByStat = inv.getTotalPercentages();
		
		int speedPercentage = totalPercentByStat.get(Item.Effect.SPEED);
		int healthPercentage = totalPercentByStat.get(Item.Effect.HEALTH);
		int damagePercentage = totalPercentByStat.get(Item.Effect.DAMAGE);
		
		assertEquals(-50, speedPercentage);
		assertEquals(-100, healthPercentage);
		assertEquals(-200, damagePercentage);
	}
	@Test
	public void testTotalMixedPercentages(){
		
		Item i0 = new Item("test0", -25, Item.Effect.SPEED);
		Item i1 = new Item("test1", 100, Item.Effect.HEALTH);
		Item i2 = new Item("test2", 0, Item.Effect.DAMAGE);
		Item i3 = new Item("test3", 50, Item.Effect.SPEED);
		Item i4 = new Item("test4", -50, Item.Effect.HEALTH);
		Item i5 = new Item("test5", -100, Item.Effect.DAMAGE);
		Item[] items = {i0,i1,i2,i3,i4,i5};
		Inventory inv = new Inventory();
		for(int i = 0; i<6; i++){
			
			inv.add(items[i]);
		}
		
		TreeMap<Item.Effect, Integer> totalPercentByStat = inv.getTotalPercentages();
		
		int speedPercentage = totalPercentByStat.get(Item.Effect.SPEED);
		int healthPercentage = totalPercentByStat.get(Item.Effect.HEALTH);
		int damagePercentage = totalPercentByStat.get(Item.Effect.DAMAGE);
		
		assertEquals(25, speedPercentage);
		assertEquals(50, healthPercentage);
		assertEquals(-100, damagePercentage);
	}
	
}

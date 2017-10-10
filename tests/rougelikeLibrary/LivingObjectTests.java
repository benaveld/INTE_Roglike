package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

public class LivingObjectTests {
	
	@Test
	public void testLivingObjectHealth(){
		LivingObject lO = new LivingObject(0,0);
		assertEquals(0, lO.getHealth());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLivingObjectHealth(){
		
		LivingObject lO = new LivingObject(0,-1);
		assertEquals(0, lO.getHealth());
		
	}
	@Test
	public void testLivingObjectSpeed(){
		
		LivingObject lO = new LivingObject(1,0);
		assertEquals(1, lO.getSpeed());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testLivingObjectNegativeSpeed(){
		
		LivingObject lO = new LivingObject(-1,0);
		assertEquals(0, lO.getSpeed());
		
	}
	@Test
	public void testLivingObjectSpeedZero(){
		LivingObject lO = new LivingObject(0,0);
		assertEquals(0, lO.getSpeed());
	}
	@Test
	public void testLivingObjectDamage() {
		LivingObject lO = new LivingObject(0,100);
		lO.takeDamage(10);
		assertEquals(90, lO.getHealth());
	}
	@Test
	public void testLivingObject0Damage() {
		LivingObject lO = new LivingObject(0,100);
		lO.takeDamage(0);
		assertEquals(100, lO.getHealth());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testLivingObjectNegativeDamage() {
		LivingObject lO = new LivingObject(0,100);
		lO.takeDamage(-10);
		assertEquals(100, lO.getHealth());
	}
	@Test
	public void testLivingObjectNegativeHealthAfterDamage() {
		LivingObject lO = new LivingObject(0,100);
		lO.takeDamage(110);
		assertEquals(0, lO.getHealth());
	}
}

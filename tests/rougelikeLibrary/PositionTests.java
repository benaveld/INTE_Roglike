package rougelikeLibrary;

import static org.junit.Assert.*;

import org.junit.*;

public class PositionTests {
	@Test
	public void testPosition() {
		Position p = new Position(1, 2);
		assertEquals(1, p.getX());
		assertEquals(2, p.getY());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeXPosition() {
		new Position(-1, 2);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeYPosition() {
		new Position(1, -1);
	}
	@Test
	public void testPositionEqual() {
		Position p1 = new Position(1, 2);
		Position p2 = new Position(1, 2);
		assertTrue(p1.equals(p2));
	}
	@Test
	public void testPositionEqualNot() {
		Position p1 = new Position(1, 2);
		Position p2 = new Position(2, 1);
		assertFalse(p1.equals(p2));
	}
	@Test
	public void testPositionNotEqualWithOtherObjects() {
		Position p = new Position(1,2);
		Object obj = new Object();
		assertFalse(p.equals(obj));
	}
	@Test
	public void testPositionHash() {
		Position p = new Position(1, 2);
		assertEquals(994, p.hashCode());
	}
	@Test
	public void testPositionHashIsSame() {
		Position p1 = new Position(1, 2);
		Position p2 = new Position(1, 2);
		assertTrue(p1.hashCode() == p2.hashCode());
	}
	@Test
	public void testPositionToString() {
		Position p = new Position(1, 2);
		assertEquals("X: 1 Y: 2", p.toString());
	}
	@Test
	public void testPositonSetX() {
		Position p = new Position(1, 2);
		p.setX(3);
		assertEquals(3, p.getX());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPositionSetNegativeX() {
		Position p = new Position(1, 2);
		p.setX(-2);
	}
	@Test
	public void testPositionSetY() {
		Position p = new Position(1, 2);
		p.setY(17);
		assertEquals(17, p.getY());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPositionSetNegativeY() {
		Position p = new Position(1, 2);
		p.setY(-4);
	}
	
}
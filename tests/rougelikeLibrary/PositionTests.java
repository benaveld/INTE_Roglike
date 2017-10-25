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
		Position p = new Position(1, 2);
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
		Position p = new Position(1, 2).setX(3);
		assertEquals(3, p.getX());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPositionSetNegativeX() {
		Position p = new Position(1, 2);
		p.setX(-2);
	}

	@Test
	public void testPositionSetY() {
		Position p = new Position(1, 2).setY(17);
		assertEquals(17, p.getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPositionSetNegativeY() {
		Position p = new Position(1, 2);
		p.setY(-4);
	}

	@Test
	public void testPositionGetLocation() {
		Position p = new Position(1, 2);
		Position pClone = p.getLocation();
		assertFalse(p == pClone);
		assertTrue(p.equals(pClone));
	}

	@Test
	public void testPositionTranslate() {
		Position p = new Position(1, 2).translate(3, 4);
		assertEquals(4, p.getX());
		assertEquals(6, p.getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPositionTranslateResultNecativeX() {
		Position p = new Position(1, 2);
		p.translate(-2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPositionTranslateResultNecativeY() {
		Position p = new Position(1, 2);
		p.translate(0, -3);
	}

	@Test(expected = ArithmeticException.class)
	public void testOverflowEast() {
		new Position(Integer.MAX_VALUE, Integer.MAX_VALUE).translateCardinalDirection(Position.CardinalDirection.East);
	}

	@Test(expected = ArithmeticException.class)
	public void testOverflowSouth() {
		new Position(Integer.MAX_VALUE, Integer.MAX_VALUE).translateCardinalDirection(Position.CardinalDirection.South);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnderflowNorth() {
		new Position(0, 0).translateCardinalDirection(Position.CardinalDirection.North);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnderflowWest() {
		new Position(0, 0).translateCardinalDirection(Position.CardinalDirection.West);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testOverflowTranslateX() {
		Position p = new Position(1,2);
		p.translate(Integer.MAX_VALUE, 0);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testPositionOverflowTranslateY() {
		Position p = new Position(1, 2);
		p.translate(0, Integer.MAX_VALUE);
	}
	
	@Test
	public void testPositionTranslateCardinalDirectionNorth() {
		Position p = new Position(1, 2).translateCardinalDirection(Position.CardinalDirection.North);
		assertEquals(1, p.getY());
	}
	
	@Test
	public void testPositionTranslateCardinalDirectionSouth() {
		Position p = new Position(1, 2).translateCardinalDirection(Position.CardinalDirection.South);
		assertEquals(3, p.getY());
	}
	
	@Test
	public void testPositionTranslateCardinalDirectionEast() {
		Position p = new Position(1, 2).translateCardinalDirection(Position.CardinalDirection.East);
		assertEquals(2, p.getX());
		assertEquals(2, p.getY());
	}
	
	@Test
	public void testPositionTranslateCardinalDirectionWest() {
		Position p = new Position(1, 2).translateCardinalDirection(Position.CardinalDirection.West);
		assertEquals(0, p.getX());
		assertEquals(2, p.getY());
	}
}

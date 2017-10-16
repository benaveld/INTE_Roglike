package rougelikeLibrary;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashMap;

import org.junit.*;

public class TurnSystemTests {
	@Test
	public void testTurnSystemStartTurn() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Character c = new Character(0, 0, 0, 5, 5, new TurnSystem(new EnemyAI(1)));
		HashMap<Point, Object> room = new HashMap<Point, Object>();
		room.put(c.getPoint(), c);
		
		ts.startTurn(c, 1, room);
		assertNotNull(room.get(new Point(5,6)));
	}
	@Test
	public void testTurnSystemMove() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Point startLocation = new Point(5,5);
		Character c = new Character(0, 0, 0, startLocation.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Point, Object> room = new HashMap<Point, Object>();
		room.put(c.getPoint(), c);
		
		assertNotNull(room.get(c.getPoint()));
		ts.move(c, new Point(4,5), room);
		assertNotEquals(startLocation, c.getPoint());
		assertNull(room.get(startLocation));
		assertNotNull(room.get(c.getPoint()));
	}
	@Test
	public void testTurnSystemGetNewLocation() {
		EnemyAI ai = new EnemyAI(0);
		TurnSystem ts = new TurnSystem(ai);
		Point location = new Point(5,5);
		Character c = new Character(0, 0, 0, location.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Point, Object> room = new HashMap<Point, Object>();
		room.put(c.getPoint(), c);
		
		location.setLocation(5, 4);
		Point p = ts.getNewLocation(c, ai.requestMove());
		assertEquals(location, p);
	}
}

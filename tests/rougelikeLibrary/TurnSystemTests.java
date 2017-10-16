package rougelikeLibrary;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.*;

public class TurnSystemTests {
	@Test
	public void testTurnSystemStartTurn() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Character c = new Character(0, 0, 0, 5, 5, new TurnSystem(new EnemyAI(1)));
		HashMap<Position, Object> room = new HashMap<Position, Object>();
		room.put(c.getPosition(), c);
		
		ts.startTurn(c, 1, room);
		assertNotNull(room.get(new Position(5,6)));
	}
	@Test
	public void testTurnSystemMove() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Position startLocation = new Position(5,5);
		Character c = new Character(0, 0, 0, startLocation.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Position, Object> room = new HashMap<Position, Object>();
		room.put(c.getPosition(), c);
		
		assertNotNull(room.get(c.getPosition()));
		ts.move(c, new Position(4,5), room);
		assertNotEquals(startLocation, c.getPosition());
		assertNull(room.get(startLocation));
		assertNotNull(room.get(c.getPosition()));
	}
	@Test
	public void testTurnSystemGetNewLocation() {
		EnemyAI ai = new EnemyAI(0);
		TurnSystem ts = new TurnSystem(ai);
		Position location = new Position(5,5);
		Character c = new Character(0, 0, 0, location.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Position, Object> room = new HashMap<Position, Object>();
		room.put(c.getPosition(), c);
		
		location.setX(5);
		location.setY(4);
		Position p = ts.getNewLocation(c, ai.requestMove());
		assertEquals(location, p);
	}
	
	@Test
	public void testTurnSystemEnterDoor()
	{
		EnemyAI ai = new EnemyAI(0);
		TurnSystem ts = new TurnSystem(ai);
	}
}

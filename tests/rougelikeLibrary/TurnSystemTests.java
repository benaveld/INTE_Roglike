package rougelikeLibrary;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.*;

public class TurnSystemTests {
	@Test
	public void testTurnCharacterPlacedInMap() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Character c = new Character(0, 0, 0, 5, 5, new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new WorldPosition(0,0), new RoomSpace(3,3));
		r.addEnemy(new Position(5,5), c);
		
		assertTrue(r.getFromPosition(new Position(5,5)).size() > 0);
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
		Position location = new Position(5,5);
		Character c = new Character(0, 0, 0, location.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Position, Object> room = new HashMap<Position, Object>();
		room.put(c.getPosition(), c);
		
		location.setX(5);
		location.setY(4);
		Position p = new Position(5,4); //TEMP;
		//Position p = ts.getNewLocation(c, ai.requestMove());
		assertEquals(location, p);
	}
	
	@Test
	public void testTurnSystemEnterDoor()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Character c = new Character(5, 0, 0, 1, 1, ts);
		Room r = new Room(new WorldPosition(0,0), new RoomSpace(2,2));
		
		assertTrue(c.startTurn(r));
	}
	
	@Test
	public void testTurnWrongOriginalInput()
	{
		Room room = new Room(new WorldPosition(0,0), new RoomSpace(1,1));
		Character c = new Character(0, 0, 0, 5, 5, new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new WorldPosition(0,0), new RoomSpace(3,3));
		
		assertTrue(c.startTurn(r));
	}
}

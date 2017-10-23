package rougelikeLibrary;

import static org.junit.Assert.*;

import org.junit.*;

import rougelikeLibrary.Position.CardinalDirection;

public class WallWalkerAITests {

	@Test
	public void testWallWalkerMoveAroundRoom() {
		WallWalkerAI e = new WallWalkerAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		TurnSystem ts = new TurnSystem(e);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		Room room = new Room(new Position(0, 0), new RoomSpace(3, 3));
		room.setPlayer(new Position(0,0), new Player(0,0,0, new Position(0,0), new TurnSystem(new TUI())));
		assertEquals(d, e.requestMove(room,c));
	}

	@Test
	public void testWallWalkerChangeDirection() {
		WallWalkerAI e = new WallWalkerAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		Room room = new Room(new Position(0, 0), new RoomSpace(3, 3));
		room.setPlayer(new Position(0,0), new Player(0,0,0, new Position(0,0), new TurnSystem(new TUI())));
		TurnSystem ts = new TurnSystem(e);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		for (int u = 0; u < 3; u++) {
			for (int i = 0; i < 3; i++) {
				e.requestMove(room,c);
			}
			e.requestMoveAfterFail(room,c);
		}
		assertEquals(d, e.requestMoveAfterFail(room,c));
	}
	
	@Test
	public void testWallWalkerAttackPlayer()
	{
		WallWalkerAI e = new WallWalkerAI(11037);
		Room room = new Room(new Position(0, 0), new RoomSpace(3, 3));
		TurnSystem ts = new TurnSystem(e);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		Player p = new Player(0,0,0, new Position(0,0), new TurnSystem(new TUI()));
		room.setPlayer(new Position(1,1), p);

		CardinalDirection d = CardinalDirection.values()[0];
		c.setPosition(new Position(1,2));
		assertEquals(d,e.requestMove(room, c));
		
		d = CardinalDirection.values()[1];
		c.setPosition(new Position(0,1));
		assertEquals(d,e.requestMove(room, c));
		

		d = CardinalDirection.values()[2];
		c.setPosition(new Position(1,0));
		assertEquals(d,e.requestMoveAfterFail(room, c));
		

		d = CardinalDirection.values()[3];
		c.setPosition(new Position(2,1));
		assertEquals(d,e.requestMoveAfterFail(room, c));
	}

}

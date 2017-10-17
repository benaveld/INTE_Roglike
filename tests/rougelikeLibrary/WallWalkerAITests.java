package rougelikeLibrary;

import static org.junit.Assert.*;

import org.junit.*;

import rougelikeLibrary.Position.CardinalDirection;

public class WallWalkerAITests {

	@Test
	public void testWallWalkerMoveAroundRoom() {
		WallWalkerAI e = new WallWalkerAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		Room room = new Room(new Position(0, 0), new RoomSpace(3, 3));
		assertEquals(d, e.requestMove(room));
	}

	@Test
	public void testWallWalkerChangeDirection() {
		WallWalkerAI e = new WallWalkerAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		Room room = new Room(new Position(0, 0), new RoomSpace(3, 3));
		for (int u = 0; u < 3; u++) {
			for (int i = 0; i < 3; i++) {
				e.requestMove(room);
			}
			e.requestMoveAfterFail(room);
		}
		assertEquals(d, e.requestMoveAfterFail(room));
	}

}

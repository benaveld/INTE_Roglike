package rougelikeLibrary;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.junit.*;

import rougelikeLibrary.Position.CardinalDirection;

public class IOTests {

	private InputStream orgInput;
	private PrintStream orgOutput;
	
	@Before
	public void saveInputStream() {
		orgInput = System.in;
		orgOutput = new PrintStream(System.out);
	}

	@Test
	public void testRequstMovePlayer() {
		CardinalDirection d = CardinalDirection.North;
		String s = "n";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);
		Room room = new Room(new Position(0,0),new RoomSpace(3,3), new HashMap<Position, List<Mappable>>());

		TUI t = new TUI();
		TurnSystem ts = new TurnSystem(t);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		assertEquals(d, t.requestMove(room,c));
	}
	
	@Test
	public void testRequestMovePlayerAllDirections()
	{
		String[] moves = {"n","e","s","w"};
		Room room = new Room(new Position(0,0),new RoomSpace(3,3), new HashMap<Position, List<Mappable>>());
		TUI t = new TUI();
		TurnSystem ts = new TurnSystem(t);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		for (int i = 0; i < 4; i++)
		{
			CardinalDirection d = CardinalDirection.values()[i];
			String s = moves[i];
			InputStream stream = new ByteArrayInputStream(s.getBytes());
			System.setIn(stream);

			assertEquals(d, t.requestMove(room,c));
		}
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequstMovePlayerWrongInput() {
		String s = "g";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);
		Room room = new Room(new Position(0,0),new RoomSpace(3,3), new HashMap<Position, List<Mappable>>());

		TUI t = new TUI();
		TurnSystem ts = new TurnSystem(t);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		t.requestMove(room,c);
	}
	
	@Test
	public void testRequestNotAllowedMove()
	{
		CardinalDirection d = CardinalDirection.North;
		String s = "n";
		String failed = "Move not allowed.";
		InputStream iStream = new ByteArrayInputStream(s.getBytes());
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(oStream);
		System.setIn(iStream);
		System.setOut(printStream);

		Room room = new Room(new Position(0,0),new RoomSpace(3,3), new HashMap<Position, List<Mappable>>());
		
		TUI t = new TUI();
		TurnSystem ts = new TurnSystem(t);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		assertEquals(d, t.requestMoveAfterFail(room,c));
		InputStream oStreamRead = new ByteArrayInputStream(oStream.toByteArray());

		
		Scanner scan = new Scanner(oStreamRead);
		String scannedLine = scan.nextLine();
		scan.close();
		assertEquals(failed,scannedLine);
	}

	@Test
	public void testCalculateMovementBasicAIRandom() {
		EnemyAI e = new EnemyAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		Room room = new Room(new Position(0,0),new RoomSpace(3,3), new HashMap<Position, List<Mappable>>());
		TurnSystem ts = new TurnSystem(e);
		Character c = new Character(0, 0, 0, new Position(0,0), ts);
		assertEquals(d, e.requestMove(room,c));
	}
	
	@Test
	public void testCalculateMovementBasicAINotAllowedMove()
	{
		EnemyAI e = new EnemyAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		TurnSystem ts = new TurnSystem(e);

    Character c = new Character(0, 0, 0, new Position(0,0), ts);
		Room room = new Room(new Position(0,0),new RoomSpace(3,3), new HashMap<Position, List<Mappable>>());

		assertEquals(d, e.requestMoveAfterFail(room,c));
	}

	@After
	public void resetInput() {
		System.setIn(orgInput);
		System.setOut(orgOutput);
	}
}

package rougelikeLibrary;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
		Room room = new Room(new Position(0,0),new RoomSpace(3,3));

		TUI t = new TUI();
		assertEquals(d, t.requestMove(room));
	}
	
	@Test
	public void testRequestMovePlayerAllDirections()
	{
		String[] moves = {"n","e","s","w"};
		Room room = new Room(new Position(0,0),new RoomSpace(3,3));
		TUI t = new TUI();
		for (int i = 0; i < 4; i++)
		{
			CardinalDirection d = CardinalDirection.values()[i];
			String s = moves[i];
			InputStream stream = new ByteArrayInputStream(s.getBytes());
			System.setIn(stream);

			assertEquals(d, t.requestMove(room));
		}
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequstMovePlayerWrongInput() {
		String s = "g";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);
		Room room = new Room(new Position(0,0),new RoomSpace(3,3));

		TUI t = new TUI();
		t.requestMove(room);
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

		Room room = new Room(new Position(0,0),new RoomSpace(3,3));
		
		TUI t = new TUI();
		assertEquals(d, t.requestMoveAfterFail(room));
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
		Room room = new Room(new Position(0,0),new RoomSpace(3,3));
		assertEquals(d, e.requestMove(room));
	}
	
	@Test
	public void testCalculateMovementBasicAINotAllowedMove()
	{
		EnemyAI e = new EnemyAI(11037);
		CardinalDirection d = CardinalDirection.values()[0];
		Room room = new Room(new Position(0,0),new RoomSpace(3,3));
		assertEquals(d, e.requestMoveAfterFail(room));
	}

	@After
	public void resetInput() {
		System.setIn(orgInput);
		System.setOut(orgOutput);
	}
}

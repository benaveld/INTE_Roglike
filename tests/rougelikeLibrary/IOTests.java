package rougelikeLibrary;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import org.junit.*;

import rougelikeLibrary.IO.Direction;

public class IOTests {

	private InputStream orgInput;
	private PrintStream orgOutput;
	private Random r;

	@Before
	public void saveInputStream() {
		orgInput = System.in;
		orgOutput = new PrintStream(System.out);
		r = new Random();
	}

	@Test
	public void testRequstMovePlayer() {
		Direction d = Direction.NORTH;
		String s = "n";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);

		TUI t = new TUI();
		assertEquals(d, t.requestMove());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequstMovePlayerWrongInput() {
		Direction d = Direction.NORTH;
		String s = "g";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);

		TUI t = new TUI();
		t.requestMove();
	}
	
	@Test
	public void testRequestNotAllowedMove()
	{
		Direction d = Direction.NORTH;
		String s = "n";
		String failed = "Move not allowed.";
		InputStream iStream = new ByteArrayInputStream(s.getBytes());
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(oStream);
		System.setIn(iStream);
		System.setOut(printStream);
		
		TUI t = new TUI();
		assertEquals(d, t.requestMoveAfterFail());
		InputStream oStreamRead = new ByteArrayInputStream(oStream.toByteArray());

		
		Scanner scan = new Scanner(oStreamRead);
		String scannedLine = scan.nextLine();
		scan.close();
		assertEquals(failed,scannedLine);
	}

	@Test
	public void testCalculateMovementAIRandom() {
		int i = r.nextInt(4);
		EnemyAI e = new EnemyAI(i);
		Direction d = Direction.values()[i];
		assertEquals(d, e.requestMove());
	}
	
	@Test
	public void testCalculateMovementAINotAllowedMove()
	{
		int i = r.nextInt(4);
		EnemyAI e = new EnemyAI(i);
		Direction d = Direction.values()[i];
		assertEquals(d, e.requestMoveAfterFail());
	}

	@After
	public void resetInput() {
		System.setIn(orgInput);
		System.setOut(orgOutput);
	}
}

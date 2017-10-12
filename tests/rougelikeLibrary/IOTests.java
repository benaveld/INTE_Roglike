package rougelikeLibrary;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import org.junit.*;

public class IOTests {

	private InputStream orgInput;
	private Random r;

	@Before
	public void saveInputStream() {
		orgInput = System.in;
		r = new Random();
	}

	@Test
	public void testCheckTUIInput() {
		TUI t = new TUI();
		assertEquals(System.in, t.getInputStream());
	}

	@Test
	public void testRequstMovePlayer() {
		String s = "n";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);

		TUI t = new TUI(stream);
		assertEquals("n", t.requestMove());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequstMovePlayerWrongInput() {
		String s = "F";
		InputStream stream = new ByteArrayInputStream(s.getBytes());
		System.setIn(stream);

		TUI t = new TUI(stream);
		assertEquals("g", t.requestMove());
	}

	@Test
	public void testCalculateMovementAIRandom() {
		int i = r.nextInt(4);
		EnemyAI e = new EnemyAI(i);
		String s = "";
		switch(i)
		{
		case 0:
			s = "n";
			break;
		case 1:
			s = "e";
			break;
		case 2:
			s = "s";
			break;
		case 3:
			s = "w";
			break;
		}
		assertEquals(s, e.requestMove());
	}

	@After
	public void resetInput() {
		System.setIn(orgInput);
	}
}

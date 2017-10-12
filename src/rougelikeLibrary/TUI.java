package rougelikeLibrary;

import java.io.InputStream;
import java.util.Scanner;

public class TUI extends IO {

	InputStream inputStream;

	public TUI() {
		this.inputStream = System.in;
	}

	public TUI(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String requestMove() {
		String s = getInputFromUser();
		if (!s.equals("n") && !s.equals("e") && !s.equals("s") && !s.equals("w")) {
			throw new IllegalArgumentException("Input needs to be single character and one of: n s e w");
		}
		return s;
	}

	private String getInputFromUser() {
		Scanner scanner = new Scanner(inputStream);
		String s = scanner.nextLine();
		scanner.close();
		return s;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

}

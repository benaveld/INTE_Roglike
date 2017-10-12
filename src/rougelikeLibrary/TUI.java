package rougelikeLibrary;

import java.util.Scanner;

public class TUI extends IO {


	@Override
	public Direction requestMove() {
		String s = getInputFromUser();
		if (s.equals("n"))
		{
			return Direction.NORTH;
		}
		else if (s.equals("e"))
		{
			return Direction.EAST;
		}
		else if (s.equals("s"))
		{
			return Direction.SOUTH;
		}
		else if (s.equals("w"))
		{
			return Direction.WEST;
		}
		throw new IllegalArgumentException("Input must be one of the characters: n e s w");
	}
	
	@Override
	public Direction requestMoveAfterFail()
	{
		System.out.println("Move not allowed.");
		return requestMove();
	}
	
	
	private String getInputFromUser() {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		scanner.close();
		return s;
	}
}

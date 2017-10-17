package rougelikeLibrary;

import java.util.Scanner;

import rougelikeLibrary.Position.CardinalDirection;

public class TUI extends IO {


	@Override
	public CardinalDirection requestMove(Room room) {
		String s = getInputFromUser();
		if (s.equals("n"))
		{
			return CardinalDirection.North;
		}
		else if (s.equals("e"))
		{
			return CardinalDirection.East;
		}
		else if (s.equals("s"))
		{
			return CardinalDirection.South;
		}
		else if (s.equals("w"))
		{
			return CardinalDirection.West;
		}
		throw new IllegalArgumentException("Input must be one of the characters: n e s w");
	}
	
	@Override
	public CardinalDirection requestMoveAfterFail(Room room)
	{
		System.out.println("Move not allowed.");
		return requestMove(room);
	}
	
	
	private String getInputFromUser() {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		scanner.close();
		return s;
	}
}

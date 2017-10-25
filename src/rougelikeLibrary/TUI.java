package rougelikeLibrary;

import java.util.Scanner;

import rougelikeLibrary.Position.CardinalDirection;

public class TUI implements IO {

	/**
	 * Gets a CardinalDirection from this IO by asking user for input. This input is
	 * then converted to a Cardinal Direction
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 * @throws if
	 *             the input from user is wrong, it instead throws that the input is
	 *             incorrect
	 */
	@Override
	public CardinalDirection requestMove(Room room, Character c) {
		String s = getInputFromUser();
		switch (s) {
		case "n":
			return CardinalDirection.North;

		case "e":
			return CardinalDirection.East;

		case "s":
			return CardinalDirection.South;

		case "w":
			return CardinalDirection.West;
		}
		throw new IllegalArgumentException("Input must be one of the characters: n e s w");
	}

	/**
	 * Gets a CardinalDirection from this IO by asking user for input after having
	 * asked once before. This input is then converted to a Cardinal Direction
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 * @throws if
	 *             the input from user is wrong, it instead throws that the input is
	 *             incorrect
	 */
	@Override
	public CardinalDirection requestMoveAfterFail(Room room, Character c) {
		System.out.println("Move not allowed.");
		return requestMove(room, c);
	}

	/**
	 * Gets the input from user by reading what the user types into the console.
	 * 
	 * @return The input that user wrote, converted to lower case.
	 */
	private String getInputFromUser() {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		scanner.close();
		return s.toLowerCase();
	}
}

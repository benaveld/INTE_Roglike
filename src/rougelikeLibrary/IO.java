package rougelikeLibrary;

import rougelikeLibrary.Position.CardinalDirection;

public interface IO {

	/**
	 * Gets a CardinalDirection from this IO.
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 */
	public CardinalDirection requestMove(Room room, Character c);

	/**
	 * 
	 * Gets a CardinalDirection from this IO after having failed once before.
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 */
	public CardinalDirection requestMoveAfterFail(Room room, Character c);
}

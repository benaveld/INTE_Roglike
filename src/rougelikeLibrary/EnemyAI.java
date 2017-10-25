package rougelikeLibrary;

import java.util.Random;

import rougelikeLibrary.Position.CardinalDirection;

public class EnemyAI implements IO {

	private CardinalDirection lastDirection = null;
	private final Random RANDOM;

	/**
	 * Constructor Sets RANDOM to new seed
	 * 
	 * @param seed
	 *            The seed that RANDOM will be based on
	 */

	public EnemyAI(int seed) {
		RANDOM = new Random(seed);
	}

	/**
	 * Gets a CardinalDirection from this IO by AI.
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 */
	@Override
	public CardinalDirection requestMove(Room room, Character c) {
		return calculateNextMove(room);
	}

	/**
	 * Gets a CardinalDirection from this IO by AI after failing once. Continues to
	 * try as long as the new direction is the same as the last.
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 */
	@Override
	public CardinalDirection requestMoveAfterFail(Room room, Character c) {
		CardinalDirection dir = null;
		do {
			dir = calculateNextMove(room);
		} while (dir == lastDirection);
		lastDirection = dir;
		return dir;
	}

	/**
	 * Calculates and returns the new direction by selecting random using RANDOM
	 * 
	 * @param room
	 *            Current active room
	 * @return The selected CardinalDirection
	 */
	protected CardinalDirection calculateNextMove(Room room) {
		int i = RANDOM.nextInt(CardinalDirection.values().length);
		return CardinalDirection.values()[i];
	}

	/**
	 * Get the last direction
	 * 
	 * @return The last selected direction
	 */
	public CardinalDirection getLastDirection() {
		return lastDirection;
	}

	/**
	 * Sets the last used direction
	 * 
	 * @param value
	 *            The latest direction
	 */
	public void setLastDirection(CardinalDirection value) {
		lastDirection = value;
	}
}

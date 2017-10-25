package rougelikeLibrary;

import rougelikeLibrary.Position.CardinalDirection;

public class WallWalkerAI extends EnemyAI {

	private boolean movedUpLast = true;
	private boolean movedRightLast = false;
	private boolean movedYAxisLast = true;

	/**
	 * Constructor Also sets the last direction to NORTH.
	 * 
	 * @param seed
	 *            The seed that RANDOM will use.
	 */
	public WallWalkerAI(int seed) {
		super(seed);
		setLastDirection(CardinalDirection.North);
	}

	/**
	 * Gets a CardinalDirection from this IO by AI. If there is a player next to it,
	 * it always select the direction towards the player.
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 */
	@Override
	public CardinalDirection requestMove(Room room, Character c) {
		CardinalDirection attackPlayer = checkPlayerAround(room, c);
		if (attackPlayer != null) {
			return attackPlayer;
		}
		return getLastDirection();
	}

	/**
	 * Gets a CardinalDirection from this IO by AI after failing once. If there is a
	 * player next to it, it always select the direction towards the player. Saves
	 * the last used direction for next turn.
	 * 
	 * @param room
	 *            Current active room
	 * @param c
	 *            Character connected to this IO
	 * @return The selected CardinalDirection
	 */
	@Override
	public CardinalDirection requestMoveAfterFail(Room room, Character c) {
		CardinalDirection attackPlayer = checkPlayerAround(room, c);
		if (attackPlayer != null) {
			return attackPlayer;
		}
		setLastDirection(calculateNextMove(room));
		return getLastDirection();
	}

	/**
	 * Checks if the player is next to the walker, either to the sides or
	 * above/below.
	 * 
	 * @param room
	 *            Current active room
	 * @param walker
	 *            The walker connected to this AI
	 * @return Either the direction that the player is standing in if its next to
	 *         walker otherwise it returns null
	 */
	private CardinalDirection checkPlayerAround(Room room, Character walker) {
		Position myPosition = walker.getPosition();
		if (myPosition.getX() > 0) {
			if (room.existPlayer(new Position(myPosition.getX() - 1, myPosition.getY()))) {
				return CardinalDirection.West;
			}
		}

    if (myPosition.getX() < room.getRoomSpace().getMaxIndexWidth())
		{
			if (room.existPlayer(new Position(myPosition.getX() + 1, myPosition.getY())))
			{
				return CardinalDirection.East;
			}
		}
		if (myPosition.getY() > 0) {
			if (room.existPlayer(new Position(myPosition.getX(), myPosition.getY() - 1))) {
				return CardinalDirection.North;
			}
		}

    if (myPosition.getY() < room.getRoomSpace().getMaxIndexHeight())
		{
			if (room.existPlayer(new Position(myPosition.getX(), myPosition.getY() + 1)))
			{
				return CardinalDirection.South;
			}
		}
		return null;
	}

	/**
	 * Calculates the next direction to walk. If it hits a wall it changes the axis
	 * its walking and walks the opposite way it walked the last time it walked the
	 * new axis
	 * 
	 * @param room
	 *            Current active room
	 * @return The selected CardinalDirection
	 */
	@Override
	protected CardinalDirection calculateNextMove(Room room) {
		if (movedYAxisLast) {
			movedYAxisLast = false;
			if (movedRightLast) {
				movedRightLast = false;
				return CardinalDirection.West;
			} else {
				movedRightLast = true;
				return CardinalDirection.East;
			}
		} else {
			movedYAxisLast = true;
			if (movedUpLast) {
				movedUpLast = false;
				return CardinalDirection.South;
			} else {
				movedUpLast = true;
				return CardinalDirection.North;
			}
		}
	}
}

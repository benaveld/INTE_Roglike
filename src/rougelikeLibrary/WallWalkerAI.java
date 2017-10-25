package rougelikeLibrary;

import rougelikeLibrary.Position.CardinalDirection;

public class WallWalkerAI extends EnemyAI {

	private boolean movedUpLast = true;
	private boolean movedRightLast = false;
	private boolean movedYAxisLast = true;
	private CardinalDirection lastDir = CardinalDirection.North;
	
	public WallWalkerAI(int seed)
	{
		super(seed);
	}
	
	@Override
	public CardinalDirection requestMove(Room room, Character c) {
		CardinalDirection attackPlayer = checkPlayerAround(room, c);
		if (attackPlayer != null)
		{
			return attackPlayer;
		}
		return lastDir;
	}
	
	@Override
	public CardinalDirection requestMoveAfterFail(Room room, Character c)
	{
		CardinalDirection attackPlayer = checkPlayerAround(room, c);
		if (attackPlayer != null)
		{
			return attackPlayer;
		}
		lastDir = calculateNextMove(room);
		return lastDir;
	}
	
	private CardinalDirection checkPlayerAround(Room room, Character walker)
	{
		Position myPosition = walker.getPosition();
		if (myPosition.getX() > 0)
		{
			if (room.existPlayer(new Position(myPosition.getX() - 1, myPosition.getY())))
			{
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
		if (myPosition.getY() > 0)
		{
			if (room.existPlayer(new Position(myPosition.getX(), myPosition.getY() - 1)))
			{
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
	
	@Override
	protected CardinalDirection calculateNextMove(Room room)
	{
		if (movedYAxisLast)
		{
			movedYAxisLast = false;
			if (movedRightLast)
			{
				movedRightLast = false;
				return CardinalDirection.West;
			}
			else
			{
				movedRightLast = true;
				return CardinalDirection.East;
			}
		}
		else 
		{
			movedYAxisLast = true;
			if (movedUpLast)
			{
				movedUpLast = false;
				return CardinalDirection.South;
			}
			else
			{
				movedUpLast = true;
				return CardinalDirection.North;
			}
		}
	}
}

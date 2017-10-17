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
	public CardinalDirection requestMove(Room room) {
		return lastDir;
	}
	
	@Override
	public CardinalDirection requestMoveAfterFail(Room room)
	{
		return calculateNextMove(room);
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

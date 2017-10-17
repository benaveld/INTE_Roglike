package rougelikeLibrary;

import java.util.Random;

import rougelikeLibrary.Position.CardinalDirection;

public class EnemyAI extends IO {

	private CardinalDirection lastDirection = null;
	private Random r;
	
	public EnemyAI(int seed)
	{
		r = new Random(seed);
	}

	@Override
	public CardinalDirection requestMove(Room room) {
		return calculateNextMove(room);
	}
	
	@Override
	public CardinalDirection requestMoveAfterFail(Room room)
	{
		CardinalDirection dir = null;
		do
		{
			dir = calculateNextMove(room);
		} while (dir == lastDirection);
		lastDirection = dir;
		return dir;
	}

	protected CardinalDirection calculateNextMove(Room room) {
		int i = r.nextInt(CardinalDirection.values().length);
		return CardinalDirection.values()[i];
	}
}

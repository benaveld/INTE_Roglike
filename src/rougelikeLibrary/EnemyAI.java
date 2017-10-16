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
	public CardinalDirection requestMove() {
		return calculateNextMove();
	}
	
	@Override
	public CardinalDirection requestMoveAfterFail()
	{
		CardinalDirection dir = null;
		do
		{
			dir = calculateNextMove();
		} while (dir != lastDirection);
		return dir;
	}

	private CardinalDirection calculateNextMove() {
		int i = r.nextInt(CardinalDirection.values().length);
		System.out.println(i);
		return CardinalDirection.values()[i];
	}
}

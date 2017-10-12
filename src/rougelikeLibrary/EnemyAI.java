package rougelikeLibrary;

public class EnemyAI extends IO {

	private int randomDirection;
	private int lastDirection = -1;
	
	public EnemyAI(int direction) {
		this.randomDirection = direction;
	}

	@Override
	public Direction requestMove() {
		return calculateNextMove();
	}
	
	@Override
	public Direction requestMoveAfterFail()
	{
		if (randomDirection != lastDirection)
		{
			return calculateNextMove();
		}
		return null;
	}

	private Direction calculateNextMove() {
		return Direction.values()[randomDirection];
	}
}

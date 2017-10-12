package rougelikeLibrary;

public class EnemyAI extends IO {

	private int randomDirection;
	private static final String[] DIRECTIONS = { "n", "e", "s", "w" };

	public EnemyAI(int direction) {
		this.randomDirection = direction;
	}

	@Override
	public String requestMove() {
		return calculateNextMove();
	}

	private String calculateNextMove() {
		return DIRECTIONS[randomDirection];
	}
}

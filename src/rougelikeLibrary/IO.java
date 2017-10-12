package rougelikeLibrary;

public abstract class IO {

	enum Direction {NORTH,EAST,SOUTH,WEST};
	
	public abstract Direction requestMove();	
	
	public abstract Direction requestMoveAfterFail();
}

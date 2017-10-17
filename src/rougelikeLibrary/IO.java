package rougelikeLibrary;

import rougelikeLibrary.Position.CardinalDirection;

public abstract class IO {

	
	public abstract CardinalDirection requestMove(Room room);	
	
	public abstract CardinalDirection requestMoveAfterFail(Room room);
}

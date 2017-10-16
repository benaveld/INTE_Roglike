package rougelikeLibrary;

import rougelikeLibrary.Position.CardinalDirection;

public abstract class IO {

	
	public abstract CardinalDirection requestMove();	
	
	public abstract CardinalDirection requestMoveAfterFail();
}

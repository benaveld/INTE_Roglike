package rougelikeLibrary;

import java.awt.Point;
import java.util.HashMap;

import rougelikeLibrary.IO.Direction;

public class TurnSystem {
	
	private IO io;
	
	public TurnSystem(IO io) {
		this.io = io;
	}
	
	public void startTurn(Character character, int moves, HashMap<Point, Object> room) {
		Point newLocation = getNewLocation(character, io.requestMove());
				
		if(room.get(newLocation) == null) {
			move(character, newLocation, room);
		} else {
			int safteCount = 0;
			do {
				newLocation = getNewLocation(character, io.requestMoveAfterFail());
				if(room.get(newLocation) == null) {
					move(character, newLocation, room);
					break;
				}
				safteCount++;
			} while(safteCount < 10);
		}
	}
	
	public void move(Character character, Point newLocation, HashMap<Point, Object> room) {
		room.remove(character.getPoint());
		room.put(newLocation, character);
		character.setPoint(newLocation);
	}
	
	public Point getNewLocation(Character character, Direction dir) {
		Point newLocation = character.getPoint().getLocation();
		switch (dir) {
		case NORTH:
			newLocation.translate(0, -1);
			break;

		case WEST:
			newLocation.translate(-1, 0);
			break;
			
		case SOUTH:
			newLocation.translate(0, 1);
			break;
			
		case EAST:
			newLocation.translate(1, 0);
			break;
			
		default:
			throw new RuntimeException("IO return a invalid direction.");
		}
		return newLocation;
	}

}

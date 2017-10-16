package rougelikeLibrary;

import java.awt.Point;
import java.util.HashMap;

import rougelikeLibrary.IO.Direction;

public class TurnSystem {
	
	private IO io;
	
	public TurnSystem(IO io) {
		this.io = io;
	}
	
	public void startTurn(Character character, int moves, HashMap<Position, Object> room) {
		Position newLocation = getNewLocation(character, io.requestMove());
				
		if(room.get(newLocation) == null) {
			move(character, newLocation, room);
		} else {
			int safetyCount = 0;
			do {
				newLocation = getNewLocation(character, io.requestMoveAfterFail());
				if(room.get(newLocation) == null) {
					move(character, newLocation, room);
					break;
				}
				safetyCount++;
			} while(safetyCount < 10);
		}
	}
	
	public void move(Character character, Position newLocation, HashMap<Position, Object> room) {
		room.remove(character.getPosition());
		room.put(newLocation, character);
		character.setPosition(newLocation);
	}
	
	public Position getNewLocation(Character character, Direction dir) {
		Position newLocation = character.getPosition();
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

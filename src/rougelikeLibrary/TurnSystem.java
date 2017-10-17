package rougelikeLibrary;

import java.util.HashMap;
import java.util.List;

import rougelikeLibrary.Position.CardinalDirection;

public class TurnSystem {

	private IO io;

	public TurnSystem(IO io) {
		this.io = io;
	}

	public boolean startTurn(Character character, int moves, Room room) {

		RoomSpace rs = room.getRoomSpace();
		Position newPosition = getnewPosition(character, io.requestMove());
		if (newPosition == null || !newPosIsInsideRoomSpace(newPosition, rs)) {
			int safetyCount = 0;
			do {
				newPosition = getnewPosition(character, io.requestMoveAfterFail());
				if (newPosition != null && newPosIsInsideRoomSpace(newPosition, rs)) {
					break;
				} else {
					safetyCount++;
				}
			} while (safetyCount < 20);
			if (safetyCount >= 20) {
				return false;
			}
		}
		if (room.getFromPosition(newPosition).size() == 0) {
			// Room.Move(character.getPos(),newPosition)
		} else if (doorIsInMappable(character, room.getFromPosition(newPosition), newPosition)) {
			return true;
		}
		if (moves > 0) {
			moves--;
			return startTurn(character, moves, room);
		}
		return false;
	}

	private boolean newPosIsInsideRoomSpace(Position pos, RoomSpace rs) {
		if (pos.getX() < rs.getWidth() && pos.getX() >= 0 && pos.getY() < rs.getHeight() && pos.getY() >= 0) {
			return true;
		}
		return false;
	}

	private boolean doorIsInMappable(Character character, List<Mappable> list, Position newPosition) {
		boolean foundDoor = false;
		boolean moveToNewSpot = false;
		for (Mappable m : list) {
			if (m instanceof Item) {
				character.getInventory().add((Item) m);
			} else if (m instanceof Character) {
				Character enemy = (Character) m;
				enemy.takeDamage(character.getDamage());
				moveToNewSpot = false;
			} else if (m instanceof Door) {
				foundDoor = true;
			}
		}
		if (moveToNewSpot) {
			// Room.Move(character.getPos(),newPosition)
		}
		return foundDoor;
	}

	public void move(Character character, Position newPosition, HashMap<Position, Object> room) {
		room.remove(character.getPosition());
		room.put(newPosition, character);
		character.setPosition(newPosition);
	}

	private Position getnewPosition(Character character, CardinalDirection dir) {
		Position newPosition = character.getPosition();
		try {
			switch (dir) {
			case North:
				newPosition.translate(0, -1);
				break;

			case West:
				newPosition.translate(-1, 0);
				break;

			case South:
				newPosition.translate(0, 1);
				break;

			case East:
				newPosition.translate(1, 0);
				break;

			default:
				throw new RuntimeException("IO return a invalid direction.");
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return newPosition;
	}

}

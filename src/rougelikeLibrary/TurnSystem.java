package rougelikeLibrary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import rougelikeLibrary.Position.CardinalDirection;

public class TurnSystem {

	private IO io;

	public TurnSystem(IO io) {
		this.io = io;
	}

	public boolean startTurn(Character character, int moves, Room room) {

		RoomSpace rs = room.getRoomSpace();
		Position newPosition = character.getPosition().translateCardinalDirection(io.requestMove(room, character));
		if (newPosition == null || !newPosIsInsideRoomSpace(newPosition, rs)) {
			int safetyCount = 0;
			do {
				newPosition = character.getPosition().translateCardinalDirection(io.requestMoveAfterFail(room, character));
				if (newPosition != null && newPosIsInsideRoomSpace(newPosition, rs)) {
					break;
				} else {
					safetyCount++;
				}
			} while (safetyCount < 100);
			if (safetyCount >= 100) {
				return false;
			}
		}
		if (room.getFromPosition(newPosition).size() == 0) {
			room.moveCharacter(character.getPosition(), newPosition);
		} else if (doorIsInMappable(character, room, newPosition)) {
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

	private boolean doorIsInMappable(Character character, Room r, Position newPosition) {
		boolean moveToNewSpot = true;
		Mappable foundDoor = null;
		if (r.getPlayerPosition() != null && r.getPlayerPosition().equals(newPosition) && !(character instanceof Player)) {
			Character player = r.getPlayer();
			player.takeDamage(character.getDamage());
			moveToNewSpot = false;
		}
		else if (r.existEnemy(newPosition) && character instanceof Player)
		{
			Character enemy = r.getCharacter(newPosition);
			enemy.takeDamage(character.getDamage());
			moveToNewSpot = false;
		}
		if (moveToNewSpot) {
			List<Mappable> list = r.getFromPosition(newPosition);
			for (Mappable m : list) {
				if (m instanceof Item) {
					character.getInventory().add((Item) m);
				} else if (m instanceof Door) {
					foundDoor = m;
				}
			}
			list.clear();
			if (foundDoor != null)
			{
				list.add(foundDoor);
			}
			r.moveCharacter(character.getPosition(), newPosition);
		}
		return foundDoor != null;
	}

	public void move(Character character, Position newPosition, HashMap<Position, Object> room) {
		room.remove(character.getPosition());
		room.put(newPosition, character);
		character.setPosition(newPosition);
	}
	/*
	private Position getNewPosition(Character character, CardinalDirection dir) {
		try {
			Position pos = character.getPosition();
			switch (dir) {
			case North:
				return pos.translate(0, -1);

			case West:
				return pos.translate(-1, 0);

			case South:
				return pos.translate(0, 1);

			case East:
				return pos.translate(1, 0);
				
			default:
				return null;
				
			}
		} catch (RuntimeException e) {
			return null;
		}
	}
	*/
}

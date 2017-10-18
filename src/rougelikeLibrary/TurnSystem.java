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
		Position newPosition = getnewPosition(character, io.requestMove(room, character));
		if (newPosition == null || !newPosIsInsideRoomSpace(newPosition, rs)) {
			int safetyCount = 0;
			do {
				newPosition = getnewPosition(character, io.requestMoveAfterFail(room, character));
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

	private Position getnewPosition(Character character, CardinalDirection dir) {
		int x = character.getPosition().getX();
		int y = character.getPosition().getY();
		Position newPosition = new Position(x, y); // Need new object because of translate
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

			}
		} catch (Exception e) {
			return null;
		}
		return newPosition;
	}

}

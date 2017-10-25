package rougelikeLibrary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import rougelikeLibrary.Position.CardinalDirection;

public class TurnSystem {

	private final IO IO;

	private enum States {
		INPUT, CHECK_CHAR, ATTACK, MOVE, CHECK_MAPPABLES, PICKUP, END
	};

	private States currentState;
	private Position newPos;
	private ArrayList<Item> itemsToAdd;
	private boolean foundDoor = false;

	/**
	 * Constructor
	 * 
	 * @param io
	 *            Input that this TurnSystem will request for moves
	 */
	public TurnSystem(IO io) {
		this.IO = io;
	}

	/**
	 * Main function of TurnSystem, runs through the states to to get input, move
	 * player, check for objects in spot and pick them up
	 * 
	 * @param character
	 *            The character connected to this TurnSystem
	 * @param moves
	 *            How many times the character is allowed to move
	 * @param room
	 *            The current room that the character is in
	 * @return If the character end
	 */
	public boolean startTurn(Character character, int moves, Room room) {
		currentState = States.INPUT;
		foundDoor = false;
		boolean loop = true;
		while (loop) {
			switch (currentState) {
			case INPUT:
				currentState = getInput(character, room);
				break;

			case CHECK_CHAR:
				currentState = checkForCharacter(character, room);
				break;

			case ATTACK:
				currentState = attackNewPos(character, room);
				break;

			case MOVE:
				currentState = moveCharacter(character, room);
				break;

			case CHECK_MAPPABLES:
				currentState = checkMappablesInSpot(character, room);
				break;

			case PICKUP:
				currentState = pickUpItems(character);
				break;

			case END:
				moves--;
				if (moves > 0 && !foundDoor) {
					currentState = States.INPUT;
				} else {
					loop = false;
				}
			}
		}
		return foundDoor;
	}

	/**
	 * Gets input from IO system
	 * 
	 * @param character
	 *            The character connected to TurnSystem
	 * @param room
	 *            The current room that character is in
	 * @return END if failed to find new position or CHECK_ENEMY if found new
	 *         position
	 */
	private States getInput(Character character, Room room) {
		RoomSpace rs = room.getRoomSpace();
		int safetyCount = 0;
		int maxSafetyCount = 100;
		Position newPosition;
		try {
			newPosition = character.getPosition().translateCardinalDirection(IO.requestMove(room, character));
		} catch (IllegalArgumentException e) {
			System.out.println(e);
			newPosition = null;
		}
		while ((newPosition == null || !newPosIsInsideRoomSpace(newPosition, rs))) {
			safetyCount++;
			if (safetyCount > maxSafetyCount) {
				return States.END;
			} else {
				try {
					newPosition = character.getPosition().translateCardinalDirection(IO.requestMove(room, character));
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				}
			}
		}
		newPos = newPosition;
		return States.CHECK_CHAR;
	}

	/**
	 * Checks if position is inside selected RoomSpace
	 * 
	 * @param pos
	 *            Position to check
	 * @param rs
	 *            RoomSpace to compare to
	 * @return True if inside space, false if outside
	 */
	private boolean newPosIsInsideRoomSpace(Position pos, RoomSpace rs) {
		if (pos.getX() < rs.getWidth() && pos.getX() >= 0 && pos.getY() < rs.getHeight() && pos.getY() >= 0) {
			return true;
		}
		return false;
	}
  
	/**
	 * Checks if their is an other Character in the new position
	 * 
	 * @param character
	 *            Character connected to this TurnSystem
	 * @param room
	 *            Room that character is currently in
	 * @return ATTACK if there is a character in spot and is attackable, MOVE if the
	 *         spot is empty, or END if there is a character but it is not
	 *         attackable.
	 */
	private States checkForCharacter(Character character, Room room) {
		if (room.getPlayerPosition() != null && room.getPlayerPosition().equals(newPos)
				&& !(character instanceof Player)) {
			return States.ATTACK;
		} else if (room.existEnemy(newPos) && character instanceof Player) {
			return States.ATTACK;
		} else if (!room.existCharacter(room.getFromPosition(newPos))) {
			return States.MOVE;
		} else {
			return States.END;
		}
	}

	/**
	 * Attacks character that is standing in the new position.
	 * 
	 * @param character
	 *            Character connected
	 * @param room
	 *            Room that character is currently in
	 * @return Always return END
	 */
	private States attackNewPos(Character character, Room room) {
		Character receiver = room.getCharacter(newPos);
		receiver.takeDamage(character.getDamage());
		return States.END;
	}

	/**
	 * Moves character from current position to new position
	 * 
	 * @param character
	 *            Character connected to this TurnSystem
	 * @param room
	 *            Room that character is currently in
	 * @return Always return CHECK_MAPPABLES
	 */
	private States moveCharacter(Character character, Room room) {
		room.moveCharacter(character.getPosition(), newPos);
		return States.CHECK_MAPPABLES;
	}

	/**
	 * Checks for all Mappable in new position. If there is items it adds it to a
	 * list of items to add to player. If there is a door it saves it away to later
	 * re added it to list if the list is cleared. If the spot contains items, they
	 * are saved away and the list is later cleared of all mappables, and re-adds
	 * character and door if there was one.
	 * 
	 * @param character
	 *            Character connected to this TurnSystem
	 * @param room
	 *            Room that character is currently in
	 * @return If there were items, it returns PICKUP, otherwise it returns END
	 */
	private States checkMappablesInSpot(Character character, Room room) {
		Door tempDoorHolder = null;
		List<Mappable> mappables = room.getFromPosition(newPos);
		if (room.getFromPosition(newPos).size() > 1) {
			itemsToAdd = new ArrayList<Item>();
			for (Mappable m : mappables) {

				if (m instanceof Item) {
					itemsToAdd.add((Item) m);
				} else if (m instanceof Door) {
					tempDoorHolder = (Door) m;
					if (character instanceof Player)
					{
					foundDoor = true;
					}
				}
			}

			if (itemsToAdd.size() > 0) {
				mappables.clear();
				if (tempDoorHolder != null) {
					mappables.add(tempDoorHolder);
				}
				mappables.add(character);
				return States.PICKUP;
			} else {
				return States.END;
			}
		} else {
			return States.END;
		}
	}

	/**
	 * Adds all the items found to the characters inventory
	 * 
	 * @param character
	 *            Character connected to this TurnSystem
	 * @return Always returns END
	 */
	private States pickUpItems(Character character) {
		Inventory inv = character.getInventory();
		for (Item i : itemsToAdd) {
			inv.add(i);
		}
		itemsToAdd = null;
		return States.END;
	}
}

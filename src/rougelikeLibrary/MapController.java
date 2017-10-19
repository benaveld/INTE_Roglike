package rougelikeLibrary;

import java.util.HashMap;
import java.util.Map;


/**
 * Controls the world space which consists of a grid where each coordinate in the grid is a room.
 * A new room can only be created on a coordinate if there is no prior room and the doors between adjacent rooms align.
 * The coordinate system starts with [0, 0] top-left.
 * As an example:
 * a room at coordinate [3, 5] with doors left and top, can only have a room at coordinate [2, 5] if it has a door to the right and a room at coordinate [3, 4] if it has a door at the bottom.
 */
public class MapController {
    private Room currentRoom;
    private Map<Position, Room> map = new java.util.HashMap<>();


    /**
     * Constructor
     *
     * @param centerRoom the initial room. Preferably in the middle.
     */
    public MapController(Room centerRoom) {
        if (centerRoom == null) {
            throw new IllegalArgumentException("No current room available.");
        }
        currentRoom = centerRoom;
        map.put(currentRoom.getPosition(), currentRoom);
    }


    /**
     * Plays current room.
     * @return next entered room coordinate as WorldPosition.
     */
    public Position playCurrentRoom() throws IllegalArgumentException {
        Position.CardinalDirection nextRoomDirection = currentRoom.play();
        Position nextRoomPosition = currentRoom.getPosition().getNewPositionFromCardinalDirection(nextRoomDirection);
        return nextRoomPosition;
    }


    /**
     * Sets new current room
     * @param room the new room to be the current.
     * @throws IllegalArgumentException if room is null.
     */
    public void setCurrentRoom(Room room) throws IllegalArgumentException {
        if (room == null) {
            throw new IllegalArgumentException("Room can't be null.");
        }
        currentRoom = room;
    }


    /**
     * Get current room
     * @return the current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }


    /**
     * Adds a room to the map with the world position from the room.
     * @param room the room to add
     * @throws IllegalArgumentException if either room is null or there already exists a room at the coordinate.
     */
    public void addRoom(Room room) throws IllegalArgumentException {
        if (room == null) {
            throw new IllegalArgumentException("Can't add null room.");
        }

        if (roomExist(room.getPosition())) {
            throw new IllegalArgumentException("Can't create room at coordinate " + room.getPosition() + " because there already exists a room.");
        }

        map.put(room.getPosition(), room);
    }


    /**
     * Check if room exist at the soecified coordinate
     * @param position the coordinate to check for existing room.
     * @return true if the coordinate is free otherwise false if there exists a room
     */
    public boolean roomExist(Position position) {
        return map.get(position) != null;
    }


    /**
     * Gets the room at the specified world coordinate.
     * @param position the position for the room.
     * @return the room at the position.
     * @exception IllegalArgumentException if no room exists at the specified world coordinate.
     */
    public Room getRoom(Position position) throws IllegalArgumentException {
        Room room = map.get(position);
        if (room == null) {
            throw new IllegalArgumentException("No room exists at the specified coordinate " + position);
        }

        return map.get(position);
    }


    /**
     * Returns a List of cardinal directions and the permissions for doors for a room at the specified world position.
     * Example:
     *      A room that has a door in north must have a adjacent room in north with a door to the south.
     *      The opposite is true also, a room with no door to the south can not have a adjacent room to the south with a door to the north.
     * @param newRoomPosition the position to test for a room
     * @return list of cardinal directions and their permission for doors.
     * @throws IllegalArgumentException if the worldPosition is not empty.
     */
    public java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission>
        getCardinalDirectionPermissions(Position newRoomPosition) throws IllegalArgumentException {

        if (roomExist(newRoomPosition)) {
            throw new IllegalArgumentException("Coordinate " + newRoomPosition + " is not empty.");
        }

        java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission>
                cardinalDirectionPermissions = new HashMap<>();

        cardinalDirectionPermissions.put(Position.CardinalDirection.North,
                getCardinalDirectionPermission(newRoomPosition, Position.CardinalDirection.North));

        cardinalDirectionPermissions.put(Position.CardinalDirection.South,
                getCardinalDirectionPermission(newRoomPosition, Position.CardinalDirection.South));

        cardinalDirectionPermissions.put(Position.CardinalDirection.West,
                getCardinalDirectionPermission(newRoomPosition, Position.CardinalDirection.West));

        cardinalDirectionPermissions.put(Position.CardinalDirection.East,
                getCardinalDirectionPermission(newRoomPosition, Position.CardinalDirection.East));

        return cardinalDirectionPermissions;
    }


    /**
     * Retrieves permissions for a door in cardinal direction for newRoomPosition.
     * @param newRoomPosition the position for the room to test for.
     * @param cardinalDirection the cardinal direction from the room to get permissions for.
     *      I.e for a given position with cardinal direction North, the permissions is fetched for a door in the north direction in the room.
     * @return the permission for a door in the cardinal direction (Optional, Disallowed or Mandatory)
     */
    private Position.CardinalDirectionPermission getCardinalDirectionPermission(Position newRoomPosition, Position.CardinalDirection cardinalDirection) {
        Position roomPositionInCardinalDirection;

        try {
            roomPositionInCardinalDirection = newRoomPosition.getNewPositionFromCardinalDirection(cardinalDirection);
        } catch (IllegalArgumentException iae) {
            // World position for a room in the cardinal direction is out of bounds.
            return Position.CardinalDirectionPermission.Disallowed;
        }

        // No room exists in the cardinal direction
        if (!roomExist(roomPositionInCardinalDirection)) {
            return Position.CardinalDirectionPermission.Optional;
        }

        // Has door in the opposite direction
        if (getRoom(roomPositionInCardinalDirection).existDoor(getOppositeCardinalDirection(cardinalDirection))) {
            return Position.CardinalDirectionPermission.Mandatory;
        } else {
            return Position.CardinalDirectionPermission.Disallowed;
        }
    }


    /**
     * Get the opposite cardinal direction
     * @param cardinalDirection cardinal direction
     * @return return the opposite direction
     */
    public Position.CardinalDirection getOppositeCardinalDirection(Position.CardinalDirection cardinalDirection) {
        Position.CardinalDirection oppositeCardinalDirection = null;

        switch (cardinalDirection) {
            case North:
                oppositeCardinalDirection = Position.CardinalDirection.South;
                break;
            case South:
                oppositeCardinalDirection = Position.CardinalDirection.North;
                break;
            case West:
                oppositeCardinalDirection = Position.CardinalDirection.East;
                break;
            case East:
                oppositeCardinalDirection = Position.CardinalDirection.West;
                break;
        }

        return oppositeCardinalDirection;
    }
}
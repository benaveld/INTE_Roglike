package rougelikeLibrary;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main container for a room in the grid that makes up the game world
 */
public class Room {
    private static final Logger log = Logger.getLogger(Room.class.getName());
    private final Position position;
    private final RoomSpace roomSpace;
    private Map<Position, List<Mappable>> roomMap;


    public Room(Position worldPosition, RoomSpace roomSpace) {
        position = worldPosition;
        this.roomSpace = roomSpace;
        this.roomMap = new HashMap<>();
    }


    /**
     * Constructor
     *
     * @param worldPosition position for the room in world space
     * @param roomMap room map for the room
     */
    public Room(Position worldPosition, RoomSpace roomSpace, Map<Position, List<Mappable>> roomMap) {
        position = worldPosition;
        this.roomSpace = roomSpace;
        this.roomMap = roomMap;
    }


    /**
     * Retrieves the position for this room in world space.
     * @return the WorldPosition for this room.
     */
    public Position getPosition() {
        return position;
    }


    /**
     * @return room space
     */
    public RoomSpace getRoomSpace() {
        return roomSpace;
    }


    /**
     * @return room map for the room
     */
    public Map<Position, List<Mappable>> getRoomMap() {
        return roomMap;
    }


    // @TODO implement move(pos to pos)


    /**
     * Plays this room.
     * @return the new direction for next room to enter.
     */
    public Position.CardinalDirection play() {
        return Position.CardinalDirection.North;
    }
  
  
    public boolean existPlayer(Position position) {
        return playerPosition.equals(position);


    /**
     * Get mappables for the position
     * @param position the position to get mappables from
     * @return a list och mappables
     */
    public List<Mappable> getFromPosition(Position position) {
        List<Mappable> mappables = roomMap.get(position);

        if (mappables == null) {
            mappables = new ArrayList<>();
            roomMap.put(position, mappables);
        }
        return mappables;
    }


    /**
     * Adds door to the position
     * @param cardinalDirection the cardinal direction to add the door to.
     */
    public void addDoor(Position.CardinalDirection cardinalDirection) {
        Position position = getDoorPosition(cardinalDirection);
        List<Mappable> mappables = getFromPosition(position);
        mappables.add(new Door());
    }


    /**
     * Returns the position for a given cardinal direction for a door.
     * @param cardinalDirection the cardinal direction to get a position for
     */
    public Position getDoorPosition(Position.CardinalDirection cardinalDirection) {
        // width: 24
        // cx: 23
        // center_x: 11,5 = 11

        int x = roomSpace.getWidth() - 1;
        int y = roomSpace.getHeight() - 1;

        switch (cardinalDirection) {
            case North:
                x = x / 2;
                y = 0;
                break;

            case South:
                x = x / 2;
                break;

            case West:
                x = 0;
                y = y / 2;
                break;

            case East:
                y = y / 2;
                break;
        }

        return new Position(x, y);
    }


    /**
     * Checks if there exist a door in this room at the cardinal direction.
     * @param cardinalDirection the cardinal direction to check for a door.
     * @return true if there exist a door at the cardinal direction otherwise false.
     */
    public boolean existDoor(Position.CardinalDirection cardinalDirection) {
        Position position = getDoorPosition(cardinalDirection);
        return existType(roomMap.get(position), Door.class);
    }


    /**
     * Checks if there exist a door at the specific position in this room.
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existDoor(Position position) {
        return existType(roomMap.get(position), Door.class);
    }


    /**
     * Check if given exact class type exists in list
     * @param mappables the object to check for type
     * @param typeClass type class type to check against
     * @return true if object class type and type class match otherwise false.
     */
    public boolean existType(List<Mappable> mappables, Class typeClass) {
        if (mappables == null) {
            return false;
        }

        for (Mappable mappable : mappables) {
            if (mappable.getClass() == typeClass) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adds an enemy to the room space
     * @param position the position for the enemy. If there's already an enemy at the position, it will be overwritten.
     * @param enemy the item to add.
     */
    public void addEnemy(Position position, Character enemy) {
        List<Mappable> mappables = getFromPosition(position);
        Iterator mappablesIterator = mappables.iterator();

        while (mappablesIterator.hasNext()) {
            Mappable mappable = (Mappable) mappablesIterator.next();
            if (mappable instanceof Enemy) {
                mappablesIterator.remove();
            }
        }
        mappables.add(enemy);
    }


    /**
     * Gets the enemy at the given position.
     * @param position the position to retrieve the enemy from.
     * @return an enemy object or null if there's no on the position.
     */
    public Character getEnemy(Position position) {
        Character character = getCharacter(position);
        return ((character != null) && (character instanceof Enemy)) ? character : null;
    }

    /**
     * Checks if there exist a item at the specific position in this room.
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existItem(Position position) {
        return existType(roomMap.get(position), Item.class);
    }


    /**
     * Checks if there exist a enemy at the specific position in this room.
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existEnemy(Position position) {
        return existType(roomMap.get(position), Enemy.class);
    }


    /**
     * Verifies if list of mappables contains a object that inherits from character.
     * @param mappables the list of mappable objects.
     * @return true if mappable inheriting from character is found otherwise false.
     */
    public boolean existCharacter(List<Mappable> mappables) {
        if (mappables == null) {
            return false;
        }

        for (Mappable mappable : mappables) {
            if (mappable instanceof Character) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adds item to specific position
     * @param position position to add the item to
     * @param item the item to add
     */
    public void addItem(Position position, Item item) {
        List<Mappable> mappables = getFromPosition(position);
        mappables.add(item);
    }


    /**
     * Get the number of doors in the room.
     * @return number of doors.
     */
    public int getDoorsCount() {
        int doors = 0;

        for(Map.Entry<Position, List<Mappable>> entrySet : roomMap.entrySet()) {
            List<Mappable> mappables = entrySet.getValue();
            for(Mappable mappable : mappables) {
                if (mappable instanceof Door) {
                    doors++;
                }
            }
        }
        return doors;
    }


    /**
     * If player not currently exist in room, set the player object at specified position.
     * Otherwise if player exists, the existing player object is moved to the new position.
     * @param position for the player
     * @param player the player object
     * @throws IllegalAccessError if there already exist a character at the position
     */
    public void setPlayer(Position position, Character player) throws IllegalArgumentException {
        if (existCharacter(getFromPosition(position))) {
            throw new IllegalArgumentException("Position already contains a character");
        }

        Position playerPosition = getPlayerPosition();
        if (playerPosition == null) {
            getFromPosition(position).add(player);
        } else {
            moveCharacter(playerPosition, position);
        }
    }


    /**
     * @return null if no player is found, otherwise a position for the player
     */
    public Position getPlayerPosition() {
        for(Map.Entry<Position, List<Mappable>> entrySet : roomMap.entrySet()) {
            List<Mappable> mappables = entrySet.getValue();
            for(Mappable mappable : mappables) {
                if (mappable instanceof Player) {
                    return entrySet.getKey();
                }
            }
        }
        return null;
    }


    /**
     * Checks if player exists on position.
     * @param position to check for
     * @return true if there is otherwise false
     */
    public boolean existPlayer(Position position) {
        Position playerPosition = getPlayerPosition();
        return (playerPosition != null) && playerPosition.equals(position);
    }


    /**
     * Get the player
     * @return the player in the room. If there is no player, null is returned.
     */
    public Character getPlayer() {
        return getCharacter(getPlayerPosition());
    }


    /**
     * Move character from one position to an other.
     * If there is no character in from position method returns.
     * @param fromPosition
     * @param toPosition
     * @throws IllegalAccessError if there's already a character in to position.
     * There can only be one character per position.
     */
    public void moveCharacter(Position fromPosition, Position toPosition) throws IllegalArgumentException {
        if (fromPosition.equals(toPosition)) {
            return;
        }

        List<Mappable> mappablesFrom = getFromPosition(fromPosition);
        List<Mappable> mappablesTo = getFromPosition(toPosition);

        if (!existCharacter(mappablesFrom)) {
            return;
        }
        if (existCharacter(mappablesTo)) {
            throw new IllegalArgumentException("To position already contains a character.");
        }

        Iterator fromIterator = mappablesFrom.iterator();
        while (fromIterator.hasNext()) {
            Mappable mappable = (Mappable) fromIterator.next();
            if (mappable instanceof Character) {
                mappablesTo.add(mappable);
                fromIterator.remove();

                roomMap.put(fromPosition, mappablesFrom);
                roomMap.put(toPosition, mappablesTo);
                return;
            }
        }
    }


    /**
     * Gets the character at the position
     * @param position the position
     * @return a character if there's one otherwise null.
     */
    public Character getCharacter(Position position) {
        if (position != null) {
            List<Mappable> mappables = roomMap.get(position);
            if (mappables != null) {
                for (Mappable mappable : mappables) {
                    if (mappable instanceof Character) {
                        return (Character) mappable;
                    }
                }
            }
        }
        return null;
    }

}

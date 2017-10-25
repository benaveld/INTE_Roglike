package rougelikeLibrary;


import java.util.*;
import java.util.logging.Logger;

/**
 * Main container for a room in the grid that makes up the game world
 */
public class Room {
    private static final Logger log = Logger.getLogger(Room.class.getName());
    private final Position position;
    private final RoomSpace roomSpace;
    private Map<Position, List<Mappable>> roomMap;


    /**
     * Constructor
     * @param worldPosition position for the room in world space
     * @param roomSpace dimensions of all rooms
     * @param roomMap room map for all the rooms
     */
    public Room(Position worldPosition, RoomSpace roomSpace, Map<Position, List<Mappable>> roomMap) {
        position = worldPosition;
        this.roomSpace = roomSpace;
        this.roomMap = roomMap;
    }


    /**
     * Retrieves the position for this room in world space.
     *
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


    /**
     * Plays this room.
     * Each play starts with the player if it exist. If player activates a door this method exists.
     * If player doesn't activate a door the the rest of the characters in the room is played.
     *
     * If character doesn't activate a door null is returned.
     *
     * @return if player activates a door the new direction for next room to enter.
     * if no door is activated null is returned.
     */
    public Position.CardinalDirection play() {
        Character character = getPlayer();
        if (character != null) {

            // Character activated a door
            if (character.startTurn(this)) {
                return getCardinalDirection(character.getPosition());
            }
        }

        //Iterate through all positions in room and play all other characters than player.
        for (Map.Entry<Position, List<Mappable>> entrySet : roomMap.entrySet()) {
            List<Mappable> mappables = entrySet.getValue();

            for (Mappable mappable : mappables) {
                if (mappable instanceof Character && !(mappable instanceof Player)) {
                    ((Character) mappable).startTurn(this);
                }
            }
        }

        return null;
    }


    /**
     * Get mappables for the position
     *
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
     *
     * @param cardinalDirection the cardinal direction to add the door to.
     */
    public void addDoor(Position.CardinalDirection cardinalDirection) {
        Position position = getDoorPosition(cardinalDirection);
        List<Mappable> mappables = getFromPosition(position);
        mappables.add(new Door());
    }


    /**
     * Returns the position for a given cardinal direction for a door.
     *
     * @param cardinalDirection the cardinal direction to get a position for
     */
    public Position getDoorPosition(Position.CardinalDirection cardinalDirection) {
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
     *
     * @param cardinalDirection the cardinal direction to check for a door.
     * @return true if there exist a door at the cardinal direction otherwise false.
     */
    public boolean existDoor(Position.CardinalDirection cardinalDirection) {
        Position position = getDoorPosition(cardinalDirection);
        return existType(roomMap.get(position), Door.class);
    }


    /**
     * Checks if there exist a door at the specific position in this room.
     *
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existDoor(Position position) {
        return existType(roomMap.get(position), Door.class);
    }


    /**
     * Check if given exact class type exists in list
     *
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
     *
     * @param position the position for the enemy. If there's already an enemy at the position, it will be overwritten.
     * @param enemy    the enemy to add.
     */
    public void addEnemy(Position position, Enemy enemy) {
        List<Mappable> mappables = getFromPosition(position);
        Iterator mappablesIterator = mappables.iterator();

        enemy.setPosition(position);
        enemy.setRoom(this);

        while (mappablesIterator.hasNext()) {
            Mappable mappable = (Mappable) mappablesIterator.next();
            if (mappable instanceof Enemy) {
                mappablesIterator.remove();
            }
        }
        mappables.add(enemy);
        enemy.setPosition(position);
    }


    /**
     * Gets the enemy at the given position.
     *
     * @param position the position to retrieve the enemy from.
     * @return an enemy object or null if there's no on the position.
     */
    public Character getEnemy(Position position) {
        Character character = getCharacter(position);
        return ((character != null) && (character instanceof Enemy)) ? character : null;
    }

    /**
     * Checks if there exist a item at the specific position in this room.
     *
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existItem(Position position) {
        return existType(roomMap.get(position), Item.class);
    }


    /**
     * Checks if there exist a enemy at the specific position in this room.
     *
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existEnemy(Position position) {
        return existType(roomMap.get(position), Enemy.class);
    }


    /**
     * Verifies if list of mappables contains a object that inherits from character.
     *
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
     *
     * @param position position to add the item to
     * @param item     the item to add
     */
    public void addItem(Position position, Item item) {
        List<Mappable> mappables = getFromPosition(position);
        mappables.add(item);
    }


    /**
     * Get the number of doors in the room.
     *
     * @return number of doors.
     */
    public int getDoorsCount() {
        int doors = 0;

        for (Map.Entry<Position, List<Mappable>> entrySet : roomMap.entrySet()) {
            List<Mappable> mappables = entrySet.getValue();
            for (Mappable mappable : mappables) {
                if (mappable instanceof Door) {
                    doors++;
                }
            }
        }
        return doors;
    }


    /**
     * Set the player object at specified position.
     * @param position for the player
     * @param player   the player object
     * @throws IllegalAccessError if there already exist a character at the position
     */
    public void setPlayer(Position position, Character player) throws IllegalArgumentException {
        if (existCharacter(getFromPosition(position))) {
            throw new IllegalArgumentException("Position already contains a character");
        }

        getFromPosition(position).add(player);
        player.setPosition(position);
    }


    /**
     * Returns the position for the player.
     * @return null if no player is found, otherwise a position for the player
     */
    public Position getPlayerPosition() {
        for (Map.Entry<Position, List<Mappable>> entrySet : roomMap.entrySet()) {
            List<Mappable> mappables = entrySet.getValue();
            for (Mappable mappable : mappables) {
                if (mappable instanceof Player) {
                    return entrySet.getKey();
                }
            }
        }
        return null;
    }


    /**
     * Checks if player exists on position.
     *
     * @param position to check for
     * @return true if there is otherwise false
     */
    public boolean existPlayer(Position position) {
        Position playerPosition = getPlayerPosition();
        return (playerPosition != null) && playerPosition.equals(position);
    }


    /**
     * Get the player
     *
     * @return the player in the room. If there is no player, null is returned.
     */
    public Character getPlayer() {
        return getCharacter(getPlayerPosition());
    }


    /**
     * Move character from one position to an other.
     * If there is no character in from position method returns.
     *
     * @param fromPosition
     * @param toPosition
     * @throws IllegalAccessError if there's already a character in to position or from and to position is same.
     * There can only be one character per position.
     */
    public void moveCharacter(Position fromPosition, Position toPosition) throws IllegalArgumentException {
        if (fromPosition.equals(toPosition)) {
            throw new IllegalArgumentException("From position can't be same as to position.");
        }

        List<Mappable> mappablesFrom = getFromPosition(fromPosition);
        if (!existCharacter(mappablesFrom)) {
            throw new IllegalArgumentException("Character at from position is missing.");
        }

        List<Mappable> mappablesTo = getFromPosition(toPosition);
        if (existCharacter(mappablesTo)) {
            throw new IllegalArgumentException("To position already contains a character.");
        }

        boolean finished = false;

        Iterator fromIterator = mappablesFrom.iterator();
        while (fromIterator.hasNext() && !finished) {
            Mappable mappable = (Mappable) fromIterator.next();
            if (mappable instanceof Character) {
                mappablesTo.add(mappable);
                fromIterator.remove();

                ((Character) mappable).setPosition(toPosition);

                roomMap.put(fromPosition, mappablesFrom);
                roomMap.put(toPosition, mappablesTo);
                finished = true;
            }
        }
    }


    /**
     * Gets the character at the position
     *
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


    /**
     * Translates a position at the borders of a room into the corresponding cardinal direction.
     * @param position the position at the borders of a room.
     * @return a cardinal direction that corresponds to the position
     * @throws IllegalArgumentException of there is no valid translation from position to cardinal direction or if position is null.
     */
    public Position.CardinalDirection getCardinalDirection(Position position) throws IllegalArgumentException {
        if (position == null) {
            throw new IllegalArgumentException("Position can not be null.");
        }

        Position positionNorth = new Position((roomSpace.getWidth() - 1) / 2, 0);
        Position positionSouth = new Position((roomSpace.getWidth() - 1) / 2, roomSpace.getHeight() - 1);
        Position positionWest = new Position(0, (roomSpace.getHeight() - 1) / 2);
        Position positionEast = new Position(roomSpace.getWidth() - 1, (roomSpace.getHeight() - 1) / 2);

        if (position.equals(positionNorth)) {
            return Position.CardinalDirection.North;
        } else if (position.equals(positionSouth)) {
            return Position.CardinalDirection.South;
        } else if (position.equals(positionWest)) {
            return Position.CardinalDirection.West;
        } else if (position.equals(positionEast)) {
            return Position.CardinalDirection.East;
        }

        throw new IllegalArgumentException("Position is not at the borders of the room and can not be translated to a cardinal direction.");
    }
}
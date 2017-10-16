package rougelikeLibrary;

import java.util.HashMap;

/**
 * Main container for a room in the grid that makes up the game world
 */
public class Room {
    private final Position position;

    private java.util.Map<Position.CardinalDirection, Position> cardinalDirectionsDoors = new HashMap<>();
    private java.util.Map<Position, Position.CardinalDirection> positionDoors = new HashMap<>();

    private java.util.Map<Position, Item> items = new HashMap<>();
    private java.util.Map<Position, Character> enemies = new HashMap<>();
    private final RoomSpace roomSpace;
    private Position playerPosition;
    private Character player;


    /**
     * Constructor
     *
     * @param worldPosition position for the room
     * @param roomSpace room space for the room
     */
    public Room(Position worldPosition, RoomSpace roomSpace) {
        position = worldPosition;
        this.roomSpace = roomSpace;
    }


    /**
     * Retrieves the position for this room in world space.
     * @return the WorldPosition for this room.
     */
    public Position getPosition() {
        return position;
    }


    public RoomSpace getRoomSpace() {
        return roomSpace;
    }


    /**
     * Adds an item to the room space
     * @param position the position for the item. If there's already an item at the position, it will be overwritten.
     * @param item the item to add.
     */
    public void addItem(Position position, Item item) {
        items.put(position, item);
    }


    /**
     * Adds an enemy to the room space
     * @param position the position for the enemy. If there's already an enemy at the position, it will be overwritten.
     * @param enemy the item to add.
     */
    public void addEnemy(Position position, Character enemy) {
        enemies.put(position, enemy);
    }


    /**
     * Checks if enemy exists on position.
     * @param position to check for
     * @return true if there is otherwise false
     */
    public boolean existEnemy(Position position) {
        return enemies.get(position) != null;
    }


    /**
     * Checks if item exists on position.
     * @param position to check for
     * @return true if there is otherwise false
     */
    public boolean existItem(Position position) {
        return items.get(position) != null;
    }


    /**
     * Set the player
     * @param position for the player
     * @param player the player
     */
    public void setPlayer(Position position, Character player) {
        playerPosition = position;
        this.player = player;
    }


    /**
     * Checks if player exists on position.
     * @param position to check for
     * @return true if there is otherwise false
     */
    public boolean existPlayer(Position position) {
        return playerPosition == position;
    }


    /**
     * Get the player
     * @return the player in the room. If there is no player, null is returned.
     */
    public Character getPlayer() {
        return player;
    }


    /**
     * Plays this room.
     * @return the new direction for next room to enter.
     */
    public Position.CardinalDirection play() {
        return Position.CardinalDirection.North;
    }


    /**
     * Adds a door to this room in the cardinal direction. If there already exist a door in that direction the old value is overwritten.
     * @param cardinalDirection the cardinal direction to add a door to this room
     */
    public void addDoor(Position.CardinalDirection cardinalDirection) {
        int x = roomSpace.getWidth();
        int y = roomSpace.getHeight();

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

        cardinalDirectionsDoors.put(cardinalDirection, new Position(x, y));
        positionDoors.put(new Position(x, y), cardinalDirection);
    }


    /**
     * Checks if there exist a door in this room at the cardinal direction.
     * @param cardinalDirection the cardinal direction to check for a door.
     * @return true if there exist a door at the cardinal direction otherwise false.
     */
    public boolean existDoor(Position.CardinalDirection cardinalDirection) {
        return cardinalDirectionsDoors.get(cardinalDirection) != null;
    }


    /**
     * Checks if there exist a door at the specific position in this room.
     * @param position the position to check for a door.
     * @return true if there exist a door at position otherwise false.
     */
    public boolean existDoor(Position position) {
        return positionDoors.get(position) != null;
    }


    /**
     * Get the number of doors in the room.
     * @return number of doors.
     */
    public int getDoorsCount() {
        return cardinalDirectionsDoors.size();
    }
}

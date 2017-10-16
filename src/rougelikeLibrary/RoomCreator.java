package rougelikeLibrary;

import java.util.HashMap;
import java.util.Random;

/**
 * Handles logic for creating and populating new rooms.
 * Every room contains x Characters, Items and doors.
 */
public class RoomCreator {
    private final long seed;
    private final Item [] items;
    private final Character [] enemies;
    private final Character player;
    private final RoomSpace roomSpace;
    private final Random random;

    private final int maxRoomItems = 10;
    private final int maxRoomEnemies = 5;

    /**
     * Constructor
     *
     * @param seed random seed for generating content in rooms.
     * @param player the player
     * @param items items that's available for randomization.
     * @param enemies enemies that's available for randomization.
     */
    public RoomCreator(long seed, Character player, Item [] items, Character [] enemies, RoomSpace roomSpace) {
        this.seed = seed;
        this.player = player;
        this.items = items;
        this.enemies = enemies;
        this.roomSpace = roomSpace;
        this.random = new Random(seed);
    }


    /**
     * Creates a new room at the specified coordinate.
     * @param worldPosition coordinate in world space for the room
     * @return Room
     */
    public Room createRoom(Position worldPosition, java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions) {
        Room room = new Room(worldPosition, roomSpace);

        int maxItems = random.nextInt(maxRoomItems + 1);
        int maxEnemies = random.nextInt(maxRoomEnemies + 1);

        for (int i = 0; i < maxItems; i++) {
            room.addItem(getRandomRoomPosition(room.getRoomSpace()), items[random.nextInt(items.length)]);
        }

        for (int e = 0; e < maxEnemies; e++) {
            room.addEnemy(getRandomRoomPosition(room.getRoomSpace()), enemies[random.nextInt(enemies.length)]);
        }

        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.North))) {
            room.addDoor(Position.CardinalDirection.North);
        }
        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.South))) {
            room.addDoor(Position.CardinalDirection.South);
        }
        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.West))) {
            room.addDoor(Position.CardinalDirection.West);
        }
        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.East))) {
            room.addDoor(Position.CardinalDirection.East);
        }

        return room;
    }


    /**
     * Calculates the choice based on the permissions.
     * @param cardinalDirectionPermission the permissions to calculate from.
     * @return true if permission is mandatory or if random returns true on optional permission.
     *      If a permission is missing or is null, false will be returned.
     */
    private boolean getCardinalDirectionPermissionChoice(Position.CardinalDirectionPermission cardinalDirectionPermission) {
        boolean choice = false;

        switch (cardinalDirectionPermission) {
            case Disallowed:
                choice = false;
                break;

            case Optional:
                choice = random.nextBoolean();
                break;

            case Mandatory:
                choice = true;
                break;
        }
        return choice;
    }


    /**
     * Creates the initial room. All cardinal directions for doors is optional with a minimum of one door.
     * @param position coordinate in world space for the room
     */
    public Room createInitialRoom(Position position) {
        java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionsAll = new HashMap<>();
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Mandatory);

        Room room = createRoom(position, cardinalDirectionPermissionsAll);

        room.setPlayer(getRandomRoomPosition(room.getRoomSpace()), player);

        // Need minimum one doors at the beginning
        if (room.getDoorsCount() < 1) {
            room.addDoor(Position.CardinalDirection.North);
        }

        return room;
    }


    /**
     * Get a random position in roomSpace
     * @param roomSpace
     * @return random position
     */
    private Position getRandomRoomPosition(RoomSpace roomSpace) {
        int x = random.nextInt(roomSpace.getWidth() + 1);
        int y = random.nextInt(roomSpace.getHeight() + 1);
        return new Position(x, y);
    }
}

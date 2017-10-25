package rougelikeLibrary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Handles logic for creating and populating new rooms.
 * Every room contains x Characters, Items and doors.
 */
public class RoomCreator {
    private static final Logger log = Logger.getLogger(Room.class.getName());
    private final long seed;
    private final Character player;
    private final RoomSpace roomSpace;
    private final Random random;

    private List<MappableTypeWrapper> mappableTypes;


    /**
     * Constructor
     *
     * @param seed random seed for generating content in rooms.
     * @param player the player
     * @param mappableTypes List of types that are mappable
     * @param roomSpace the room space for all created rooms
     */
    public RoomCreator(long seed, Character player, List<MappableTypeWrapper> mappableTypes, RoomSpace roomSpace) {
        this.seed = seed;
        this.player = player;
        this.mappableTypes = mappableTypes;
        this.roomSpace = roomSpace;
        this.random = new Random(seed);
    }


    /**
     * Creates the initial room. All cardinal directions for doors is optional with a minimum of one door.
     * @param position coordinate in world space for the room
     */
    public Room createInitialRoom(Position position, Map<Position.CardinalDirection, Position.CardinalDirectionPermission> doorCardinalDirectionPermissions) {
        Room room = createRoom(position, doorCardinalDirectionPermissions);
        Map<Position, List<Mappable>> roomMap = room.getRoomMap();
        Position playerPosition = getRandomRoomPosition();

        // Player can't be placed on enemies at start
        roomMap.remove(playerPosition);
        room.setPlayer(playerPosition, player);
        player.setPosition(playerPosition);

        addToRoom(roomMap, playerPosition, player);

        // Need minimum one doors at the beginning
        if (room.getDoorsCount() < 1) {
            addToRoom(roomMap, room.getDoorPosition(Position.CardinalDirection.North), new Door());
        }

        return room;
    }


    /**
     * Creates a new room at the specified coordinate.
     * @param position coordinate in world space for the room
     * @return Room
     * @throws IllegalArgumentException if min or max quantity of given type is not completed.
     */
    public Room createRoom(Position position, java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions) throws IllegalArgumentException {
        Map<Position, List<Mappable>> roomMap = new HashMap<>();

        Room room = new Room(position, roomSpace, roomMap);

        for(MappableTypeWrapper mappableTypeWrapper : mappableTypes) {
            addTypeToRoom(mappableTypeWrapper, roomMap);
        }

        addDoors(room, cardinalDirectionPermissions);

        return room;
    }


    // @TODO private
    /**
     * Adds type to a position in room based on probability, minimum quantity and not exceeding maximum quantity
     * @param mappableTypeWrapper information needed to create a type
     * @param roomMap the room
     */
    public void addTypeToRoom(MappableTypeWrapper mappableTypeWrapper, Map<Position, List<Mappable>> roomMap) {
        // Loop until given min and max quantity of type is completed.
        while (true) {
            // Iterate all positions in a room
            for (int x = 0; x < roomSpace.getWidth(); x++) {
                for (int y = 0; y < roomSpace.getHeight(); y++) {
                    // Get probability boolean to create a type in the given position
                    if (getProbabilityBoolean(mappableTypeWrapper.probability)) {
                        addToRoom(roomMap, new Position(x, y), createType(mappableTypeWrapper));
                        mappableTypeWrapper.addQuantity();

                        if (mappableTypeWrapper.isMaxQuantity()) {
                            return;
                        }
                    }
                }
            }
            if (mappableTypeWrapper.isMinQuantity()) {
                return;
            }
        }
    }


    /**
     * Creates a mappable type
     * @param mappableTypeWrapper the wrapper that holds information necessary to create a mappable type
     * @return a mappable type
     * @throws IllegalArgumentException if arguments is illegal
     */
    public Mappable createType(MappableTypeWrapper mappableTypeWrapper) throws IllegalArgumentException {
        try {
            Constructor<?> constructor = mappableTypeWrapper.classType.getConstructor(mappableTypeWrapper.parameterTypes);
            return (Mappable) constructor.newInstance(mappableTypeWrapper.parameterValues);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException ex) {
            throw new IllegalArgumentException("Item type, parameter types or parameter values is illegal. : " + ex);
        }
    }


    /**
     * Adds a mappable type to a position in room map
     * @param roomMap the map that contains all lists of mappables per position
     * @param position the position in map
     * @param mappableType the mappable type to add to the position
     */
    public void addToRoom(Map<Position, List<Mappable>> roomMap, Position position, Mappable mappableType) {
        List<Mappable> mappableList = roomMap.get(position);

        if (mappableList == null) {
            mappableList = new ArrayList<>();
            roomMap.put(position, mappableList);
        }
        mappableList.add(mappableType);

        if (mappableType instanceof Character) {
            ((Character) mappableType).setPosition(position);
        }
    }


    /**
     * Adds a door in a specific cardinal direction based on cardinal direction permissions.
     * The permissions dictate if it is mandatory, optional or disallowed.
     * If it's optional it's probability based if the door is created.
     * @param room the room to add doors to
     * @param cardinalDirectionPermissions the permissions that dictates optional (i.e. probability based), mandatory or disallowed.
     */
    public void addDoors(Room room, java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions) {
        Map<Position, List<Mappable>> roomMap = room.getRoomMap();

        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.North))) {
           addToRoom(roomMap, room.getDoorPosition(Position.CardinalDirection.North), new Door());
        }
        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.South))) {
            addToRoom(roomMap, room.getDoorPosition(Position.CardinalDirection.South), new Door());
        }
        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.West))) {
            addToRoom(roomMap, room.getDoorPosition(Position.CardinalDirection.West), new Door());
        }
        if (getCardinalDirectionPermissionChoice(cardinalDirectionPermissions.get(Position.CardinalDirection.East))) {
            addToRoom(roomMap, room.getDoorPosition(Position.CardinalDirection.East), new Door());
        }
    }


    /**
     * Calculates the choice based on the permissions. Optional is roughly 50% probability for either true/false.
     * @param cardinalDirectionPermission the permissions to calculate from.
     * @return true if permission is mandatory or if random returns true on optional permission.
     * If a permission is missing or is null, false will be returned.
     */
    public boolean getCardinalDirectionPermissionChoice(Position.CardinalDirectionPermission cardinalDirectionPermission) {
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
     * Returns a boolean value based on the probability given as argument to get a random number in the span.
     * @param percentProbability the probability in percent. 0 < probability <= 100.
     * @return a boolean value based on the probability
     * @throws IllegalArgumentException if probability is not 0 < probability <= 100.
     */
    public boolean getProbabilityBoolean(int percentProbability) throws IllegalArgumentException {
        if (percentProbability < 1 || percentProbability > 100) {
            throw new IllegalArgumentException("Probability must be between 0 and 100 exclusive.");
        }
        return random.nextDouble() <= (percentProbability / 100d);
    }


    /**
     * Get a random position in roomSpace
     * @return random position
     */
    public Position getRandomRoomPosition() {
        int x = random.nextInt(roomSpace.getWidth());
        int y = random.nextInt(roomSpace.getHeight());
        return new Position(x, y);
    }


    /**
     * Gets the position in the room for a given cardinal direction
     * @param room the room for which the position is related to
     * @param cardinalDirection the direction in room to get a position for
     * @return the position in room that corresponds to the cardinal direction
     */
    public Position getPositionCardinalDirection(Room room, Position.CardinalDirection cardinalDirection) throws IllegalArgumentException {
        if (cardinalDirection == null) {
            throw new IllegalArgumentException("Cardinal direction can not be null.");
        }

        Position north = new Position((room.getRoomSpace().getMaxIndexWidth()) / 2, 0);
        Position south = new Position((room.getRoomSpace().getMaxIndexWidth()) / 2, room.getRoomSpace().getMaxIndexHeight());
        Position west = new Position(0, (room.getRoomSpace().getMaxIndexHeight()) / 2);
        Position east = new Position(room.getRoomSpace().getMaxIndexWidth(), (room.getRoomSpace().getMaxIndexWidth() / 2));

        Position cardinalDirectionPosition = null;
        switch (cardinalDirection) {
            case North:
                cardinalDirectionPosition = north;
                break;
            case South:
                cardinalDirectionPosition = south;
                break;
            case West:
                cardinalDirectionPosition = west;
                break;
            case East:
                cardinalDirectionPosition = east;
                break;
        }
        return cardinalDirectionPosition;
    }
}

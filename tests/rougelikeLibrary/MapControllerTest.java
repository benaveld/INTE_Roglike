package rougelikeLibrary;

import static org.junit.Assert.*;

import javafx.geometry.Pos;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class MapControllerTest {
    private final int dummySeed = 1337;
    private final int dummyCenterX = 12321;
    private final int dummyCenterY = 6423;
    private final Position centerWorldPosition = new Position(dummyCenterX, dummyCenterY);

    private RoomCreator roomCreator;
    private Room centerRoom;
    private MapController mapController;
    private java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionsAll = new HashMap<>();
    private java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions = new HashMap<>();
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionsAllOptional = new HashMap<>();

    Item [] items = {
            new Item("fast shoes", 12, Item.Effect.SPEED),
            new Item("deadly axe", 9, Item.Effect.DAMAGE),
            new Item("better shoe laces", 33, Item.Effect.SPEED),
            new Item("candy", 77, Item.Effect.HEALTH)
    };

    Character [] enemies = {
            new Enemy(22, 33, 44, new TurnSystem(new EnemyAI(1))),
            new Enemy(2, 3, 44, new TurnSystem(new EnemyAI(1))),
            new Enemy(22, 33, 4, new TurnSystem(new EnemyAI(1))),
            new Enemy(2, 3, 44, new TurnSystem(new EnemyAI(1)))
    };



    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Before
    public void init() {
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAll.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        roomCreator = new RoomCreator(dummySeed,
                new Player(1, 1, 1, new TurnSystem(new EnemyAI(1))),
                new ArrayList<MappableTypeWrapper>(),
                new RoomSpace(32, 32));

        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        centerRoom = roomCreator.createInitialRoom(centerWorldPosition, cardinalDirectionPermissionsAllOptional);
        mapController = new MapController(centerRoom);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor() {
        new MapController(null);
    }

    @Test
    public void playCurrentRoom() throws Exception {
        mapController.playCurrentRoom();
    }

    @Test
    public void setCurrentRoom() throws Exception {
        Room room = new Room(new Position(34, 34), new RoomSpace(12, 12), new HashMap<Position, List<Mappable>>());
        mapController.setCurrentRoom(room);
        assertEquals(mapController.getCurrentRoom(), room);

        try {
            mapController.setCurrentRoom(null);
            fail("Expected IllegalArgumentException: current room is null.");
        } catch (IllegalArgumentException iae) {assertTrue(true); }
    }

    @Test
    public void getCurrentRoom() throws Exception {
        setCurrentRoom();
    }

    @Test
    public void addRoom() throws Exception {
        Position worldPosition = new Position(32, 54);
        Map<Position, List<Mappable>> roomMap = new HashMap<>();
        Room room = new Room(worldPosition, new RoomSpace(32, 32), roomMap);
        mapController.addRoom(room);
        assertEquals(mapController.getRoom(worldPosition), room);

        exception.expect(IllegalArgumentException.class);
        mapController.addRoom(null);
    }

    @Test
    public void roomExist() throws Exception {
        Position worldPosition = new Position(32, 54);
        Map<Position, List<Mappable>> roomMap = new HashMap<>();
        Room room = new Room(worldPosition, new RoomSpace(32, 32), roomMap);
        mapController.addRoom(room);
        assertTrue(mapController.roomExist(worldPosition));
    }


    @Test(expected = IllegalArgumentException.class)
    public void getRoom() throws Exception {
        mapController.getRoom(new Position(0, 0));
    }

    @Test
    public void getCardinalDirectionPermissions() throws Exception {
        Room cRoom = new Room(centerWorldPosition, new RoomSpace(32, 32), new HashMap<Position, List<Mappable>>());
        cRoom.addDoor(Position.CardinalDirection.West);
        MapController mapController = new MapController(cRoom);
        Position newPosition = new Position(centerWorldPosition.getX() - 1, centerWorldPosition.getY());

        Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions = mapController.getCardinalDirectionPermissions(newPosition);
        assertEquals(cardinalDirectionPermissions.get(Position.CardinalDirection.East), Position.CardinalDirectionPermission.Mandatory);


        exception.expect(IllegalArgumentException.class);
        mapController.getCardinalDirectionPermissions(centerWorldPosition);
    }

    @Test
    public void testPlayRoom() {
        Position nextRoomPosition = mapController.playCurrentRoom();
        assertNull(nextRoomPosition);
    }


    @Test
    public void testRoomExist() {
        assertTrue(mapController.roomExist(centerWorldPosition));
    }


    @Test
    public void testSetCurrentRoom() {
        Position beforePosition = new Position(12, 13);
        Room room1 = roomCreator.createRoom(beforePosition, cardinalDirectionPermissionsAll);
        MapController mapController = new MapController(room1);

        assertEquals(mapController.getCurrentRoom().getPosition(), beforePosition);

        Position afterPosition = new Position(22, 23);
        Room room2 = roomCreator.createRoom(afterPosition, cardinalDirectionPermissionsAll);
        mapController.setCurrentRoom(room2);

        assertEquals(mapController.getCurrentRoom().getPosition(), afterPosition);
    }

    @Test
    public void testGetCurrentRoom() {
        Position initialPosition = new Position(32, 54);
        Room room = roomCreator.createRoom(initialPosition, cardinalDirectionPermissionsAll);
        MapController mapController = new MapController(room);
        assertEquals(mapController.getCurrentRoom().getPosition(), initialPosition);
    }


    @Test
    public void testAddRoom() {
        Position worldPosition1 = new Position(2, 3);
        Position worldPosition2 = new Position(12, 13);
        Position worldPosition3 = new Position(22, 23);

        assertFalse(mapController.roomExist(worldPosition1));
        assertFalse(mapController.roomExist(worldPosition2));
        assertFalse(mapController.roomExist(worldPosition3));

        mapController.addRoom(roomCreator.createRoom(worldPosition1, cardinalDirectionPermissionsAll));
        mapController.addRoom(roomCreator.createRoom(worldPosition2, cardinalDirectionPermissionsAll));
        mapController.addRoom(roomCreator.createRoom(worldPosition3, cardinalDirectionPermissionsAll));

        assertTrue(mapController.roomExist(worldPosition1));
        assertTrue(mapController.roomExist(worldPosition2));
        assertTrue(mapController.roomExist(worldPosition3));
    }


    @Test
    public void testGetRoom() {
        Position worldPosition1 = new Position(2, 3);
        Position worldPosition2 = new Position(12, 13);
        Position worldPosition3 = new Position(22, 23);

        assertFalse(mapController.roomExist(worldPosition1));
        assertFalse(mapController.roomExist(worldPosition2));
        assertFalse(mapController.roomExist(worldPosition3));

        mapController.addRoom(roomCreator.createRoom(worldPosition1, cardinalDirectionPermissionsAll));
        mapController.addRoom(roomCreator.createRoom(worldPosition2, cardinalDirectionPermissionsAll));
        mapController.addRoom(roomCreator.createRoom(worldPosition3, cardinalDirectionPermissionsAll));

        assertEquals(mapController.getRoom(worldPosition1).getPosition(), worldPosition1);
        assertEquals(mapController.getRoom(worldPosition2).getPosition(), worldPosition2);
        assertEquals(mapController.getRoom(worldPosition3).getPosition(), worldPosition3);
    }

    /**
     * Tests a possible position for a room which direction it can have doors to.
     * Example:
     *      Room position to test for is [0, 4]
     *
     *      In this case there is a room at [0, 3] (to the north of the room position to test.)
     *          It has a door to the south.
     *
     *      Then there is a room at [1, 4] (to the east of the room position to test).
     *          It has NO door to the west.
     *
     *      That means it's MANDATORY for the room position to test to have a door to the North.
     *      It's DISALLOWED for the room position to test for to have a door to the West because it's out of bounds.
     *      It's DISALLOWED for the room position to test for to have a door to the East,
     *          because the adjacent room doesn't have a door to the West.
     *
     *      Because there is no room in the South, door to the South is OPTIONAL.
     */
    @Test
    public void testCardinalDirectionPermissionDoors() {
        Room northRoom = roomCreator.createRoom(new Position(0, 3), cardinalDirectionPermissionsAll);
        northRoom.addDoor(Position.CardinalDirection.South);

        Room eastRoom = roomCreator.createRoom(new Position(1, 4), cardinalDirectionPermissionsAll);

        mapController.addRoom(northRoom);
        mapController.addRoom(eastRoom);

        java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirections =
                mapController.getCardinalDirectionPermissions(new Position(0, 4));

        assertEquals(cardinalDirections.get(Position.CardinalDirection.North), Position.CardinalDirectionPermission.Mandatory);
        assertEquals(cardinalDirections.get(Position.CardinalDirection.South), Position.CardinalDirectionPermission.Optional);
        assertEquals(cardinalDirections.get(Position.CardinalDirection.West), Position.CardinalDirectionPermission.Disallowed);
        assertEquals(cardinalDirections.get(Position.CardinalDirection.East), Position.CardinalDirectionPermission.Disallowed);
    }


    @Test
    public void getOppositeCardinalDirection() {
        assertEquals(mapController.getOppositeCardinalDirection(Position.CardinalDirection.North), Position.CardinalDirection.South);
        assertEquals(mapController.getOppositeCardinalDirection(Position.CardinalDirection.South), Position.CardinalDirection.North);
        assertEquals(mapController.getOppositeCardinalDirection(Position.CardinalDirection.West), Position.CardinalDirection.East);
        assertEquals(mapController.getOppositeCardinalDirection(Position.CardinalDirection.East), Position.CardinalDirection.West);
    }

}

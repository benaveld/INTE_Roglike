package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

/**
 */
public class MapControllerTests {
    private final int dummySeed = 1337;
    private final int dummyCenterX = 12321;
    private final int dummyCenterY = 6423;
    private final WorldPosition centerWorldPosition = new WorldPosition(dummyCenterX, dummyCenterY);

    private RoomCreator roomCreator;
    private Room centerRoom;
    private MapController mapController;

    @Before
    public void init() {
        roomCreator = new RoomCreator(dummySeed);
        centerRoom = roomCreator.createRoom(centerWorldPosition);
        mapController = new MapController(centerRoom);
    }

    @Test
    public void testPlayRoom() {
        WorldPosition nextRoomPosition = mapController.playCurrentRoom();
        assertNotNull(nextRoomPosition);
    }

    @Test
    public void testRoomExist() {
        assertTrue(mapController.roomExist(centerWorldPosition));
    }


    @Test
    public void testAddRoom() {
        WorldPosition worldPosition1 = new WorldPosition(2, 3);
        WorldPosition worldPosition2 = new WorldPosition(12, 13);
        WorldPosition worldPosition3 = new WorldPosition(22, 23);

        assertFalse(mapController.roomExist(worldPosition1));
        assertFalse(mapController.roomExist(worldPosition2));
        assertFalse(mapController.roomExist(worldPosition3));

        mapController.addRoom(new Room(worldPosition1));
        mapController.addRoom(new Room(worldPosition2));
        mapController.addRoom(new Room(worldPosition3));

        assertTrue(mapController.roomExist(worldPosition1));
        assertTrue(mapController.roomExist(worldPosition2));
        assertTrue(mapController.roomExist(worldPosition3));
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
        Room northRoom = new Room(new WorldPosition(0, 3));
        northRoom.addDoor(WorldPosition.CardinalDirection.South);

        Room eastRoom = new Room(new WorldPosition(1, 4));

        mapController.addRoom(northRoom);
        mapController.addRoom(eastRoom);

        java.util.Map<WorldPosition.CardinalDirection, WorldPosition.CardinalDirectionPermission> cardinalDirections =
                mapController.getCardinalDirectionPermissions(new WorldPosition(0, 4));

        assertEquals(cardinalDirections.get(WorldPosition.CardinalDirection.North), WorldPosition.CardinalDirectionPermission.Mandatory);
        assertEquals(cardinalDirections.get(WorldPosition.CardinalDirection.South), WorldPosition.CardinalDirectionPermission.Optional);
        assertEquals(cardinalDirections.get(WorldPosition.CardinalDirection.West), WorldPosition.CardinalDirectionPermission.Disallowed);
        assertEquals(cardinalDirections.get(WorldPosition.CardinalDirection.East), WorldPosition.CardinalDirectionPermission.Disallowed);
    }
}

package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.HashMap;

public class RoomCreatorTests {
    private final int arbitraryX = 1337;
    private final int arbitraryY = 97;
    private final int roomCreatorSeed = 37135;
    private RoomCreator roomCreator;

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

    @Before
    public void init() {
        roomCreator = new RoomCreator(roomCreatorSeed, new Player(1, 1, 1, new TurnSystem(new EnemyAI(1))), items, enemies, new RoomSpace(32, 32));
    }


    @Test
    public void testCreateRoom() {
        java.util.Map<WorldPosition.CardinalDirection, WorldPosition.CardinalDirectionPermission> cardinalDirectionPermissions = new HashMap<>();
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.North, WorldPosition.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.South, WorldPosition.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.West, WorldPosition.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.East, WorldPosition.CardinalDirectionPermission.Disallowed);

        Room room = roomCreator.createRoom(new WorldPosition(arbitraryX, arbitraryY), cardinalDirectionPermissions);
        assertNotNull(room);
        assertFalse(room.existDoor(WorldPosition.CardinalDirection.South));
        assertTrue(room.existDoor(WorldPosition.CardinalDirection.West));
        assertFalse(room.existDoor(WorldPosition.CardinalDirection.East));

        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.North, WorldPosition.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.South, WorldPosition.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.West, WorldPosition.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.East, WorldPosition.CardinalDirectionPermission.Mandatory);

        room = roomCreator.createRoom(new WorldPosition(arbitraryX, arbitraryY), cardinalDirectionPermissions);
        assertNotNull(room);
        assertFalse(room.existDoor(WorldPosition.CardinalDirection.North));
        assertTrue(room.existDoor(WorldPosition.CardinalDirection.South));
        assertFalse(room.existDoor(WorldPosition.CardinalDirection.West));
        assertTrue(room.existDoor(WorldPosition.CardinalDirection.East));
    }


    @Test
    public void testCreateInitialRoom() {
        Room room = roomCreator.createInitialRoom(new WorldPosition(arbitraryX, arbitraryY));
        assertNotNull(room);

        // Minimum 1 door needs to exist in first room
        assertTrue(room.existDoor(WorldPosition.CardinalDirection.North) ||
            room.existDoor(WorldPosition.CardinalDirection.South) ||
            room.existDoor(WorldPosition.CardinalDirection.West) ||
            room.existDoor(WorldPosition.CardinalDirection.East));

    }
}

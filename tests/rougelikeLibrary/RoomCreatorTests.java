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
        java.util.Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions = new HashMap<>();
        cardinalDirectionPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Disallowed);

        Room room = roomCreator.createRoom(new Position(arbitraryX, arbitraryY), cardinalDirectionPermissions);
        assertNotNull(room);
        assertFalse(room.existDoor(Position.CardinalDirection.South));
        assertTrue(room.existDoor(Position.CardinalDirection.West));
        assertFalse(room.existDoor(Position.CardinalDirection.East));

        cardinalDirectionPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Mandatory);

        room = roomCreator.createRoom(new Position(arbitraryX, arbitraryY), cardinalDirectionPermissions);
        assertNotNull(room);
        assertFalse(room.existDoor(Position.CardinalDirection.North));
        assertTrue(room.existDoor(Position.CardinalDirection.South));
        assertFalse(room.existDoor(Position.CardinalDirection.West));
        assertTrue(room.existDoor(Position.CardinalDirection.East));
    }


    @Test
    public void testCreateInitialRoom() {
        Room room = roomCreator.createInitialRoom(new Position(arbitraryX, arbitraryY));
        assertNotNull(room);

        // Minimum 1 door needs to exist in first room
        assertTrue(room.existDoor(Position.CardinalDirection.North) ||
            room.existDoor(Position.CardinalDirection.South) ||
            room.existDoor(Position.CardinalDirection.West) ||
            room.existDoor(Position.CardinalDirection.East));

    }
}

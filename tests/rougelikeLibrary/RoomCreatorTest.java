package rougelikeLibrary;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomCreatorTest {
    private final int arbitraryX = 1337;
    private final int arbitraryY = 97;
    private final long roomCreatorSeed = 3735;
    private RoomCreator roomCreator;
    private Position stdPosition;
    private Position stdWorldPosition;
    private final int dummyX = 523223;
    private final int dummyY = 219219129;
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> stdCardinalPermissions = new HashMap<>();
    private Map<Position, List<Mappable>> roomMap;

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
        roomCreator = new RoomCreator(roomCreatorSeed,
                new Player(1, 1, 1, new TurnSystem(new EnemyAI(1))),
                new ArrayList<MappableTypeWrapper>(),
                new RoomSpace(32, 32));
                stdPosition = new Position(dummyX, dummyY);
                stdWorldPosition = new Position(43, 12);
        roomMap = new HashMap<>();
        stdCardinalPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Mandatory);
        stdCardinalPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Disallowed);
        stdCardinalPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        stdCardinalPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);
    }



    @Test
    public void addTypeToRoom() throws Exception {
        Class [] parameterTypesItem = {String.class, int.class, Item.Effect.class};
        Object [] parameterValuesItem = {"item 1", 43, Item.Effect.DAMAGE};

        Class [] parameterTypesEnemy = {int.class, int.class, int.class, TurnSystem.class};
        Object [] parameterValuesEnemy = {34, 87, 22, new TurnSystem(new EnemyAI(43))};

        int minQntyItem = 1;
        int maxQntyItem = 5;
        int minQntyEnemy = 17;
        int maxQntyEnemy = 28;
        MappableTypeWrapper itemType = new MappableTypeWrapper(Item.class, parameterTypesItem, parameterValuesItem, minQntyItem, maxQntyItem, 1);
        MappableTypeWrapper enemyType = new MappableTypeWrapper(Enemy.class, parameterTypesEnemy, parameterValuesEnemy, new EnemyAI(2143), minQntyEnemy, maxQntyEnemy, 30);

        Room room = roomCreator.createRoom(stdWorldPosition, stdCardinalPermissions);
        Map<Position, List<Mappable>> roomMapLocal = room.getRoomMap();
        roomCreator.addTypeToRoom(itemType, roomMapLocal);
        roomCreator.addTypeToRoom(enemyType, roomMapLocal);

        int createdItem = 0;
        int createdEnemy = 0;

        assertEquals(roomMapLocal, room.getRoomMap());

        for(Map.Entry<Position, List<Mappable>> entrySet : roomMapLocal.entrySet()) {
            if (room.existType(entrySet.getValue(), Item.class)) {
                createdItem++;
            }
            if (room.existType(entrySet.getValue(), Enemy.class)) {
                createdEnemy++;
            }
        }
        assertTrue(createdItem >= minQntyItem && createdItem <= maxQntyItem);
        assertTrue(createdEnemy >= minQntyEnemy && createdEnemy <= maxQntyEnemy);
    }


    @Test
    public void createType() throws Exception {
        Class [] parameterTypesEnemy = {int.class, int.class, int.class, TurnSystem.class};
        Object [] parameterValuesEnemy = {34, 87, 22, new TurnSystem(new EnemyAI(43))};
        MappableTypeWrapper enemyType = new MappableTypeWrapper(Enemy.class, parameterTypesEnemy, parameterValuesEnemy,
                new EnemyAI(2143), 1, 2, 30);

        Mappable mappableEnemy = roomCreator.createType(enemyType);
        assertTrue(mappableEnemy instanceof Enemy);
        Enemy enemy = (Enemy) mappableEnemy;
        assertEquals(enemy.getSpeed(), 34);
        assertEquals(enemy.getHealth(), 87);
        assertEquals(enemy.getDamage(), 22);
    }


    @Test
    public void addToRoom() throws Exception {
        Mappable mappable = new Enemy(1, 2, 3, new TurnSystem(new EnemyAI(53)));
        Room room = new Room(stdWorldPosition, new RoomSpace(32, 32), roomMap);
        assertTrue(room.getRoomMap().isEmpty());

        roomCreator.addToRoom(roomMap, stdPosition, mappable);
        List<Mappable> mappables = room.getFromPosition(stdPosition);
        room.existType(mappables, Enemy.class);
    }


    @Test
    public void addDoors() throws Exception {
        stdCardinalPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Mandatory);
        stdCardinalPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Mandatory);
        stdCardinalPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Mandatory);
        stdCardinalPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Mandatory);

        // 4 doors
        roomMap = new HashMap<>();
        Room room = new Room(stdWorldPosition, new RoomSpace(32, 32), roomMap);
        assertEquals(room.getDoorsCount(), 0);
        roomCreator.addDoors(room, stdCardinalPermissions);
        assertEquals(room.getDoorsCount(), 4);

        // 0 doors
        roomMap = new HashMap<>();
        room = new Room(stdWorldPosition, new RoomSpace(32, 32), roomMap);
        assertEquals(room.getDoorsCount(), 0);
        stdCardinalPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Disallowed);
        stdCardinalPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Disallowed);
        stdCardinalPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Disallowed);
        stdCardinalPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Disallowed);
        roomCreator.addDoors(room, stdCardinalPermissions);
        assertEquals(room.getDoorsCount(), 0);

        // Probability based
        stdCardinalPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Optional);
        stdCardinalPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Optional);
        stdCardinalPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        stdCardinalPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        for (int i = 0; i < 1000; i++) {
            if (room.getDoorsCount() >= 2) {
                break;
            }
            roomMap = new HashMap<>();
            room = new Room(stdWorldPosition, new RoomSpace(32, 32), roomMap);
            roomCreator.addDoors(room, stdCardinalPermissions);
        }
        assertTrue(room.getDoorsCount() >= 2);

    }


    @Test
    public void getCardinalDirectionPermissionChoice() throws Exception {
        assertTrue(roomCreator.getCardinalDirectionPermissionChoice(Position.CardinalDirectionPermission.Mandatory));
        assertFalse(roomCreator.getCardinalDirectionPermissionChoice(Position.CardinalDirectionPermission.Disallowed));
    }


    @Test
    public void getProbabilityBoolean() throws Exception {
        int twentyPercent = 20;
        int generatedTrue = 0;
        for (int i = 0; i < 1000000; i++) {
            if (roomCreator.getProbabilityBoolean(twentyPercent)) {
                generatedTrue++;
            }
        }
        assertTrue(generatedTrue >= 200000);
    }


    @Test
    public void getRandomRoomPosition() throws Exception {
        assertNotNull(roomCreator.getRandomRoomPosition());
    }



    @Test
    public void createInitialRoom() throws Exception {
        Room room = roomCreator.createInitialRoom(new Position(arbitraryX, arbitraryY));
        assertNotNull(room);

        // Minimum 1 door needs to exist in first room
        assertTrue(room.existDoor(Position.CardinalDirection.North) ||
                room.existDoor(Position.CardinalDirection.South) ||
                room.existDoor(Position.CardinalDirection.West) ||
                room.existDoor(Position.CardinalDirection.East));

    }

    @Test
    public void createRoom() throws Exception {
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
}

package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
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
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionsAllOptional = new HashMap<>();
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionAllDisallowed = new HashMap<>();
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionAllMandatory = new HashMap<>();


    Item [] items = {
            new Item("fast shoes", 12, Item.Effect.SPEED),
            new Item("deadly axe", 9, Item.Effect.DAMAGE),
            new Item("better shoe laces", 33, Item.Effect.SPEED),
            new Item("candy", 77, Item.Effect.HEALTH)
    };

    Character [] enemies = {
            new Enemy(22, 33, 44, new Position(0,0), new TurnSystem(new EnemyAI(1))),
            new Enemy(2, 3, 44, new Position(0,0), new TurnSystem(new EnemyAI(1))),
            new Enemy(22, 33, 4, new Position(0,0), new TurnSystem(new EnemyAI(1))),
            new Enemy(2, 3, 44, new Position(0,0), new TurnSystem(new EnemyAI(1)))
    };


    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Before
    public void init() {
        roomCreator = new RoomCreator(roomCreatorSeed,
                new Player(1, 1, 1, new Position(0,0), new TurnSystem(new EnemyAI(1))),
                new ArrayList<MappableTypeWrapper>(),
                new RoomSpace(32, 32));
                stdPosition = new Position(dummyX, dummyY);
                stdWorldPosition = new Position(43, 12);
        roomMap = new HashMap<>();
        stdCardinalPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Mandatory);
        stdCardinalPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Disallowed);
        stdCardinalPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        stdCardinalPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        cardinalDirectionPermissionAllDisallowed.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissionAllDisallowed.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissionAllDisallowed.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Disallowed);
        cardinalDirectionPermissionAllDisallowed.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Disallowed);

        cardinalDirectionPermissionAllMandatory.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissionAllMandatory.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissionAllMandatory.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Mandatory);
        cardinalDirectionPermissionAllMandatory.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Mandatory);
    }


    @Test
    public void addTypeToRoom() throws Exception {
        Class [] parameterTypesItem = {String.class, int.class, Item.Effect.class};
        Object [] parameterValuesItem = {"item 1", 43, Item.Effect.DAMAGE};

        Class [] parameterTypesEnemy = {int.class, int.class, int.class, Position.class, TurnSystem.class};
        Object [] parameterValuesEnemy = {34, 87, 22, stdPosition, new TurnSystem(new EnemyAI(43))};

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
        Class [] parameterTypesEnemyInvalid = {String.class, int.class, int.class, Position.class, TurnSystem.class};
        Class [] parameterTypesEnemy = {int.class, int.class, int.class, Position.class, TurnSystem.class};
        Object [] parameterValuesEnemy = {34, 87, 22, stdPosition, new TurnSystem(new EnemyAI(43))};

        MappableTypeWrapper enemyType = new MappableTypeWrapper(Enemy.class, parameterTypesEnemy, parameterValuesEnemy,
                new EnemyAI(2143), 1, 2, 30);

        Mappable mappableEnemy = roomCreator.createType(enemyType);
        assertTrue(mappableEnemy instanceof Enemy);
        Enemy enemy = (Enemy) mappableEnemy;
        assertEquals(enemy.getSpeed(), 34);
        assertEquals(enemy.getHealth(), 87);
        assertEquals(enemy.getDamage(), 22);

        exception.expect(IllegalArgumentException.class);
        roomCreator.createType(new MappableTypeWrapper(Enemy.class, parameterTypesEnemyInvalid, parameterValuesEnemy,
                new EnemyAI(2143), 1, 2, 30));
    }


    @Test
    public void addToRoom() throws Exception {
        Mappable mappable = new Enemy(1, 2, 3, new Position(0,0), new TurnSystem(new EnemyAI(53)));
        Room room = new Room(stdWorldPosition, new RoomSpace(32, 32), roomMap);
        assertTrue(room.getRoomMap().isEmpty());

        assertEquals(((Character) mappable).getPosition(), new Position(0, 0));
        roomCreator.addToRoom(roomMap, stdPosition, mappable);
        assertEquals(room.getCharacter(stdPosition).getPosition(), stdPosition);

        List<Mappable> mappables = room.getFromPosition(stdPosition);
        room.existType(mappables, Enemy.class);
    }


    @Test
    public void addDoors() throws Exception {
        // 4 doors
        Room room = new Room(stdWorldPosition, new RoomSpace(32, 32), new HashMap<>());
        assertEquals(room.getDoorsCount(), 0);
        roomCreator.addDoors(room, cardinalDirectionPermissionAllMandatory);
        assertEquals(room.getDoorsCount(), 4);

        // 0 doors
        room = new Room(stdWorldPosition, new RoomSpace(32, 32), new HashMap<>());

        assertEquals(room.getDoorsCount(), 0);

        roomCreator.addDoors(room, cardinalDirectionPermissionAllDisallowed);
        assertEquals(room.getDoorsCount(), 0);

        for (int i = 0; i < 1000; i++) {
            if (room.getDoorsCount() >= 2) {
                break;
            }
            room = new Room(stdWorldPosition, new RoomSpace(32, 32), new HashMap<>());
            roomCreator.addDoors(room, cardinalDirectionPermissionsAllOptional);
        }
        assertTrue(room.getDoorsCount() >= 2);
    }


    @Test
    public void getCardinalDirectionPermissionChoice() throws Exception {
        assertTrue(roomCreator.getCardinalDirectionPC(Position.CardinalDirectionPermission.Mandatory));
        assertFalse(roomCreator.getCardinalDirectionPC(Position.CardinalDirectionPermission.Disallowed));
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

        exception.expect(IllegalArgumentException.class);
        roomCreator.getProbabilityBoolean(0);
    }

    @Test
    public void getRandomRoomPosition() throws Exception {
        assertNotNull(roomCreator.getRandomRoomPosition());
    }



    @Test
    public void createInitialRoom() throws Exception {
        Room room = roomCreator.createInitialRoom(new Position(arbitraryX, arbitraryY), cardinalDirectionPermissionAllDisallowed);
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


        Class [] parameterTypesItem = {String.class, int.class, Item.Effect.class};
        Class [] parameterTypesItem2 = {String.class, int.class, Item.Effect.class};
        Object [] parameterValuesItem = {"item 1", 43, Item.Effect.DAMAGE};
        Object [] parameterValuesItem2 = {"item 2", 89, Item.Effect.HEALTH};

        List<MappableTypeWrapper> mappableTypes = new ArrayList<>();
        mappableTypes.add(new MappableTypeWrapper(Item.class, parameterTypesItem, parameterValuesItem, 1, 90, 1));
        mappableTypes.add(new MappableTypeWrapper(Item.class, parameterTypesItem2, parameterValuesItem2, 11, 90, 1));

        RoomCreator roomCreatorNew = new RoomCreator(123, new Player(1, 2, 3, new Position(0,0), new TurnSystem(new EnemyAI(213))), mappableTypes, new RoomSpace(32, 32));

        room = roomCreatorNew.createRoom(new Position(arbitraryX, arbitraryY), cardinalDirectionPermissions);
        assertNotNull(room);
        assertFalse(room.existDoor(Position.CardinalDirection.North));
        assertTrue(room.existDoor(Position.CardinalDirection.South));
        assertFalse(room.existDoor(Position.CardinalDirection.West));
        assertTrue(room.existDoor(Position.CardinalDirection.East));
    }

    @Test
    public void getPositionCardinalDirection() {
        RoomSpace thisRoomSpace = new RoomSpace(32, 32);
        Position positionNorth = new Position((thisRoomSpace.getWidth() - 1) / 2, 0);
        Position positionSouth = new Position((thisRoomSpace.getWidth() - 1) / 2, thisRoomSpace.getHeight() - 1);
        Position positionWest = new Position(0, (thisRoomSpace.getHeight() - 1) / 2);
        Position positionEast = new Position(thisRoomSpace.getWidth() - 1, (thisRoomSpace.getHeight() - 1) / 2);

        Room room = new Room(stdWorldPosition, thisRoomSpace, roomMap);

        assertEquals(roomCreator.getPositionCardinalDirection(room, Position.CardinalDirection.North), positionNorth);
        assertEquals(roomCreator.getPositionCardinalDirection(room, Position.CardinalDirection.South), positionSouth);
        assertEquals(roomCreator.getPositionCardinalDirection(room, Position.CardinalDirection.West), positionWest);
        assertEquals(roomCreator.getPositionCardinalDirection(room, Position.CardinalDirection.East), positionEast);

        try {
            roomCreator.getPositionCardinalDirection(room, null);
            fail("Expected IllegalArgementException: cardinal direction can not be null.");
        } catch (IllegalArgumentException iae) { assertTrue(true); }
    }
}

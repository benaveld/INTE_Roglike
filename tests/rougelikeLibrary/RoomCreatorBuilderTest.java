package rougelikeLibrary;


import static org.junit.Assert.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import java.util.HashMap;
import java.util.Map;


public class RoomCreatorBuilderTest {
    private RoomCreatorBuilder roomCreatorBuilder;
    private IO io;
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> stdCardinalPermissions = new HashMap<>();
    private Map<Position.CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissionsAllOptional = new HashMap<>();

    private Class [] parameterTypesItem = {
        String.class,
        int.class,
        Item.Effect.class
    };

    private Object [] parameterValuesItem = {
        "item",
        37,
        Item.Effect.DAMAGE
    };

    private Class [] parameterTypesEnemy = {
        int.class,
        int.class,
        int.class,
        Position.class,
        TurnSystem.class
    };

    private Object [] parameterValuesEnemy = {
        1,
        2,
        3,
        new Position(65, 3),
        new TurnSystem(new EnemyAI(1234))
    };


    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Before
    public void init() {
        roomCreatorBuilder = new RoomCreatorBuilder();
        io = new EnemyAI(6);
        stdCardinalPermissions.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Mandatory);
        stdCardinalPermissions.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Disallowed);
        stdCardinalPermissions.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        stdCardinalPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.North, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.South, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.West, Position.CardinalDirectionPermission.Optional);
        cardinalDirectionPermissionsAllOptional.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);
    }



    @Test
    public void setSeed() throws Exception {
        long seed = 324;
        roomCreatorBuilder.setSeed(seed);
        roomCreatorBuilder.setRoomSpace(12, 12);
        roomCreatorBuilder.setPlayer(new Player(1, 2, 3, new Position(0,0), new TurnSystem(new TUI())));
        RoomCreator roomCreator = roomCreatorBuilder.build();
        assertNotNull(roomCreator);
    }

    @Test
    public void setPlayer() throws Exception {
        long seed = 434;
        roomCreatorBuilder.setSeed(seed);
        roomCreatorBuilder.setRoomSpace(12, 12);
        Player player = new Player(1, 2, 3, new Position(0,0), new TurnSystem(new TUI()));

        roomCreatorBuilder.setPlayer(player);
        RoomCreator roomCreator = roomCreatorBuilder.build();
        Room room = roomCreator.createInitialRoom(new Position(23, 23), cardinalDirectionPermissionsAllOptional);
        Player playerFromRoom = (Player) room.getPlayer();
        assertEquals(playerFromRoom, player);
    }

    @Test
    public void setRoomSpace() throws Exception {
        long seed = 434;
        roomCreatorBuilder.setSeed(seed);
        RoomSpace roomSpace = new RoomSpace(234, 234);
        roomCreatorBuilder.setRoomSpace(roomSpace.getWidth(), roomSpace.getHeight());
        roomCreatorBuilder.setPlayer(new Player(1, 2, 3, new Position(0,0), new TurnSystem(new TUI())));
        Room room = roomCreatorBuilder.build().createInitialRoom(new Position(23, 23), cardinalDirectionPermissionsAllOptional);
        assertEquals(room.getRoomSpace().getWidth(), roomSpace.getWidth());
        assertEquals(room.getRoomSpace().getHeight(), roomSpace.getHeight());
    }

    @Test
    public void build() throws Exception {
        roomCreatorBuilder.setSeed(234);
        roomCreatorBuilder.setRoomSpace(23, 23);
        roomCreatorBuilder.setPlayer(new Player(1, 2, 3, new Position(0,0), new TurnSystem(new TUI())));
        assertNotNull(roomCreatorBuilder.build().createInitialRoom(new Position(23, 23), cardinalDirectionPermissionsAllOptional));
    }

    @Test
    public void addItem() throws Exception {
        roomCreatorBuilder.setSeed(234);
        roomCreatorBuilder.setRoomSpace(23, 23);
        roomCreatorBuilder.setPlayer(new Player(1, 2, 3, new Position(0,0), new TurnSystem(new TUI())));


        Class [] parameterTypesItemValid = {String.class, int.class, Item.Effect.class};
        Class [] parameterTypesItemInvalid = {Integer.class, int.class, Item.Effect.class};
        Object [] parameterValuesItemValid = {"item 1", 43, Item.Effect.DAMAGE};
        Object [] parameterValuesItemInvalid = {"item 1", "43", Item.Effect.DAMAGE};

        roomCreatorBuilder.addItemTemplate(Item.class, parameterTypesItemValid, parameterValuesItemValid, 1, 3, 34);

        exception.expect(IllegalArgumentException.class);
        roomCreatorBuilder.addItemTemplate(Item.class, parameterTypesItemInvalid, parameterValuesItemValid, 1, 3, 34);

        exception.expect(IllegalArgumentException.class);
        roomCreatorBuilder.addItemTemplate(Item.class, parameterTypesItemValid, parameterValuesItemInvalid, 1, 3, 34);

        exception.expect(IllegalArgumentException.class);
        roomCreatorBuilder.addItemTemplate(Item.class, parameterTypesItemInvalid, parameterValuesItemInvalid, 1, 3, 34);

    }

    @Test
    public void addEnemy() throws Exception {
        int width = 23;
        int height = 23;
        roomCreatorBuilder.setSeed(234);
        roomCreatorBuilder.setRoomSpace(width, height);
        roomCreatorBuilder.setPlayer(new Player(1, 2, 3, new Position(0,0), new TurnSystem(new TUI())));


        io = new EnemyAI(23);
        Class [] parameterTypesEnemyValid = {int.class, int.class, int.class, Position.class, TurnSystem.class};
        Class [] parameterTypesEnemyInvalid = {int.class, int.class, String.class, Position.class, TurnSystem.class};
        Object [] parameterValuesEnemyValid = {1, 43, 78, new Position(5, 7), new TurnSystem(io)};
        Object [] parameterValuesEnemyInvalid = {"5", 5, 78, "dummy"};

        roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, 1, 3, 34);

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyInvalid, parameterValuesEnemyValid, io, 1, 3, 34);
            fail("Expected IllegalArgumentException, wrong parameters addEnemy: parameter type and value");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyInvalid, io, 1, 3, 34);
            fail("Expected IllegalArgumentException, wrong parameters addEnemy: parameter value");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyInvalid, parameterValuesEnemyValid, io, 1, 3, 34);
            fail("Expected IllegalArgumentException, wrong parameters addEnemy: parameter type");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, null, 1, 3, 34);
            fail("Expected IllegalArgumentException, wrong parameters addEnemy: io == null");
        } catch (IllegalArgumentException iae) { assertTrue(true); }


        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, -1, 3, 34);
            fail("Expected IllegalArgumentException, illegal min Enemies: min < 0");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, 5, 3, 34);
            fail("Expected IllegalArgumentException, illegal min Enemies: min > max");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, width * height + 1, 3, 34);
            fail("Expected IllegalArgumentException, illeagal min Enemies: min > roomspace");
        } catch (IllegalArgumentException iae) { assertTrue(true); }


        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, 5, -1, 34);
            fail("Expected IllegalArgumentException, illeagal max Enemies: max < 0");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, 5, 4, 34);
            fail("Expected IllegalArgumentException, illeagal max Enemies: max < min");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemyValid, parameterValuesEnemyValid, io, 1, width * height + 1, 34);
            fail("Expected IllegalArgumentException, illeagal max Enemies: max > roomspace");
        } catch (IllegalArgumentException iae) { assertTrue(true); }
    }


    @Test(expected = IllegalAccessError.class)
    public void testRoomCreatorBuilderEmptyBuild() {
        (new RoomCreatorBuilder()).build();
    }


    @Test(expected = IllegalArgumentException.class)
    public void testRoomCreatorBuilderPlayer() {
        (new RoomCreatorBuilder()).setPlayer(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testRoomCreatorBuilderWidth() {
        (new RoomCreatorBuilder()).setRoomSpace(0, 0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testRoomCreatorBuilderHeight() {
        (new RoomCreatorBuilder()).setRoomSpace(0, 0);
    }


    @Test
    public void testBuilder() {
        assertEquals(roomCreatorBuilder
                .setSeed(1234)
                .setPlayer(new Player(1, 2, 3, new Position(0,0), new TurnSystem(new EnemyAI(6))))
                .setRoomSpace(Integer.MAX_VALUE, Integer.MAX_VALUE), roomCreatorBuilder);

        assertNotNull(roomCreatorBuilder.build());
    }


    @Test
    public void testGenericItem() {
        roomCreatorBuilder.setRoomSpace(13, 13);
        roomCreatorBuilder.addItemTemplate(Item.class, parameterTypesItem, parameterValuesItem, 1,5, 50);
    }

    @Test
    public void testGenericEnemy() {
        roomCreatorBuilder.setRoomSpace(13, 13);
        roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemy, parameterValuesEnemy, io,1,5, 50);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGenericItemInvalidParameterType() {
        Class [] parameterTypesInvalid = {
            Integer.class,
            int.class,
            Item.Effect.class
        };

        roomCreatorBuilder.addItemTemplate(Item.class, parameterTypesInvalid, parameterValuesItem,1, 5, 50);
    }


    //@TODO add check for negative percentaage in Item
//    @Test(expected = IllegalArgumentException.class)
//    public void testGenericItemInvalidParameterValue() {
//        Object [] parameterValuesInvalid = {
//            "Generic Item",
//            -1,
//            Item.Effect.DAMAGE
//        };
//
//        roomCreatorBuilder.AddItem(Item.class, parameterTypesItem, parameterValuesInvalid, io);
//    }


    @Test(expected = IllegalArgumentException.class)
    public void testGenericEnemyInvalidParameterType() {
        Class [] parameterTypesInvalid = {
            int.class,
            Boolean.class,
            int.class
        };

        roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesInvalid, parameterValuesEnemy, io, 1, 5, 50);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGenericEnemyInvalidParameterValue() {
        Object [] parameterValuesInvalid = {
            1,
            -1,
            3
        };

        roomCreatorBuilder.addEnemyTemplate(Enemy.class, parameterTypesEnemy, parameterValuesInvalid, io, 1, 5, 50);
    }
}

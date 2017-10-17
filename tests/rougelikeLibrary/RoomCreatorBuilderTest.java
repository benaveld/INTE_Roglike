package rougelikeLibrary;


import static org.junit.Assert.*;

import org.junit.*;

public class RoomCreatorBuilderTest {
    private RoomCreatorBuilder roomCreatorBuilder;

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
        TurnSystem.class
    };

    private Object [] parameterValuesEnemy = {
        1,
        2,
        3,
        new TurnSystem(new EnemyAI(1234))
    };

    private IO io;

    @Before
    public void init() {
        roomCreatorBuilder = new RoomCreatorBuilder();
        io = new EnemyAI(6);
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
        (new RoomCreatorBuilder()).setWorldDimension(0, 0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testRoomCreatorBuilderHeight() {
        (new RoomCreatorBuilder()).setWorldDimension(0, 0);
    }


    @Test
    public void testBuilder() {
        /**
         * Valid minimum combinations for a build:
         * seed, character, width, height
         */


        assertEquals(roomCreatorBuilder
                .setSeed(1234)
                .setPlayer(new Player(1, 2, 3, new TurnSystem(new EnemyAI(6))))
                .setWorldDimension(Integer.MAX_VALUE, Integer.MAX_VALUE), roomCreatorBuilder);

        assertNotNull(roomCreatorBuilder.build());
    }


    @Test
    public void testGenericItem() {
        roomCreatorBuilder.setWorldDimension(13, 13);
        roomCreatorBuilder.AddItem(Item.class, parameterTypesItem, parameterValuesItem, 1,5, 50);
    }

    @Test
    public void testGenericEnemy() {
        roomCreatorBuilder.setWorldDimension(13, 13);
        roomCreatorBuilder.AddEnemy(Enemy.class, parameterTypesEnemy, parameterValuesEnemy, io,1,5, 50);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGenericItemInvalidParameterType() {
        Class [] parameterTypesInvalid = {
            Integer.class,
            int.class,
            Item.Effect.class
        };

        roomCreatorBuilder.AddItem(Item.class, parameterTypesInvalid, parameterValuesItem,1, 5, 50);
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

        roomCreatorBuilder.AddEnemy(Enemy.class, parameterTypesInvalid, parameterValuesEnemy, io, 1, 5, 50);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGenericEnemyInvalidParameterValue() {
        Object [] parameterValuesInvalid = {
            1,
            -1,
            3
        };

        roomCreatorBuilder.AddEnemy(Enemy.class, parameterTypesEnemy, parameterValuesInvalid, io, 1, 5, 50);
    }
}

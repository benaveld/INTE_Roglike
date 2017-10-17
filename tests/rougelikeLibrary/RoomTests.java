package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.List;

/**
 */
public class RoomTests {
    private final int dummyX = 523223;
    private final int dummyY = 219219129;
    private Enemy enemy;
    private Item item1;
    private Item item2;
    private Item item3;


    @Before
    public void init() {
        enemy = new Enemy(12, 45, 23, new TurnSystem(new EnemyAI(1)));
        item1 = new Item("item 1", 12, Item.Effect.DAMAGE);
        item2 = new Item("item 2", 2, Item.Effect.SPEED);
        item3 = new Item("item 3", 88, Item.Effect.HEALTH);
    }

    @Test
    public void testAddDoorCardinalDirection() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));
        assertFalse(room.existDoor(Position.CardinalDirection.North));
        assertFalse(room.existDoor(Position.CardinalDirection.South));
        assertFalse(room.existDoor(Position.CardinalDirection.West));
        assertFalse(room.existDoor(Position.CardinalDirection.East));

        room.addDoor(Position.CardinalDirection.North);
        room.addDoor(Position.CardinalDirection.South);
        room.addDoor(Position.CardinalDirection.West);
        room.addDoor(Position.CardinalDirection.East);

        assertTrue(room.existDoor(Position.CardinalDirection.North));
        assertTrue(room.existDoor(Position.CardinalDirection.South));
        assertTrue(room.existDoor(Position.CardinalDirection.West));
        assertTrue(room.existDoor(Position.CardinalDirection.East));
    }

    @Test
    public void testAddEnemy() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));
        Position position = new Position(654, 223);

        assertFalse(room.existEnemy(position));
        room.addEnemy(position, enemy);
        assertTrue(room.existEnemy(position));
    }

    @Test
    public void testExistEnemy() {
        testAddEnemy();
    }

    @Test
    public void testAddItem() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));
        Position position1 = new Position(654, 223);
        Position position2 = new Position(54, 223);
        Position position3 = new Position(654, 23);

        assertFalse(room.existItem(position1));
        assertFalse(room.existItem(position2));
        assertFalse(room.existItem(position3));
        room.addItem(position1, item1);
        room.addItem(position2, item2);
        room.addItem(position3, item3);
        assertTrue(room.existItem(position1));
        assertTrue(room.existItem(position2));
        assertTrue(room.existItem(position3));
    }


    @Test
    public void testExistItem() {
        testAddItem();
    }


    @Test
    public void testExistDoorCardinalDirection() {
        testAddDoorCardinalDirection();
    }

    @Test
    public void testExistDoorPosition() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(33, 33));
        assertFalse(room.existDoor(Position.CardinalDirection.North));
        assertFalse(room.existDoor(Position.CardinalDirection.South));
        assertFalse(room.existDoor(Position.CardinalDirection.West));
        assertFalse(room.existDoor(Position.CardinalDirection.East));

        room.addDoor(Position.CardinalDirection.North);
        room.addDoor(Position.CardinalDirection.South);
        room.addDoor(Position.CardinalDirection.West);
        room.addDoor(Position.CardinalDirection.East);

        Position north = new Position(room.getRoomSpace().getWidth() / 2, 0);
        Position south = new Position(room.getRoomSpace().getWidth() / 2, room.getRoomSpace().getHeight());
        Position west = new Position(0, room.getRoomSpace().getHeight() / 2);
        Position east = new Position(room.getRoomSpace().getWidth(), room.getRoomSpace().getHeight() / 2);

        assertTrue(room.existDoor(north));
        assertTrue(room.existDoor(south));
        assertTrue(room.existDoor(west));
        assertTrue(room.existDoor(east));

    }

    @Test
    public void testDoorCount() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(33, 33));
        assertEquals(room.getDoorsCount(), 0);

        room.addDoor(Position.CardinalDirection.North);
        assertEquals(room.getDoorsCount(), 1);

        room.addDoor(Position.CardinalDirection.South);
        assertEquals(room.getDoorsCount(), 2);

        room.addDoor(Position.CardinalDirection.West);
        assertEquals(room.getDoorsCount(), 3);

        room.addDoor(Position.CardinalDirection.East);
        assertEquals(room.getDoorsCount(), 4);
    }

    @Test
    public void testRoomPosition() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));
        assertNotNull(room.getPosition());
        assertEquals(room.getPosition().getX(), dummyX);
        assertEquals(room.getPosition().getY(), dummyY);
    }

    @Test
    public void testGetRoomSpace() {
        testExistDoorPosition();
    }

    @Test
    public void testRoomPlay() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));
        Position.CardinalDirection cardinalDirection = room.play();
        assertNotNull(cardinalDirection);
    }


    @Test
    public void testSetPlayer() {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));
        assertNull(room.getPlayer());

        Position position = new Position(2, 3);

        room.setPlayer(position, new Player(1, 2, 3, new TurnSystem(new EnemyAI(1))));
        assertNotNull(room.getPlayer());
        assertEquals(room.getPlayer().getSpeed(), 1);
        assertEquals(room.getPlayer().getHealth(), 2);
        assertEquals(room.getPlayer().getDamage(), 3);
        assertTrue(room.existPlayer(position));
    }


    @Test
    public void testExistPlayer() {
        testSetPlayer();
    }


    @Test
    public void testGetFromPosition() {
        /**
         * en arraylist med mappables
         */
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32));

        Position position = new Position(234, 645);

        List<Mappable> mappables = room.getFromPosition(position);
        assertNotNull(mappables);

        for (Mappable mappable : mappables) {
            assertNotNull(mappable);
        }
    }


}

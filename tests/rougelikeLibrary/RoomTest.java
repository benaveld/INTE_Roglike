package rougelikeLibrary;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RoomTest {
    private final int dummyX = 523223;
    private final int dummyY = 219219129;
    private Enemy stdEnemy;
    private Enemy stdEnemy2;
    private Item item1;
    private Item item2;
    private Item item3;
    private Map<Position, List<Mappable>> roomMap;
    private Room stdRoom;
    private Player stdPlayer;
    private Position stdPosition;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        stdEnemy = new Enemy(12, 45, 23, new TurnSystem(new EnemyAI(1)));
        stdEnemy2 = new Enemy(99, 22, 77, new TurnSystem(new TUI()));
        item1 = new Item("item 1", 12, Item.Effect.DAMAGE);
        item2 = new Item("item 2", 2, Item.Effect.SPEED);
        item3 = new Item("item 3", 88, Item.Effect.HEALTH);

        roomMap = new HashMap<>();
        stdRoom = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32), roomMap);
        stdPlayer = new Player(1, 2, 3, new TurnSystem(new TUI()));
        stdPosition = new Position(dummyX, dummyY);

    }


    @Test
    public void getPosition() throws Exception {
        assertNotNull(stdRoom.getPosition());
        assertEquals(stdRoom.getPosition().getX(), dummyX);
        assertEquals(stdRoom.getPosition().getY(), dummyY);
    }


    @Test
    public void getRoomSpace() throws Exception {
        RoomSpace roomSpace = new RoomSpace(32, 32);
        Room room = new Room(new Position(123, 123), roomSpace, roomMap);
        assertEquals(room.getRoomSpace(), roomSpace);
   }

    @Test
    public void getRoomMap() throws Exception {
        List<Mappable> mappableList1 = new ArrayList<>();
        List<Mappable> mappableList2 = new ArrayList<>();
        roomMap.put(new Position(21,645), mappableList1);
        roomMap.put(new Position(56,78), mappableList2);
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32), roomMap);

        assertEquals(room.getRoomMap(), roomMap);
    }


    @Test
    public void play() throws Exception {
        Position.CardinalDirection cardinalDirection = stdRoom.play();
        assertNull(cardinalDirection);
    }


    @Test
    public void getFromPosition() throws Exception {
        Position position = new Position(234, 645);

        List<Mappable> mappables = stdRoom.getFromPosition(position);
        assertNotNull(mappables);

        for (Mappable mappable : mappables) {
            assertNotNull(mappable);
        }
    }


    @Test
    public void getDoorPosition() throws Exception {
        int evenWidth = 24;
        int evenHeight = 24;
        int evenHeightCenter = (evenHeight - 1) / 2;
        Position westPosition = new Position(0, evenHeightCenter);

        Room room1 = new Room(new Position(21, 2), new RoomSpace(evenWidth, evenHeight), roomMap);
        assertFalse(room1.existDoor(Position.CardinalDirection.West));
        room1.addDoor(Position.CardinalDirection.West);
        assertTrue(room1.getDoorPosition(Position.CardinalDirection.West).equals(westPosition));
    }


    @Test
    public void existDoorCardinal() throws Exception {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(33, 33), roomMap);
        assertFalse(room.existDoor(Position.CardinalDirection.North));
        assertFalse(room.existDoor(Position.CardinalDirection.South));
        assertFalse(room.existDoor(Position.CardinalDirection.West));
        assertFalse(room.existDoor(Position.CardinalDirection.East));

        room.addDoor(Position.CardinalDirection.North);
        room.addDoor(Position.CardinalDirection.South);
        room.addDoor(Position.CardinalDirection.West);
        room.addDoor(Position.CardinalDirection.East);

        Position north = new Position((room.getRoomSpace().getWidth() - 1) / 2, 0);
        Position south = new Position((room.getRoomSpace().getWidth() - 1) / 2, (room.getRoomSpace().getHeight() - 1));
        Position west = new Position(0, (room.getRoomSpace().getHeight() - 1) / 2);
        Position east = new Position((room.getRoomSpace().getWidth() - 1), (room.getRoomSpace().getHeight() - 1) / 2);

        assertTrue(room.existDoor(north));
        assertTrue(room.existDoor(south));
        assertTrue(room.existDoor(west));
        assertTrue(room.existDoor(east));
    }


    @Test
    public void existDoorPosition() throws Exception {
        int evenWidth = 24;
        int evenWidthCenter = (evenWidth - 1) / 2;
        int evenHeight = 24;
        int evenHeightCenter = (evenHeight - 1) / 2;
        int oddWidth = 33;
        int oddWidthCenter = (oddWidth - 1) / 2;
        int oddHeight = 33;
        int oddHeightCenter = (oddHeight - 1) / 2;

        Room room1 = new Room(new Position(21, 2), new RoomSpace(evenWidth, evenHeight), roomMap);
        Room room2 = new Room(new Position(221, 452), new RoomSpace(oddWidth, oddHeight), roomMap);

        // North
        assertFalse(room1.existDoor(new Position(evenWidthCenter, 0)));
        assertFalse(room2.existDoor(new Position(oddWidthCenter, 0)));
        // South
        assertFalse(room1.existDoor(new Position(evenWidthCenter, evenHeight - 1)));
        assertFalse(room2.existDoor(new Position(oddWidthCenter, oddHeight - 1)));
        // West
        assertFalse(room1.existDoor(new Position(0, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(0, oddHeightCenter)));
        // East
        assertFalse(room1.existDoor(new Position(evenWidth - 1, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(oddWidth - 1, oddHeightCenter)));

        room1.addDoor(Position.CardinalDirection.North);
        room2.addDoor(Position.CardinalDirection.North);

        // North
        assertTrue(room1.existDoor(new Position(evenWidthCenter, 0)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, 0)));
        // South
        assertFalse(room1.existDoor(new Position(evenWidthCenter, evenHeight - 1)));
        assertFalse(room2.existDoor(new Position(oddWidthCenter, oddHeight - 1)));
        // West
        assertFalse(room1.existDoor(new Position(0, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(0, oddHeightCenter)));
        // East
        assertFalse(room1.existDoor(new Position(evenWidth - 1, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(oddWidth - 1, oddHeightCenter)));

        room1.addDoor(Position.CardinalDirection.South);
        room2.addDoor(Position.CardinalDirection.South);

        // North
        assertTrue(room1.existDoor(new Position(evenWidthCenter, 0)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, 0)));
        // South
        assertTrue(room1.existDoor(new Position(evenWidthCenter, evenHeight - 1)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, oddHeight - 1)));
        // West
        assertFalse(room1.existDoor(new Position(0, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(0, oddHeightCenter)));
        // East
        assertFalse(room1.existDoor(new Position(evenWidth - 1, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(oddWidth - 1, oddHeightCenter)));

        room1.addDoor(Position.CardinalDirection.West);
        room2.addDoor(Position.CardinalDirection.West);

        // North
        assertTrue(room1.existDoor(new Position(evenWidthCenter, 0)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, 0)));
        // South
        assertTrue(room1.existDoor(new Position(evenWidthCenter, evenHeight - 1)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, oddHeight - 1)));
        // West
        assertTrue(room1.existDoor(new Position(0, evenHeightCenter)));
        assertTrue(room2.existDoor(new Position(0, oddHeightCenter)));
        // East
        assertFalse(room1.existDoor(new Position(evenWidth - 1, evenHeightCenter)));
        assertFalse(room2.existDoor(new Position(oddWidth - 1, oddHeightCenter)));

        room1.addDoor(Position.CardinalDirection.East);
        room2.addDoor(Position.CardinalDirection.East);

        // North
        assertTrue(room1.existDoor(new Position(evenWidthCenter, 0)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, 0)));
        // South
        assertTrue(room1.existDoor(new Position(evenWidthCenter, evenHeight - 1)));
        assertTrue(room2.existDoor(new Position(oddWidthCenter, oddHeight - 1)));
        // West
        assertTrue(room1.existDoor(new Position(0, evenHeightCenter)));
        assertTrue(room2.existDoor(new Position(0, oddHeightCenter)));
        // East
        assertTrue(room1.existDoor(new Position(evenWidth - 1, evenHeightCenter)));
        assertTrue(room2.existDoor(new Position(oddWidth - 1, oddHeightCenter)));
    }

    @Test
    public void existType() throws Exception {
        List<Mappable> mappableList1 = new ArrayList<>();
        List<Mappable> mappableList2 = new ArrayList<>();

        Position position = new Position(66, 66);
        Position position2 = new Position(6, 99);

        Mappable player = new Player(1, 2, 3, new TurnSystem(new TUI()));
        Mappable enemy = new Enemy(1, 2, 3, new TurnSystem(new EnemyAI(243)));

        mappableList1.add(player);
        mappableList1.add(new Item("item 1", 23, Item.Effect.SPEED));
        mappableList2.add(enemy);

        roomMap.put(position, mappableList1);
        roomMap.put(position2, mappableList2);

        Room room = new Room(new Position(23, 23), new RoomSpace(33, 33), roomMap);
        assertTrue(room.existType(mappableList1, Player.class));
        assertFalse(room.existType(mappableList2, Player.class));
        assertTrue(room.existType(mappableList2, Enemy.class));
        assertFalse(room.existType(mappableList1, Enemy.class));

        assertFalse(room.existType(mappableList1, Character.class));
        assertFalse(room.existType(mappableList2, Character.class));
    }


    @Test
    public void addEnemy() throws Exception {
        assertFalse(stdRoom.existEnemy(stdPosition));
        stdRoom.addEnemy(stdPosition, stdEnemy);
        assertTrue(stdRoom.existEnemy(stdPosition));
        assertEquals(stdRoom.getEnemy(stdPosition), stdEnemy);

        stdRoom.addEnemy(stdPosition, stdEnemy2);
        assertEquals(stdRoom.getCharacter(stdPosition), stdEnemy2);
    }


    @Test
    public void getEnemy() throws Exception {
        assertFalse(stdRoom.existEnemy(stdPosition));
        stdRoom.addEnemy(stdPosition, stdEnemy);
        //stdRoom.addEnemy(new Position(321, 432), stdPlayer);
        assertEquals(stdRoom.getEnemy(stdPosition), stdEnemy);
        assertNull(stdRoom.getEnemy(new Position(321, 432)));
        assertNull(stdRoom.getEnemy(null));
    }

    @Test
    public void existItem() throws Exception {
        assertFalse(stdRoom.existItem(stdPosition));
        stdRoom.addItem(stdPosition, item1);
        assertTrue(stdRoom.existItem(stdPosition));
        stdRoom.addItem(stdPosition, item2);
        assertTrue(stdRoom.existItem(stdPosition));
    }

    @Test
    public void existEnemy() throws Exception {
        assertFalse(stdRoom.existEnemy(stdPosition));
        stdRoom.addEnemy(stdPosition, stdEnemy);
        assertTrue(stdRoom.existEnemy(stdPosition));
    }

    @Test
    public void existCharacter() throws Exception {
        Position position1 = new Position(654, 77);
        Position position2 = new Position(4, 90);
        Position position3 = new Position(64, 553);
        Position position4 = new Position(251, 23);

        assertFalse(stdRoom.existCharacter(null));

        assertFalse(stdRoom.existCharacter(stdRoom.getFromPosition(position1)));
        assertFalse(stdRoom.existCharacter(stdRoom.getFromPosition(position2)));
        assertFalse(stdRoom.existCharacter(stdRoom.getFromPosition(position3)));
        assertFalse(stdRoom.existCharacter(stdRoom.getFromPosition(position4)));

        stdRoom.addEnemy(position1, stdEnemy);
        stdRoom.addItem(position2, item1);
        stdRoom.addItem(position3, item2);
        stdRoom.setPlayer(position4, stdPlayer);

        assertTrue(stdRoom.existCharacter(stdRoom.getFromPosition(position1)));
        assertFalse(stdRoom.existCharacter(stdRoom.getFromPosition(position2)));
        assertFalse(stdRoom.existCharacter(stdRoom.getFromPosition(position3)));
        assertTrue(stdRoom.existCharacter(stdRoom.getFromPosition(position4)));
        assertFalse(stdRoom.existCharacter(null));
        assertFalse(stdRoom.existCharacter(new ArrayList<Mappable>()));
    }

    @Test
    public void addDoor() throws Exception {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32), roomMap);
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
    public void addItem() throws Exception {
        Room room = new Room(new Position(dummyX, dummyY), new RoomSpace(32, 32), roomMap);
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
    public void getDoorsCount() throws Exception {
        assertEquals(stdRoom.getDoorsCount(), 0);

        stdRoom.addDoor(Position.CardinalDirection.North);
        assertEquals(stdRoom.getDoorsCount(), 1);

        stdRoom.addDoor(Position.CardinalDirection.South);
        assertEquals(stdRoom.getDoorsCount(), 2);

        stdRoom.addDoor(Position.CardinalDirection.West);
        assertEquals(stdRoom.getDoorsCount(), 3);

        stdRoom.addDoor(Position.CardinalDirection.East);
        assertEquals(stdRoom.getDoorsCount(), 4);
    }


    @Test
    public void setPlayer() throws Exception {
        assertNull(stdRoom.getPlayer());
        stdRoom.setPlayer(stdPosition, new Player(4, 54, 25, new TurnSystem(new EnemyAI(55))));

        assertNotNull(stdRoom.getPlayer());
        assertEquals(stdRoom.getPlayer().getSpeed(), 4);
        assertEquals(stdRoom.getPlayer().getHealth(), 54);
        assertEquals(stdRoom.getPlayer().getDamage(), 25);

        exception.expect(IllegalArgumentException.class);
        stdRoom.setPlayer(stdPosition, stdPlayer);
    }


    @Test
    public void getPlayerPosition() throws Exception {
        assertNotEquals(stdRoom.getPlayerPosition(), stdPosition);
        stdRoom.setPlayer(stdPosition, stdPlayer);
        assertEquals(stdRoom.getPlayerPosition(), stdPosition);
    }

    @Test
    public void existPlayer() throws Exception {
        assertFalse(stdRoom.existPlayer(stdPosition));
        stdRoom.setPlayer(stdPosition, stdPlayer);
        assertTrue(stdRoom.existPlayer(stdPosition));
    }

    @Test
    public void getPlayer() throws Exception {
        assertNull(stdRoom.getPlayer());
        stdRoom.setPlayer(stdPosition, new Player(14, 22, 35, new TurnSystem(new EnemyAI(5))));
        assertNotNull(stdRoom.getPlayer());
        assertEquals(stdRoom.getPlayer().getSpeed(), 14);
        assertEquals(stdRoom.getPlayer().getHealth(), 22);
        assertEquals(stdRoom.getPlayer().getDamage(), 35);
        assertTrue(stdRoom.existPlayer(stdPosition));
    }

    @Test
    public void moveCharacter() throws Exception {
        List<Mappable> mappableList1 = new ArrayList<>();
        List<Mappable> mappableList2 = new ArrayList<>();

        // same position, legal move
        Position fromPosition1 = new Position(23, 65);
        Position toPosition1 = new Position(239, 659);

        // new position
        Position fromPosition2 = new Position(239, 659);
        Position toPosition2 = new Position(123, 5);

        // move to old position
        Position fromPosition3 = new Position(123, 59);
        Position toPosition3 = new Position(23, 65);

        // move to same position as other character, illegal move
        Position fromPosition4 = new Position(23, 65);
        Position toPosition4 = new Position(66, 66);

        mappableList1.add(stdPlayer);
        mappableList1.add(new Item("item 1", 23, Item.Effect.SPEED));

        mappableList2.add(stdEnemy);
        mappableList2.add(new Item("item 2", 23, Item.Effect.SPEED));
        mappableList2.add(new Door());

        roomMap.put(fromPosition1, mappableList1);
        roomMap.put(toPosition4, mappableList2);

        assertTrue(stdRoom.existPlayer(fromPosition1));
        assertFalse(stdRoom.existPlayer(toPosition1));
        stdRoom.moveCharacter(fromPosition1, toPosition1);
        assertTrue(stdRoom.existPlayer(toPosition1));

        assertFalse(stdRoom.existPlayer(toPosition2));
        stdRoom.moveCharacter(fromPosition2, toPosition2);
        assertFalse(stdRoom.existPlayer(fromPosition2));
        assertTrue(stdRoom.existPlayer(toPosition2));

        stdRoom.addEnemy(fromPosition3, stdEnemy);
        exception.expect(IllegalArgumentException.class);
        stdRoom.moveCharacter(toPosition2, fromPosition3);
    }


    @Test
    public void moveCharacterUpdatePosition() throws Exception {
        Position position1 = new Position(234, 234);
        Position position2 = new Position(34, 23);
        Position position3 = new Position(24, 24);
        Position position4 = new Position(23, 34);

        stdRoom.setPlayer(position1, stdPlayer);
        // Character at position1 has internally its position set to position1
        assertEquals(stdRoom.getCharacter(position1).getPosition(), position1);

        stdRoom.addEnemy(position3, stdEnemy);
        assertEquals(stdRoom.getCharacter(position3).getPosition(), position3);

        stdRoom.moveCharacter(position3, position4);
        assertEquals(stdRoom.getCharacter(position4).getPosition(), position4);

        stdRoom.moveCharacter(position1, position2);
        assertEquals(stdRoom.getCharacter(position2).getPosition(), position2);
    }


    @Test
    public void getCharacter() throws Exception {
        stdRoom.addEnemy(stdPosition, stdEnemy);
        assertEquals(stdRoom.getCharacter(stdPosition), stdEnemy);
        stdRoom.addItem(new Position(123, 123), item1);
        stdRoom.addItem(new Position(13, 1), item2);
    }


    @Test
    public void getCardinalDirection() {
        RoomSpace thisRoomSpace = new RoomSpace(32, 32);
        Position positionNorth = new Position((thisRoomSpace.getWidth() - 1) / 2, 0);
        Position positionSouth = new Position((thisRoomSpace.getWidth() - 1) / 2, thisRoomSpace.getHeight() - 1);
        Position positionWest = new Position(0, (thisRoomSpace.getHeight() - 1) / 2);
        Position positionEast = new Position(thisRoomSpace.getWidth() - 1, (thisRoomSpace.getHeight() - 1) / 2);

        Room room = new Room(stdPosition, thisRoomSpace, roomMap);
        assertEquals(room.getCardinalDirection(positionNorth), Position.CardinalDirection.North);
        assertEquals(room.getCardinalDirection(positionSouth), Position.CardinalDirection.South);
        assertEquals(room.getCardinalDirection(positionWest), Position.CardinalDirection.West);
        assertEquals(room.getCardinalDirection(positionEast), Position.CardinalDirection.East);

        try {
            room.getCardinalDirection(null);
            fail("Expected IllegalArgumentException: Position can not be null.");
        } catch (IllegalArgumentException iae) { assertTrue(true); }

        try {
            room.getCardinalDirection(new Position(room.getRoomSpace().getWidth() / 2, room.getRoomSpace().getHeight() / 2));
            fail("Expected IllegalArgumentException: Invalid position for cardinal direction translation.");
        } catch (IllegalArgumentException iae) { assertTrue(true); }
    }
}
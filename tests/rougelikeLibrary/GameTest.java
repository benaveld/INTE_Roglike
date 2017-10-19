package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import rougelikeLibrary.Position.CardinalDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * Tests how the game lib could be used.
 */
public class GameTest {
    private int numberOfRoomsToPlay = 3;

    private final int roomCreatorSeed = 1337;
    private RoomCreator roomCreator;
    private Position centeredPosition;
    private MapController mapController;
    private Position nextRoomRequest;


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
        java.util.Map<CardinalDirection, Position.CardinalDirectionPermission> cardinalDirectionPermissions = new HashMap<>();
        cardinalDirectionPermissions.put(Position.CardinalDirection.East, Position.CardinalDirectionPermission.Optional);

        roomCreator = new RoomCreator(
                roomCreatorSeed,
                new Player(1, 1, 1, new TurnSystem(new EnemyAI(324))),
                new ArrayList<MappableTypeWrapper>(),
                new RoomSpace(32, 32));

        centeredPosition = new Position(Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2);
        mapController = new MapController(roomCreator.createInitialRoom(centeredPosition));
    }


    @Test
    public void playRooms() {
        for (int i = 0; i < numberOfRoomsToPlay; i++) {
            // System.out.println("Coordinate: " + mapController.getCurrentRoom().getPosition());

            nextRoomRequest = mapController.playCurrentRoom();
            if (nextRoomRequest != null) {
                if (mapController.roomExist(nextRoomRequest)) {
                    mapController.setCurrentRoom(mapController.getRoom(nextRoomRequest));
                } else {
                    mapController.setCurrentRoom(roomCreator.createRoom(nextRoomRequest, mapController.getCardinalDirectionPermissions(nextRoomRequest)));
                }
            }
        }
    }


    @Test
    public void testProbability() {
        Random rand = new Random();
        int a25 = 0;
        int a1 = 0;
        int a5 = 0;
        int a50 = 0;
        int a90 = 0;
        int a100 = 0;

        for (int i = 0; i < 10000; i++) {
            if (rand.nextDouble() <= 0.01)
                a1++;
            if (rand.nextDouble() <= 0.25)
                a25++;
            if (rand.nextDouble() <= 0.05)
                a5++;
            if (rand.nextDouble() <= 0.5)
                a50++;
            if (rand.nextDouble() <= 0.9)
                a90++;
            if (rand.nextDouble() <= 1)
                a100++;
        }
        System.out.println("1%: " + a1);
        System.out.println("5%: " + a5);
        System.out.println("25%: " + a25);
        System.out.println("50%: " + a50);
        System.out.println("90%: " + a90);
        System.out.println("100%: " + a100);
    }
}

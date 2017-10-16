package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Tests how the game lib could be used.
 */
public class GameTest {
    private int numberOfRoomsToPlay = 3;

    private final int roomCreatorSeed = 1337;
    private RoomCreator roomCreator;
    private WorldPosition centeredPosition;
    private MapController mapController;
    private WorldPosition nextRoomRequest;


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
        java.util.Map<WorldPosition.CardinalDirection, WorldPosition.CardinalDirectionPermission> cardinalDirectionPermissions = new HashMap<>();
        cardinalDirectionPermissions.put(WorldPosition.CardinalDirection.East, WorldPosition.CardinalDirectionPermission.Optional);

        roomCreator = new RoomCreator(roomCreatorSeed, new Player(1, 1, 1, new TurnSystem(new EnemyAI(1))), items, enemies, new RoomSpace(32, 32));
        centeredPosition = new WorldPosition(Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2);
        mapController = new MapController(roomCreator.createInitialRoom(centeredPosition));
    }


    @Test
    public void playRooms() {
        for (int i = 0; i < numberOfRoomsToPlay; i++)
        {
           // System.out.println("Coordinate: " + mapController.getCurrentRoom().getPosition());

            nextRoomRequest = mapController.playCurrentRoom();

            if (mapController.roomExist(nextRoomRequest)) {
                mapController.setCurrentRoom(mapController.getRoom(nextRoomRequest));
            } else {
                mapController.setCurrentRoom(roomCreator.createRoom(nextRoomRequest, mapController.getCardinalDirectionPermissions(nextRoomRequest)));
            }
        }
    }
}

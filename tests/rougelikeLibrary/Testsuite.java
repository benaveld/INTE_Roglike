package rougelikeLibrary;

import org.junit.runner.*;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CharacterTests.class,
	EnemyTests.class,
	GameControllerTests.class,
	GameTest.class,
	InventoryItemTests.class,
	IOTests.class,
	MapControllerTests.class,
	PlayerTests.class,
	PositionTests.class,
	RoomCreatorTests.class,
	RoomSpaceTests.class,
	RoomTests.class,
	TurnSystemTests.class
})
public class Testsuite {

}

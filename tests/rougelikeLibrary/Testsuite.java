package rougelikeLibrary;

import org.junit.runner.*;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		CharacterTests.class,
		EnemyTests.class,
		GameTest.class,
		InventoryItemTests.class,
		IOTests.class,
		MapControllerTest.class,
		MappableTypeWrapperTest.class,
		PlayerTests.class,
		PositionTests.class,
		RoomCreatorBuilderTest.class,
		RoomCreatorTest.class,
		RoomSpaceTests.class,
		RoomTest.class,
		TurnSystemTests.class,
		WallWalkerAITests.class
})
public class Testsuite {

}



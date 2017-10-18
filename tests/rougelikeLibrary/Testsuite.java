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
		PlayerTests.class,
		PositionTests.class,
		TurnSystemTests.class,
		RoomTest.class,
		RoomSpaceTests.class,
		MapControllerTest.class,
		RoomCreatorTest.class,
		MappableTypeWrapperTest.class,
		RoomCreatorBuilderTest.class
})
public class Testsuite {

}



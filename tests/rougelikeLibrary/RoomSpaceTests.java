package rougelikeLibrary;

import static org.junit.Assert.*;
import org.junit.*;

/**
 */
public class RoomSpaceTests {

    @Test
    public void testDimensions() {
        RoomSpace roomSpace = new RoomSpace(43, 64);
        assertEquals(roomSpace.getWidth(), 43);
        assertEquals(roomSpace.getHeight(), 64);
    }

}

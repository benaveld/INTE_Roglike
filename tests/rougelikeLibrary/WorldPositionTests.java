package rougelikeLibrary;

import static org.junit.Assert.*;

import org.junit.*;


public class WorldPositionTests {

    @Test(expected = IllegalArgumentException.class)
    public void testOverflowEast() {
        WorldPosition worldPosition = new WorldPosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
        worldPosition.getNewFromCardinalDirection(WorldPosition.CardinalDirection.East);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverflowSouth() {
        WorldPosition worldPosition = new WorldPosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
        worldPosition.getNewFromCardinalDirection(WorldPosition.CardinalDirection.South);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnderflowNorth() {
        WorldPosition worldPosition = new WorldPosition(0, 0);
        worldPosition.getNewFromCardinalDirection(WorldPosition.CardinalDirection.North);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnderflowWest() {
        WorldPosition worldPosition = new WorldPosition(0, 0);
        worldPosition.getNewFromCardinalDirection(WorldPosition.CardinalDirection.West);
    }
}

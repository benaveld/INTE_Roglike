package rougelikeLibrary;

/**
 * Controls the world space which consists of a grid where each coordinate in the grid is a room.
 * A new room can only be created on a coordinate if there is no prior room and the doors between adjacent rooms align.
 * The coordinate system starts with [0, 0] top-left.
 * As an example:
 * a room at coordinate [3, 5] with doors left and top, can only have a room at coordinate [2, 5] if it has a door to the right and a room at coordinate [3, 4] if it has a door at the bottom.
 */
public class MapController {
}

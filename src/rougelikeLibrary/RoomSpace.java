package rougelikeLibrary;

/**
 * Handles local space in a room that is relative to the world space. Every coordinate in room space can contain 1 item/characters/door.
 */
public class RoomSpace {
    private final int width;
    private final int height;

    public RoomSpace(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}

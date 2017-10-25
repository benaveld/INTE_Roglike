package rougelikeLibrary;

/**
 * Handles local space in a room that is relative to the world space. Every coordinate in room space can contain 1 item/characters/door.
 */
public class RoomSpace {
    private final int width;
    private final int height;


    /**
     * Constructor
     *
     * @param width in cells for the room
     * @param height in cells for the room
     */
    public RoomSpace(int width, int height) {
        this.width = width;
        this.height = height;
    }


    /**
     * Retreives the number of cells in width.
     * @return number of cells in width.
     */
    public int getWidth() {
        return width;
    }


    /**
     * Retreives the number of cells in height.
     * @return number of cells in height.
     */    public int getHeight() {
        return height;
    }


    /**
     * retreives the maximum number that this space can index, not to be mixed with number of cells.
     * @return maximum number that this space can index in width.
     */
    public int getMaxIndexWidth() {
        return width - 1;
    }



    /**
     * retreives the maximum number that this space can index, not to be mixed with number of cells.
     * @return maximum number that this space can index in height.
     */    public int getMaxIndexHeight() {
        return height - 1;
    }

}

package rougelikeLibrary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RoomCreatorBuilder {
    private long seed;
    private Character player;
    private int width;
    private int height;

    private List<MappableTypeWrapper> mappableTypes = new ArrayList<>();


    /**
     * Sets the seed for random generation.
     * @param seed the seed value
     * @return reference to this RoomCreatorBuilder
     */
    public RoomCreatorBuilder setSeed(long seed) {
        this.seed = seed;
        return this;
    }


    /**
     * Sets the player
     * @param player the instantiated player object
     * @return reference to this RoomCreatorBuilder
     * @throws IllegalArgumentException if player is null.
     */
    public RoomCreatorBuilder setPlayer(Character player) throws IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException("Player can't be null.");
        }
        this.player = player;
        return this;
    }


    /**
     * Sets the world dimension
     * @param width the width of the world
     * @param height the height of the world
     * @return reference to this RoomCreatorBuilder
     * @throws IllegalArgumentException if world size is not greater than zero on width and height.
     */
    public RoomCreatorBuilder setWorldDimension(int width, int height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than zero.");
        }
        this.width = width;
        this.height = height;
        return this;
    }


    /**
     * Builds a new RoomCreator with the specified parameters.
     * @return a RoomCreator.
     * @throws IllegalAccessError if not all of the mandatory parameters is set.
     */
    public RoomCreator build() throws IllegalAccessError {
        if (Stream.of(seed, player, width, height).anyMatch(argument -> argument == null)) {
            throw new IllegalAccessError("Mandatory fields: seed, player, width and height is not set.");
        }
        return new RoomCreator(seed, player, mappableTypes, new RoomSpace(width, height));
    }


    /**
     * Adds a generic item type with parameter values. Used as a template for the specific item type.
     * @param itemClass the item class to use. Must be of type Item.
     * @param parameterTypes an array of parameter types for Item class.
     * @param parameterValues an array of parameter values for Item class.
     * @param io the io to use for the item type
     * @param minItems minimum amount of items in room
     * @param maxItems maximum amount of items in room
     * @param probability the probability to create this item
     * @throws IllegalArgumentException if io is null or the number of parameters or value in parameterTypes or parameterValues is wrong.
     */
    public void AddItem(Class<? extends Item> itemClass, Class<?> [] parameterTypes, Object [] parameterValues, IO io, int minItems, int maxItems, int probability)
            throws IllegalArgumentException {
        if (width * height < 1) {
            throw new IllegalArgumentException("Can't add items before world dimension is set");
        }
        testType(itemClass, parameterTypes, parameterValues, io);
        testMinMax(minItems, maxItems);
        mappableTypes.add(new MappableTypeWrapper(itemClass, parameterTypes, parameterValues, io, minItems, maxItems, probability));
    }


    /**
     * Adds a generic enemy type with parameter values. Used as a template for the specific item type.
     * @param enemyClass the item class to use. Must be of type Item.
     * @param parameterTypes an array of parameter types for Item class.
     * @param parameterValues an array of parameter values for Item class.
     * @param io the io to use for the item type
     * @param minEnemies minimum amount of enemies in a room
     * @param maxEnemies maximum amount of enemies in a room
     * @param probability the probability to create this enemy
     * @throws IllegalArgumentException if io is null or the number of parameters or value in parameterTypes or parameterValues is wrong.
     */
    public void AddEnemy(Class<? extends Character> enemyClass, Class<?> [] parameterTypes, Object [] parameterValues, IO io, int minEnemies, int maxEnemies, int probability)
            throws IllegalArgumentException {
        if (width * height < 1) {
            throw new IllegalArgumentException("Can't add items before world dimension is set");
        }
        testType(enemyClass, parameterTypes, parameterValues, io);
        testMinMax(minEnemies, maxEnemies);
        mappableTypes.add(new MappableTypeWrapper(enemyClass, parameterTypes, parameterValues, io, minEnemies, maxEnemies, probability));
    }


    /**
     * Test min and max values
     * @param min min value to test for
     * @param max max value to test for
     * @throws IllegalArgumentException
     */
    private void testMinMax(int min, int max) throws IllegalArgumentException {
        if ((min < 0) ||
            (min > max) ||
            (min > (width * height))) {
            throw new IllegalArgumentException("Illegal min value for enemies.");
        }
        if ((max < 0) ||
            (max < min) ||
            (max > (width * height))) {
            throw new IllegalArgumentException("Illegal max value for enemies.");
        }
    }


    /**
     * Test reflection so types and values are valid
     * @param typeClass
     * @param parameterTypes
     * @param parameterValues
     * @param io
     * @throws IllegalArgumentException
     */
    private void testType(Class<?> typeClass, Class<?> [] parameterTypes, Object [] parameterValues, IO io)
            throws IllegalArgumentException {
        if (io == null) {
            throw new IllegalArgumentException("IO can't be null.");
        }

        // Test if parameter types and values is valid
        try {
            Constructor<?> constructor = typeClass.getConstructor(parameterTypes);
            Object o = constructor.newInstance(parameterValues);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException |
            InvocationTargetException ex) {
            throw new IllegalArgumentException("Item type, parameter types or parameter values is illegal. : " + ex);
        }
    }
}

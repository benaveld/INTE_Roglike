package rougelikeLibrary;

import java.math.BigInteger;

/**
 * World position with helpers for cardinal direction.
 */
public class WorldPosition extends Position {
    public enum CardinalDirection {
        North,
        South,
        East,
        West
    }

    public enum CardinalDirectionPermission {
        Optional,
        Disallowed,
        Mandatory
    }

    public WorldPosition(int x, int y) {
        super(x, y);
    }

    public WorldPosition getNewFromCardinalDirection(CardinalDirection cardinalDirection) throws IllegalArgumentException {
        BigInteger bigIntegerX = new BigInteger(Integer.toString(getX()));
        BigInteger bigIntegerY = new BigInteger(Integer.toString(getY()));

        int x = getX();
        int y = getY();

        try {
            switch(cardinalDirection) {
                // Decrease Y
                case North:
                    y = bigIntegerY.subtract(BigInteger.ONE).intValueExact();
                    break;
                // Increase Y
                case South:
                    y = bigIntegerY.add(BigInteger.ONE).intValueExact();
                    break;
                // Decrease X
                case West:
                    x = bigIntegerX.subtract(BigInteger.ONE).intValueExact();
                    break;
                // Increase X
                case East:
                    x = bigIntegerX.add(BigInteger.ONE).intValueExact();
                    break;
            }
        } catch (ArithmeticException ae) {
            throw new IllegalArgumentException("Coordinate under or overflow.");
        }

        return new WorldPosition(x, y);
    }
}

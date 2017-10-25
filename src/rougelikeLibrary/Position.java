package rougelikeLibrary;

import java.util.Objects;

public class Position {
	private int x, y;

	public enum CardinalDirection {
		North, East, South, West
	}

	public enum CardinalDirectionPermission {
		Optional, Disallowed, Mandatory
	}

	public Position(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("X or Y can't be negative.");
		}

		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position p = (Position) obj;
			return this.x == p.x && this.y == p.y;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}

	public Position setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("X can't be negative.");
		}
		return new Position(x, this.y);
	}

	public int getY() {
		return y;
	}

	public Position setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("Y can't be negative.");
		}
		return new Position(this.x, y);
	}

	/**
	 * 
	 * @param deltaX 
	 * @param deltaY
	 * @return A new Position with x + deltaX and y + deltaY.
	 */
	public Position translate(int deltaX, int deltaY) {
		if (Math.addExact(x, deltaX) < 0 || Math.addExact(y, deltaY) < 0) {
			throw new IllegalArgumentException(
					"Chanage x or y to less then 0. new X: " + (x + deltaX) + " new Y: " + (y + deltaY));
		}
		return new Position(x + deltaX, y + deltaY);
	}
	
	/**
	 * 
	 * @param dir A Cardinal Direction. The enum is located in Position.
	 * @return New Position with the same x and y but with one step in the direction of the cardinal direction.
	 */
	public Position translateCardinalDirection(CardinalDirection dir) {
		Position p = null;
		switch (dir) {
		// Decrease Y
		case North:
			p = this.translate(0, -1);
			break;
		// Increase Y
		case South:
			p = this.translate(0, 1);
			break;
		// Decrease X
		case West:
			p = this.translate(-1, 0);
			break;
		// Increase X
		case East:
			p = this.translate(1, 0);
			break;
		}
		return p;
	}
	/**
	 * 
	 * @return New Position with the same x and y values.
	 */
	public Position getLocation() {
		return new Position(this.x, this.y);
	}
}

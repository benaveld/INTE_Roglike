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

	public void setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("X can't be negative.");
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("Y can't be negative.");
		}
		this.y = y;
	}

	public void translate(int deltaX, int deltaY) {
		if (Math.addExact(x, deltaX) < 0 || Math.addExact(y, deltaY) < 0) {
			throw new IllegalArgumentException(
					"Chanage x or y to less then 0. new X: " + (x + deltaX) + " new Y: " + (y + deltaY));
		}
		x += deltaX;
		y += deltaY;
	}

	public void translateCardinalDirection(CardinalDirection dir) {
		switch (dir) {
		// Decrease Y
		case North:
			this.translate(0, -1);
			break;
		// Increase Y
		case South:
			this.translate(0, 1);
			break;
		// Decrease X
		case West:
			this.translate(-1, 0);
			break;
		// Increase X
		case East:
			this.translate(1, 0);
			break;
		}
	}

	public Position getLocation() {
		return new Position(this.x, this.y);
	}

	public Position getNewPositionFromCardinalDirection(CardinalDirection cardinalDirection) {
		Position p = this.getLocation();
		p.translateCardinalDirection(cardinalDirection);
		return p;
	}
}

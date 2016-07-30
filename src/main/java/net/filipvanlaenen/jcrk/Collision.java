package net.filipvanlaenen.jcrk;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A hash function collision between two or more points.
 */
public class Collision {
	private static final int THIRTY_ONE = 31;
	private final HashFunction hashFunction;
	private final Set<Point> points;
	private final Point hashValue;

	Collision(HashFunction hashFunction, Point... points) {
		if (points.length < 2) {
			throw new IllegalArgumentException(
					String.format("There should be at least two points, but found only %d.", points.length));
		}
		this.hashFunction = hashFunction;
		this.points = new HashSet<Point>(Arrays.asList(points));
		this.hashValue = points[0].hash(hashFunction);
		for (Point point : points) {
			if (!point.hash(hashFunction).equals(hashValue)) {
				throw new IllegalArgumentException(String.format(
						"One of the points (0x%s) has a different hash value than the first point (0x%s): 0x%s ≠ 0x%s.",
						point.asHexadecimalString(), points[0].asHexadecimalString(),
						point.hash(hashFunction).asHexadecimalString(), hashValue.asHexadecimalString()));
			}
		}
	}

	HashFunction getHashFunction() {
		return hashFunction;
	}

	/**
	 * Returns an unmodifiable set with the points having the same hash value.
	 * 
	 * @return An unmodifiable set with the points having the same hash value.
	 */
	public Set<Point> getPoints() {
		return Collections.unmodifiableSet(points);
	}

	/**
	 * Returns the hash value of the collision.
	 * 
	 * @return The hash value of the collision.
	 */
	public Point getHashValue() {
		return hashValue;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Collision && isEqual((Collision) other);
	}

	private boolean isEqual(Collision other) {
		return hashFunction.equals(other.getHashFunction()) && points.equals(other.getPoints());
	}

	private int addValueToHashCode(int hashCode, int value) {
		return THIRTY_ONE * hashCode + value;
	}

	@Override
	public int hashCode() {
		int hashCode = addValueToHashCode(1, hashFunction.hashCode());
		return addValueToHashCode(hashCode, points.hashCode());
	}
}

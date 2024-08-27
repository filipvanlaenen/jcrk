package net.filipvanlaenen.jcrk;

import java.util.Set;

/**
 * A hash function collision between two or more points.
 *
 * @param hashFunction The hash function.
 * @param points       The points.
 */
public record Collision(HashFunction hashFunction, Set<Point> points) {
    /**
     * Constructor with the hash function and the points as its parameters.
     *
     * @param hashFunction The hash function.
     * @param points       The points.
     */
    public Collision(final HashFunction hashFunction, final Point... points) {
        this(hashFunction, Set.of(points));
        if (points.length < 2) {
            throw new IllegalArgumentException(
                    String.format("There should be at least two points, but found only %d.", points.length));
        }
        Point hashValue = points[0].hash(hashFunction);
        for (Point point : points) {
            if (!point.hash(hashFunction).equals(hashValue)) {
                throw new IllegalArgumentException(String.format(
                        "One of the points (0x%s) has a different hash value than the first point (0x%s): 0x%s ≠ 0x%s.",
                        point.asHexadecimalString(), points[0].asHexadecimalString(),
                        point.hash(hashFunction).asHexadecimalString(), hashValue.asHexadecimalString()));
            }
        }
    }
}

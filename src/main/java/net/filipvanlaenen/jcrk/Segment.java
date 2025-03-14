package net.filipvanlaenen.jcrk;

/**
 * A segment on a path in Pollard's rho collision search. A segment has a start point, an end point, a length, an order
 * and a hash function.
 */
public final class Segment {
    /**
     * The magic number 31.
     */
    private static final int THIRTY_ONE = 31;
    /**
     * The start point of the segment.
     */
    private final Point startPoint;
    /**
     * The (current) end point of the segment.
     */
    private Point endPoint;
    /**
     * The (current) length of the segment.
     */
    private long length;
    /**
     * The order of the segment.
     */
    private final int order;
    /**
     * The hash function of the segment.
     */
    private final HashFunction hashFunction;

    /**
     * Constructs a segment using a start point, order and a hash function, and defaults the other segment values.
     *
     * @param startPoint   The start point.
     * @param order        The order.
     * @param hashFunction The hash function.
     */
    Segment(final Point startPoint, final int order, final HashFunction hashFunction) {
        this(startPoint, startPoint, 0, order, hashFunction);
    }

    /**
     * Constructs a segment using a start point, end point, length, order and hash function.
     *
     * @param startPoint   The start point.
     * @param endPoint     The end point.
     * @param length       The length.
     * @param order        The order.
     * @param hashFunction The hash function.
     */
    Segment(final Point startPoint, final Point endPoint, final long length, final int order,
            final HashFunction hashFunction) {
        if (startPoint.order() < order) {
            throw new IllegalArgumentException(String.format(
                    "The start point's order (%d) is less than the provided order (%d).", startPoint.order(), order));
        } else if (length < 0) {
            throw new IllegalArgumentException(String.format("The length (%d) is negative.", length));
        } else if (order < 0) {
            throw new IllegalArgumentException(String.format("The order (%d) is negative.", order));
        }
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
        this.order = order;
        this.hashFunction = hashFunction;
    }

    /**
     * Adds a value to the (Java) hash code.
     *
     * @param hashCode The original (Java) hash code.
     * @param value    The value to be added.
     * @return The new (Java) hash code.
     */
    private int addValueToHashCode(final int hashCode, final int value) {
        return THIRTY_ONE * hashCode + value;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Segment && isEqual((Segment) other);
    }

    /**
     * Extends the segment by adding one point to it.
     */
    void extend() {
        if (isComplete()) {
            throw new IllegalStateException("A complete segment cannot be extended.");
        }
        endPoint = endPoint.hash(hashFunction);
        length++;
    }

    /**
     * Returns the end point.
     *
     * @return The end point.
     */
    Point getEndPoint() {
        return endPoint;
    }

    /**
     * Returns the hash function.
     *
     * @return The hash function.
     */
    HashFunction getHashFunction() {
        return hashFunction;
    }

    /**
     * Returns the length.
     *
     * @return The length.
     */
    long getLength() {
        return length;
    }

    /**
     * Returns the order.
     *
     * @return The order.
     */
    int getOrder() {
        return order;
    }

    /**
     * Returns the start point.
     *
     * @return The start point.
     */
    Point getStartPoint() {
        return startPoint;
    }

    @Override
    public int hashCode() {
        int hashCode = addValueToHashCode(1, startPoint.hashCode());
        hashCode = addValueToHashCode(hashCode, endPoint.hashCode());
        hashCode = addValueToHashCode(hashCode, (int) length);
        hashCode = addValueToHashCode(hashCode, order);
        return addValueToHashCode(hashCode, hashFunction.hashCode());
    }

    /**
     * Checks whether the segment is complete. The segment is complete if its length is larger than zero and the order
     * at least as a the required order.
     *
     * @return True if the segment is complete, and false otherwise.
     */
    boolean isComplete() {
        return length > 0 && endPoint.order() >= order;
    }

    /**
     * Checks whether a segment is equal to another segment.
     *
     * @param other The other segment.
     * @return True if the segments are equal, and false otherwise.
     */
    private boolean isEqual(final Segment other) {
        return isSpatiallyEqual(other) && order == other.order && hashFunction == other.hashFunction;
    }

    /**
     * Checks whether a segment is spatially equal to another segment.
     *
     * @param other The other segment.
     * @return True if the segments are spatially equal, and false otherwise.
     */
    private boolean isSpatiallyEqual(final Segment other) {
        return startPoint.equals(other.startPoint) && endPoint.equals(other.endPoint) && length == other.length;
    }
}

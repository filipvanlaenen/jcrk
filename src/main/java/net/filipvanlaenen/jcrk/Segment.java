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

    Segment(final Point startPoint, final int order, final HashFunction hashFunction) {
        this(startPoint, startPoint, 0, order, hashFunction);
    }

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

    Point getStartPoint() {
        return startPoint;
    }

    Point getEndPoint() {
        return endPoint;
    }

    long getLength() {
        return length;
    }

    int getOrder() {
        return order;
    }

    boolean isComplete() {
        return length > 0 && endPoint.order() >= order;
    }

    void extend() {
        if (isComplete()) {
            throw new IllegalStateException("A complete segment cannot be extended.");
        }
        endPoint = endPoint.hash(hashFunction);
        length++;
    }

    HashFunction getHashFunction() {
        return hashFunction;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Segment && isEqual((Segment) other);
    }

    private boolean isEqual(final Segment other) {
        return isSpatiallyEqual(other) && order == other.order && hashFunction == other.hashFunction;
    }

    private boolean isSpatiallyEqual(final Segment other) {
        return startPoint.equals(other.startPoint) && endPoint.equals(other.endPoint) && length == other.length;
    }

    private int addValueToHashCode(final int hashCode, final int value) {
        return THIRTY_ONE * hashCode + value;
    }

    @Override
    public int hashCode() {
        int hashCode = addValueToHashCode(1, startPoint.hashCode());
        hashCode = addValueToHashCode(hashCode, endPoint.hashCode());
        hashCode = addValueToHashCode(hashCode, (int) length);
        hashCode = addValueToHashCode(hashCode, order);
        return addValueToHashCode(hashCode, hashFunction.hashCode());
    }
}

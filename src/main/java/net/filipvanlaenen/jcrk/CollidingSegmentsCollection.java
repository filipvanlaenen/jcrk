package net.filipvanlaenen.jcrk;

import java.util.Comparator;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.SortedCollection;

/**
 * Class representing a collection of two or more colliding segments. All the segments in the collection have to be
 * produced with the same hash function and share the same end point, but have different start points.
 */
public final class CollidingSegmentsCollection {
    /**
     * The end point of the segments.
     */
    private final Point endPoint;
    /**
     * The hash function of the segments.
     */
    private final HashFunction hashFunction;
    /**
     * The colliding segments, sorted by length.
     */
    private final SortedCollection<Segment> segments;

    /**
     * Creates a new colliding segments from a collection of segments.
     *
     * @param segments The colliding segments.
     */
    CollidingSegmentsCollection(final Collection<Segment> segments) {
        if (segments.size() < 2) {
            throw new IllegalArgumentException(String.format(
                    "There should be at least two segments provided to the constructor, but found only %d.",
                    segments.size()));
        }
        this.segments = SortedCollection.of(new Comparator<Segment>() {
            @Override
            public int compare(final Segment s0, final Segment s1) {
                long l0 = s0.getLength();
                long l1 = s1.getLength();
                if (l0 < l1) {
                    return -1;
                } else if (l0 > l1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }, segments.toArray(new Segment[segments.size()]));
        Segment segment = segments.get();
        endPoint = segment.getEndPoint();
        hashFunction = segment.getHashFunction();
        for (Segment s : segments) {
            if (!s.getEndPoint().equals(endPoint)) {
                throw new IllegalArgumentException(
                        String.format("Not all segments have the same end point (0x%s ≠ 0x%s).",
                                s.getEndPoint().asHexadecimalString(), endPoint.asHexadecimalString()));
            }
            if (!s.getHashFunction().equals(hashFunction)) {
                throw new IllegalArgumentException(
                        String.format("Not all segments have the same hash function (%s ≠ %s).",
                                s.getHashFunction().toString(), hashFunction.toString()));
            }
        }
    }

    /**
     * Returns the end point of the segments.
     *
     * @return The end point of the segments.
     */
    Point getEndPoint() {
        return endPoint;
    }

    /**
     * Returns the hash function of the segments.
     *
     * @return The hash function of the segments.
     */
    HashFunction getHashFunction() {
        return hashFunction;
    }
}

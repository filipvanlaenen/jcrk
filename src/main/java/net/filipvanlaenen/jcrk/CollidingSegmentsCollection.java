package net.filipvanlaenen.jcrk;

import java.util.Comparator;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.SortedCollection;

/**
 * Class representing a collection of two or more colliding segments. All the segments in the collection have to be
 * produced with the same hash function and share the same end point, but have different start points.
 */
public final class CollidingSegmentsCollection {
    /**
     * Comparator to sort segments from shortest to longest.
     */
    static final Comparator<Segment> SEGMENT_COMPARATOR = new Comparator<Segment>() {
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
    };

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
        this.segments = SortedCollection.of(SEGMENT_COMPARATOR, segments.toArray(new Segment[segments.size()]));
        Segment segment = segments.get();
        endPoint = segment.getEndPoint();
        hashFunction = segment.getHashFunction();
        ModifiableCollection<Point> startPoints = ModifiableCollection.empty();
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
            Point startPoint = s.getStartPoint();
            if (startPoints.contains(startPoint)) {
                throw new IllegalArgumentException(String.format("Some segments have the same start point (0x%s).",
                        startPoint.asHexadecimalString()));
            } else {
                startPoints.add(startPoint);
            }
        }
    }

    /**
     * Finds a collision.
     *
     * @return A collision.
     */
    Collision findCollision() {
        Segment segment0 = segments.getAt(0);
        Segment segment1 = segments.getAt(1);
        long segmentLength0 = segment0.getLength();
        long segmentLength1 = segment1.getLength();
        long lengthDifference = segmentLength1 - segmentLength0;
        Point p = segment1.getStartPoint();
        for (int i = 0; i < lengthDifference; i++) {
            p = p.hash(hashFunction);
        }
        Point segment0Point = segment0.getStartPoint();
        Point nextSegment0Point = segment0Point.hash(hashFunction);
        Point segment1Point = p;
        Point nextSegment1Point = segment1Point.hash(hashFunction);
        while (!nextSegment0Point.equals(nextSegment1Point)) {
            segment0Point = nextSegment0Point;
            nextSegment0Point = segment0Point.hash(hashFunction);
            segment1Point = nextSegment1Point;
            nextSegment1Point = segment1Point.hash(hashFunction);
        }
        return new Collision(hashFunction, segment0Point, segment1Point);
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

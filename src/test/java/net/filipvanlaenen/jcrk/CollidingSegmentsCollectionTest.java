package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;

/**
 * Unit tests on the class <code>CollidingSegmentsCollection</code>.
 */
public class CollidingSegmentsCollectionTest {
    /**
     * The magic number two.
     */
    private static final int TWO = 2;
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The magic number four.
     */
    private static final int FOUR = 4;
    /**
     * The magic number seven.
     */
    private static final int SEVEN = 7;
    /**
     * The magic number ten.
     */
    private static final int TEN = 10;
    /**
     * The hash function SHA-1 truncated to 5 bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1_5_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 5);
    /**
     * The hash function SHA-1 truncated to 8 bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    /**
     * The hash function SHA-256 truncated to 8 bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_SHA256_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA256, 8);
    /**
     * The point 0x00.
     */
    private static final Point POINT_00 = new Point((byte) 0x00);
    /**
     * The point 0x02.
     */
    private static final Point POINT_02 = new Point((byte) 0x02);
    private static final Segment SEGMENT_02_02 = new Segment(POINT_02, POINT_02, SEVEN, FOUR, TRUNCATED_SHA1_8_BITS);
    /**
     * The point 0x0A.
     */
    private static final Point POINT_0A = new Point((byte) 0x0A);
    private static final Segment SEGMENT_0A_02 = new Segment(POINT_0A, POINT_02, TEN, FOUR, TRUNCATED_SHA1_8_BITS);
    /**
     * The point 0x20.
     */
    private static final Point POINT_20 = new Point((byte) 0x20);
    /**
     * The point 0x3C.
     */
    private static final Point POINT_3C = new Point((byte) 0x3C);
    /**
     * The point 0x58.
     */
    private static final Point POINT_58 = new Point((byte) 0x58);
    /**
     * The point 0xC4.
     */
    private static final Point POINT_C4 = new Point((byte) 0xC4);
    /**
     * The point 0xE0.
     */
    private static final Point POINT_E0 = new Point((byte) 0xE0);
    /**
     * The segment going from point 0x02 to point 0xC4.
     */
    private static final Segment SEGMENT_02_C4 = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
    /**
     * The segment going from point 0x3C to point 0x02.
     */
    private static final Segment SEGMENT_3C_02 = new Segment(POINT_3C, POINT_02, 1, 1, TRUNCATED_SHA1_8_BITS);
    /**
     * The segment going from point 0x3C to point 0xC4.
     */
    private static final Segment SEGMENT_3C_C4 = new Segment(POINT_3C, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
    /**
     * The segment going from point 0x3C to point 0xC4, but with the truncated SHA-256 hash function.
     */
    private static final Segment SEGMENT_3C_C4_SHA256 = new Segment(POINT_3C, POINT_C4, 1, 1, TRUNCATED_SHA256_8_BITS);

    /**
     * Verifies that the constructor throws an IllegalArgumentException if only one segment is provided.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfOnlyOneSegmentProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4)));
        assertEquals("There should be at least two segments provided to the constructor, but found only 1.",
                exception.getMessage());
    }

    /**
     * Verifies that the constructor throws an IllegalArgumentException if the segments have different end points.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfTheSegmentsHaveDifferentEndPoints() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_3C_02)));
        assertTrue(
                Collection
                        .of("Not all segments have the same end point (0x02 ≠ 0xc4).",
                                "Not all segments have the same end point (0xc4 ≠ 0x02).")
                        .contains(exception.getMessage()));
    }

    /**
     * Verifies that the constructor throws an IllegalArgumentException if some of the segments have the same start
     * points.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfSomeOfTheSegmentsHaveTheSameStartPoints() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_02_C4)));
        assertEquals("Some segments have the same start point (0x02).", exception.getMessage());
    }

    /**
     * Verifies that the constructor throws an IllegalArgumentException if the segments have different hash functions.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfTheSegmentsHaveDifferentHashFunctions() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_3C_C4_SHA256)));
        assertTrue(Collection
                .of("Not all segments have the same hash function (TRUNC(SHA-1, 8) ≠ TRUNC(SHA-256, 8)).",
                        "Not all segments have the same hash function (TRUNC(SHA-256, 8) ≠ TRUNC(SHA-1, 8)).")
                .contains(exception.getMessage()));
    }

    /**
     * Verifies that the end point of two segments is extracted correctly.
     */
    @Test
    public void getEndPointShouldExtractedTheEndPointCorrectly() {
        CollidingSegmentsCollection collidingSegments =
                new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_3C_C4));
        assertEquals(POINT_C4, collidingSegments.getEndPoint());
    }

    /**
     * Verifies that the hash function of two segments is extracted correctly.
     */
    @Test
    public void getHashFunctionShouldExtractedTheHashFunctionCorrectly() {
        CollidingSegmentsCollection collidingSegments =
                new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_3C_C4));
        assertEquals(TRUNCATED_SHA1_8_BITS, collidingSegments.getHashFunction());
    }

    /**
     * Verifies that for a pair of colliding segments of order 0, the collision can be found.
     */
    @Test
    public void findCollisionShouldFindCollisionOfOrder0() {
        CollidingSegmentsCollection collidingSegments =
                new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_3C_C4));
        Collision collision = collidingSegments.findCollision();
        assertEquals(collision, new Collision(TRUNCATED_SHA1_8_BITS, POINT_02, POINT_3C));
    }

    /**
     * Verifies that for a pair of colliding segments of order 2, the collision can be found.
     */
    @Test
    public void findCollisionShouldFindCollisionOfOrder2() {
        Segment s1 = new Segment(POINT_00, POINT_20, THREE, TWO, TRUNCATED_SHA1_5_BITS);
        Segment s2 = new Segment(POINT_20, POINT_20, FOUR, TWO, TRUNCATED_SHA1_5_BITS);
        CollidingSegmentsCollection collidingSegments = new CollidingSegmentsCollection(Collection.of(s1, s2));
        Collision collision = collidingSegments.findCollision();
        assertEquals(collision, new Collision(TRUNCATED_SHA1_5_BITS, POINT_E0, POINT_58));
    }

    /**
     * Verifies that for a pair of colliding segments of order 4, the collision can be found.
     */
    @Test
    public void findCollisionShouldFindCollisionOfOrder4() {
        CollidingSegmentsCollection collidingSegments =
                new CollidingSegmentsCollection(Collection.of(SEGMENT_02_02, SEGMENT_0A_02));
        Collision collision = collidingSegments.findCollision();
        assertEquals(collision, new Collision(TRUNCATED_SHA1_8_BITS, POINT_02, POINT_3C));
    }

    /**
     * Verifies that the comparator returns that a shorter segment is shorter than a long one.
     */
    @Test
    public void segmentComparatorReturnsNegativeNumberWhenComparingShortSegmentToLongSegment() {
        assertTrue(CollidingSegmentsCollection.SEGMENT_COMPARATOR.compare(SEGMENT_02_02, SEGMENT_0A_02) < 0);
    }

    /**
     * Verifies that the comparator returns that a segment is equally long to itself.
     */
    @Test
    public void segmentComparatorReturnsZeroWhenComparingSegmentWithItself() {
        assertEquals(0, CollidingSegmentsCollection.SEGMENT_COMPARATOR.compare(SEGMENT_02_02, SEGMENT_02_02));
    }

    /**
     * Verifies that the comparator returns that a longer segment is longer than a short one.
     */
    @Test
    public void segmentComparatorReturnsPositiveNumberWhenComparingLongSegmentToShortSegment() {
        assertTrue(CollidingSegmentsCollection.SEGMENT_COMPARATOR.compare(SEGMENT_0A_02, SEGMENT_02_02) > 0);
    }
}

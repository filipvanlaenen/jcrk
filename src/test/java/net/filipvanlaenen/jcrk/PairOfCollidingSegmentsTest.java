package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.filipvanlaenen.kolektoj.Collection;

/**
 * Unit tests on the class PairOfCollidingSegments.
 */
public class PairOfCollidingSegmentsTest {
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1_5_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 5);
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    private static final TruncatedStandardHashFunction TRUNCATED_SHA256_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA256, 8);
    private static final Point POINT_00 = new Point((byte) 0x00);
    private static final Point POINT_02 = new Point((byte) 0x02);
    private static final Point POINT_0A = new Point((byte) 0x0A);
    private static final Point POINT_20 = new Point((byte) 0x20);
    private static final Point POINT_3C = new Point((byte) 0x3C);
    private static final Point POINT_58 = new Point((byte) 0x58);
    private static final Point POINT_C4 = new Point((byte) 0xC4);
    private static final Point POINT_E0 = new Point((byte) 0xE0);
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

    private PairOfCollidingSegments createPairOfCollidingSegments(final Segment... segments) {
        return new PairOfCollidingSegments(Collection.of(segments));
    }

    private void createPairOfCollingsSegmentsWithOneSegment() {
        Segment segment = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
        createPairOfCollidingSegments(segment);
    }

    /**
     * The constructor throws an IllegalArgumentException if the set contains only one segment.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void constructorThrowsIllegalArgumentExceptionIfOnlyOneSegmentProvided() {
        createPairOfCollingsSegmentsWithOneSegment();
    }

    /**
     * The message of the IllegalArgumentException thrown when only one segment is provided is correct.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfOnlyOneSegmentProvided() {
        try {
            createPairOfCollingsSegmentsWithOneSegment();
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(), "There should be two segments, but found 1.");
        }
    }

    /**
     * The constructor extracts the end point correctly from the two segments.
     */
    @Test
    public void constructorExtractsTheEndPointCorrectly() {
        Assert.assertEquals(createCorrectPairOfCollidingSegments().getEndPoint(), POINT_C4);
    }

    private PairOfCollidingSegments createCorrectPairOfCollidingSegments() {
        Segment s1 = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
        Segment s2 = new Segment(POINT_3C, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
        return createPairOfCollidingSegments(s1, s2);
    }

    private void createPairOfCollidingSegmentsWithDifferentEndPoints() {
        Segment s1 = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
        Segment s2 = new Segment(POINT_3C, POINT_02, 1, 1, TRUNCATED_SHA1_8_BITS);
        createPairOfCollidingSegments(s1, s2);
    }

    /**
     * The constructor throws an IllegalArgumentException if the two segments don't have the same end point.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void constructorThrowsIllegalArgumentExceptionIfEndPointsDiffer() {
        createPairOfCollidingSegmentsWithDifferentEndPoints();
    }

    /**
     * The message of the IllegalArgumentException thrown when the two segments have different end points is correct.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfSegmentsHaveDifferentEndPoints() {
        try {
            createPairOfCollidingSegmentsWithDifferentEndPoints();
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(), "The end points of the two segments aren't equal (0x02 ≠ 0xc4).");
        }
    }

    private void createPairOfCollidingSegmentsWithSameStartPoints() {
        Segment s1 = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
        Segment s2 = new Segment(POINT_02, POINT_C4, 2, 1, TRUNCATED_SHA1_8_BITS);
        createPairOfCollidingSegments(s1, s2);
    }

    /**
     * The constructor throws an IllegalArgumentException if the two segments have the same start point.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void constructorThrowsIllegalArgumentExceptionIfSegmentsHaveSameStartEndPoint() {
        createPairOfCollidingSegmentsWithSameStartPoints();
    }

    /**
     * The message of the IllegalArgumentException thrown when the two segments have the same start point is correct.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfSegmentsHaveSameStartEndPoint() {
        try {
            createPairOfCollidingSegmentsWithSameStartPoints();
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(), "The start points of the two segments are equal (0x02 = 0x02).");
        }
    }

    /**
     * The constructor extracts the hash function correctly from the two segments.
     */
    @Test
    public void constructorExtractsTheHashFunctionCorrectly() {
        Assert.assertEquals(createCorrectPairOfCollidingSegments().getHashFunction(), TRUNCATED_SHA1_8_BITS);
    }

    private void createPairOfCollidingSegmentsWithDifferentHashFunctions() {
        Segment s1 = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
        Segment s2 = new Segment(POINT_3C, POINT_C4, 1, 1, TRUNCATED_SHA256_8_BITS);
        createPairOfCollidingSegments(s1, s2);
    }

    /**
     * The constructor throws an IllegalArgumentException if the two segments don't have the same hash function.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void constructorThrowsIllegalArgumentExceptionIfHashFunctionsDiffer() {
        createPairOfCollidingSegmentsWithDifferentHashFunctions();
    }

    /**
     * The message of the IllegalArgumentException thrown when the two segments have different hash functions is
     * correct.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfSegmentsHaveDifferentHashFunctions() {
        try {
            createPairOfCollidingSegmentsWithDifferentHashFunctions();
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(),
                    "The hash functions of the two segments aren't equal (TRUNC(SHA-1, 8) ≠ TRUNC(SHA-256, 8)).");
        }
    }

    /**
     * The CollisionFinder finds the collision for a pair of colliding segments of order 0.
     */
    @Test
    public void findsCollisionCorrectlyForPairOfCollidingSegmentsOfOrder0() {
        PairOfCollidingSegments pair = createCorrectPairOfCollidingSegments();
        Collision collision = pair.resolveCollidingSegmentsToCollision();
        Assert.assertEquals(collision, new Collision(TRUNCATED_SHA1_8_BITS, POINT_02, POINT_3C));
    }

    /**
     * The CollisionFinder finds the collision for a pair of colliding segments of order 4.
     */
    @Test
    public void findsCollisionCorrectlyForPairOfCollidingSegmentsOfOrder4() {
        Segment s1 = new Segment(POINT_02, POINT_02, SEVEN, FOUR, TRUNCATED_SHA1_8_BITS);
        Segment s2 = new Segment(POINT_0A, POINT_02, TEN, FOUR, TRUNCATED_SHA1_8_BITS);
        PairOfCollidingSegments pair = createPairOfCollidingSegments(s1, s2);
        Collision collision = pair.resolveCollidingSegmentsToCollision();
        Assert.assertEquals(collision, new Collision(TRUNCATED_SHA1_8_BITS, POINT_02, POINT_3C));
    }

    /**
     * The CollisionFinder finds the collision for a pair of colliding segments for which it has to iterate in the loop.
     */
    @Test
    public void findsCollisionCorrectlyForPairOfCollidingSegmentsOfOrder2() {
        Segment s1 = new Segment(POINT_00, POINT_20, THREE, TWO, TRUNCATED_SHA1_5_BITS);
        Segment s2 = new Segment(POINT_20, POINT_20, FOUR, TWO, TRUNCATED_SHA1_5_BITS);
        PairOfCollidingSegments pair = createPairOfCollidingSegments(s1, s2);
        Collision collision = pair.resolveCollidingSegmentsToCollision();
        Assert.assertEquals(collision, new Collision(TRUNCATED_SHA1_5_BITS, POINT_E0, POINT_58));
    }
}

package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.filipvanlaenen.kolektoj.Collection;

/**
 * Unit tests on the class PairOfCollidingSegments.
 */
public class PairOfCollidingSegmentsTest {
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    private static final Point POINT_02 = new Point((byte) 0x02);
    private static final Point POINT_0A = new Point((byte) 0x0A);
    private static final Point POINT_3C = new Point((byte) 0x3C);
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
}

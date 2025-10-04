package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>CyclicSegment</code> class.
 */
public class CyclicSegmentTest {
    /**
     * Point 0x0300.
     */
    private static final Point POINT_0300 = new Point((byte) 0x03, (byte) 0x00);
    /**
     * Point 0x0C00.
     */
    private static final Point POINT_0C00 = new Point((byte) 0x0c, (byte) 0x00);
    /**
     * Point 0x3680.
     */
    private static final Point POINT_3680 = new Point((byte) 0x36, (byte) 0x80);
    /**
     * Point 0xDE00.
     */
    private static final Point POINT_DE00 = new Point((byte) 0xde, (byte) 0x00);
    /**
     * The hash function SHA-1 truncated to 9 bits.
     */
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_9_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 9);

    /**
     * Verifies that a collision can be found when the start is longer than the cycle.
     */
    @Test
    public void cyclicSegmentShouldFindCollisionWhenCycleIsLongerThanStart() {
        CyclicSegment cyclicSegment = new CyclicSegment(POINT_0C00, POINT_DE00, SHA1_TRUNCATED_TO_9_BITS);
        Collision collision = cyclicSegment.findCollision();
        assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_9_BITS, POINT_0C00, POINT_3680));
    }

    /**
     * Verifies that a collision can be found when the start and the cycle have the same length.
     */
    @Test
    public void cyclicSegmentShouldFindCollisionWhenStartAndCycleHaveSameLength() {
        CyclicSegment cyclicSegment = new CyclicSegment(POINT_0C00, POINT_3680, SHA1_TRUNCATED_TO_9_BITS);
        Collision collision = cyclicSegment.findCollision();
        assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_9_BITS, POINT_0C00, POINT_3680));
    }

    /**
     * Verifies that a collision can be found when the cycle is longer than the start.
     */
    @Test
    public void cyclicSegmentShouldFindCollisionWhenStartIsLongerThanCycle() {
        CyclicSegment cyclicSegment = new CyclicSegment(POINT_0300, POINT_3680, SHA1_TRUNCATED_TO_9_BITS);
        Collision collision = cyclicSegment.findCollision();
        assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_9_BITS, POINT_0C00, POINT_3680));
    }
}

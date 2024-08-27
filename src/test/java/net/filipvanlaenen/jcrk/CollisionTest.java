package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the Collision class.
 */
public class CollisionTest {
    /**
     * The truncated hash function SHA1, restricted to 8 bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    /**
     * The point 0x02.
     */
    private static final Point POINT_02 = new Point((byte) 0x02);
    /**
     * The point 0x3C.
     */
    private static final Point POINT_3C = new Point((byte) 0x3C);
    /**
     * The point 0xC4.
     */
    private static final Point POINT_C4 = new Point((byte) 0xC4);

    /**
     * Verifies that the order of the collision points doesn't change collision's equality.
     */
    @Test
    public void orderOfPointsDoesNotChangeCollisionEquality() {
        Collision collision1 = new Collision(TRUNCATED_SHA1, POINT_02, POINT_3C);
        Collision collision2 = new Collision(TRUNCATED_SHA1, POINT_3C, POINT_02);
        assertEquals(collision1, collision2);
    }

    /**
     * Verifies that an IllegalArgumentException is thrown when only one point is provided is correct.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfOnlyOnePointProvided() {
        IllegalArgumentException iae =
                assertThrows(IllegalArgumentException.class, () -> new Collision(TRUNCATED_SHA1, POINT_02));
        assertEquals("There should be at least two points, but found only 1.", iae.getMessage());
    }

    /**
     * Verifies that an IllegalArgumentException is thrown when the set contains a point that has a different hash value
     * than the first point.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfPointsHaveDifferentHashValues() {
        IllegalArgumentException iae =
                assertThrows(IllegalArgumentException.class, () -> new Collision(TRUNCATED_SHA1, POINT_02, POINT_C4));
        assertEquals("One of the points (0xc4) has a different hash value than the first point (0x02): 0xeb ≠ 0xc4.",
                iae.getMessage());
    }
}

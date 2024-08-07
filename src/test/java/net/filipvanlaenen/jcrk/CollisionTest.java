package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests on the Collision class.
 */
public class CollisionTest {
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    private static final Point POINT_02 = new Point((byte) 0x02);
    private static final Point POINT_3C = new Point((byte) 0x3c);
    private static final Point HASH_VALUE = new Point((byte) 0xC4);
    private Collision collision;

    /**
     * Creates a collision instance to run the unit tests on.
     */
    @BeforeMethod
    public void createCollision() {
        this.collision = new Collision(TRUNCATED_SHA1, POINT_02, POINT_3C);
    }

    /**
     * The constructor throws an IllegalArgumentException if the set contains only one point.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void constructorThrowsIllegalArgumentExceptionIfOnlyOnePointProvided() {
        new Collision(TRUNCATED_SHA1, POINT_02);
    }

    /**
     * The message of the IllegalArgumentException thrown when only one point is provided is correct.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfOnlyOnePointProvided() {
        try {
            new Collision(TRUNCATED_SHA1, POINT_02);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(), "There should be at least two points, but found only 1.");
        }
    }

    /**
     * The constructor throws an IllegalArgumentException if the set contains a point that has a different hash value
     * than the first point.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void constructorThrowsIllegalArgumentExceptionIfPointsHaveDifferentHashValues() {
        new Collision(TRUNCATED_SHA1, POINT_02, HASH_VALUE);
    }

    /**
     * The message of the IllegalArgumentException thrown when the set contains a point that has a different hash value
     * than the first point.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfPointsHaveDifferentHashValues() {
        try {
            new Collision(TRUNCATED_SHA1, POINT_02, HASH_VALUE);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(),
                    "One of the points (0xc4) has a different hash value than the first point (0x02): 0xeb ≠ 0xc4.");
        }
    }

    /**
     * The constructor sets the hash value correctly.
     */
    @Test
    public void constructorSetsHashValueCorrectly() {
        Assert.assertEquals(collision.getHashValue(), HASH_VALUE);
    }
}

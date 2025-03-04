package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Class testing the short constructor of the Segment class.
 */
public class SegmentShortConstructorTest {
    private static final byte START_POINT_BYTE = 0x40;
    private static final Point START_POINT = new Point(START_POINT_BYTE);
    private static final int ORDER = 1;
    private static final HashFunction HASH_FUNCTION = StandardHashFunction.SHA256;
    private Segment segment;

    /**
     * Creates a segment instance using the short constructor to run the tests on.
     */
    @BeforeMethod
    public void createSegmentUsingShortConstructor() {
        segment = new Segment(START_POINT, ORDER, HASH_FUNCTION);
    }

    /**
     * The short constructor should set the start point correctly.
     */
    @Test
    public void shortConstructorSetsStartPointCorrectly() {
        Assert.assertEquals(segment.getStartPoint(), START_POINT);
    }

    /**
     * The short constructor should throw an IllegalArgumentException if the start point is not a distinguished point of
     * the provided order.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void shortConstructorThrowsIllegalArgumentExceptionIfStartPointDoesNotMatchOrder() {
        new Segment(START_POINT, 2, HASH_FUNCTION);
    }

    /**
     * The message of the IllegalArgumentException should be correct when the start point's order doesn't match the
     * segment order.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfStartPointOrderDoesNotMatchSegmentOrder() {
        try {
            new Segment(START_POINT, 2, HASH_FUNCTION);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(), "The start point's order (1) is less than the provided order (2).");
        }
    }

    /**
     * The short constructor should set the end point equal to the start point.
     */
    @Test
    public void shortConstructorSetsEndPointEqualToStartPoint() {
        Assert.assertEquals(segment.getEndPoint(), START_POINT);
    }

    /**
     * The constructor should set the length to zero.
     */
    @Test
    public void constructorSetsLengthToZero() {
        Assert.assertEquals(segment.getLength(), 0);
    }

    /**
     * The constructor should set the order correctly.
     */
    @Test
    public void constructorSetsOrderCorrectly() {
        Assert.assertEquals(segment.getOrder(), ORDER);
    }

    /**
     * The short constructor should throw an IllegalArgumentException if the order is negative.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void shortConstructorThrowsIllegalArgumentExceptionIfOrderIsNegative() {
        new Segment(START_POINT, -1, HASH_FUNCTION);
    }

    /**
     * The message of the IllegalArgumentException should be correct when the order is negative.
     */
    @Test
    public void illegalArgumentExceptionMessageCorrectIfOrderIsNegative() {
        try {
            new Segment(START_POINT, -1, HASH_FUNCTION);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertEquals(iae.getMessage(), "The order (-1) is negative.");
        }
    }

    /**
     * The short constructor should set the hash function correctly.
     */
    @Test
    public void shortConstructorSetsHashFunctionCorrectly() {
        Assert.assertEquals(segment.getHashFunction(), HASH_FUNCTION);
    }

}

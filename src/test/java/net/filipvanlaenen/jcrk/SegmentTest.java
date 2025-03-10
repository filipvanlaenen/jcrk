package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests on the Segment class.
 */
public class SegmentTest {
    private static final int BYTE_0X20 = 0x20;
    private static final int BYTE_0X40 = 0x40;
    private static final int BYTE_0XFF = 0xff;
    private static final int BYTE_LENGTH = 32;
    private static final Point POINT_ZERO = new Point(new byte[BYTE_LENGTH]);
    private static final byte[] HASH_OF_POINT_ZERO = new byte[] {0x66, 0x68, 0x7a, (byte) 0xad, (byte) 0xf8, 0x62,
            (byte) 0xbd, 0x77, 0x6c, (byte) 0x8f, (byte) 0xc1, (byte) 0x8b, (byte) 0x8e, (byte) 0x9f, (byte) 0x8e, 0x20,
            0x08, (byte) 0x97, 0x14, (byte) 0x85, 0x6e, (byte) 0xe2, 0x33, (byte) 0xb3, (byte) 0x90, 0x2a, 0x59, 0x1d,
            0x0d, 0x5f, 0x29, 0x25};
    private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(HASH_OF_POINT_ZERO);
    private static final HashFunction HASH_FUNCTION = StandardHashFunction.SHA256;

    /**
     * A segment is not complete if its length is zero.
     */
    @Test
    public void zeroLengthSegmentIsNotComplete() {
        Segment newSegment = new Segment(POINT_ZERO, 1, HASH_FUNCTION);
        Assert.assertFalse(newSegment.isComplete());
    }

    /**
     * A segment is not complete if its end point isn't of the same order (or higher) as the segment.
     */
    @Test
    public void segmentNotCompleteIfEndPointNotOfSameOrder() {
        Segment incompleteSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0XFF), 1, 1, HASH_FUNCTION);
        Assert.assertFalse(incompleteSegment.isComplete());
    }

    /**
     * A segment is complete if its end point has the same order as the segment.
     */
    @Test
    public void segmentCompleteIfEndPointHasSameOrder() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, HASH_FUNCTION);
        Assert.assertTrue(completeSegment.isComplete());
    }

    /**
     * A segment is complete if its end point has a higher order as the segment.
     */
    @Test
    public void segmentCompleteIfEndPointHasHigherOrderThanSegment() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X20), 1, 1, HASH_FUNCTION);
        Assert.assertTrue(completeSegment.isComplete());
    }

    /**
     * A complete segment cannot be extended, and an IllegalStateException will be thrown if the extend method is
     * invoked on such a segment.
     */
    @Test(expectedExceptions = IllegalStateException.class)
    public void extendOnCompleteSegmentThrowsIllegalStateException() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, HASH_FUNCTION);
        completeSegment.extend();
    }

    /**
     * The message of the IllegalStateException thrown when extend is called on a complete segment must be correct.
     */
    @Test
    public void messageOfIllegalStateExceptionAfterExtendOnCompleteSegmentMustBeCorrect() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, HASH_FUNCTION);
        try {
            completeSegment.extend();
            Assert.fail();
        } catch (IllegalStateException ise) {
            Assert.assertEquals(ise.getMessage(), "A complete segment cannot be extended.");
        }
    }

    /**
     * An incomplete segment can be extended, and its length will be increased by one.
     */
    @Test
    public void extendIncrementsIncompleteSegmentLength() {
        Segment newSegment = new Segment(POINT_ZERO, 1, HASH_FUNCTION);
        newSegment.extend();
        Assert.assertEquals(newSegment.getLength(), 1);
    }

    /**
     * An incomplete segment can be extended, and its end point will be updated.
     */
    @Test
    public void extendUpdatesIncompleteSegmentEndPoint() {
        Segment newSegment = new Segment(POINT_ZERO, 1, HASH_FUNCTION);
        newSegment.extend();
        Assert.assertEquals(newSegment.getEndPoint(), FIRST_POINT_AFTER_POINT_ZERO);
    }
}

package net.filipvanlaenen.jcrk;

import static net.filipvanlaenen.jcrk.StandardHashFunction.SHA256;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the Segment class.
 */
public class SegmentTest {
    /**
     * The magic number thirty two.
     */
    private static final int THIRTY_TWO = 32;
    /**
     * The byte 0x20.
     */
    private static final int BYTE_0X20 = 0x20;
    /**
     * The byte 0x40.
     */
    private static final int BYTE_0X40 = 0x40;
    /**
     * The byte 0xFF.
     */
    private static final int BYTE_0XFF = 0xff;
    /**
     * Point zero.
     */
    private static final Point POINT_ZERO = new Point(new byte[THIRTY_TWO]);
    /**
     * The hash value of point zero.
     */
    private static final byte[] HASH_OF_POINT_ZERO = new byte[] {0x66, 0x68, 0x7a, (byte) 0xad, (byte) 0xf8, 0x62,
            (byte) 0xbd, 0x77, 0x6c, (byte) 0x8f, (byte) 0xc1, (byte) 0x8b, (byte) 0x8e, (byte) 0x9f, (byte) 0x8e, 0x20,
            0x08, (byte) 0x97, 0x14, (byte) 0x85, 0x6e, (byte) 0xe2, 0x33, (byte) 0xb3, (byte) 0x90, 0x2a, 0x59, 0x1d,
            0x0d, 0x5f, 0x29, 0x25};
    /**
     * The first point after point zero.
     */
    private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(HASH_OF_POINT_ZERO);

    /**
     * A segment is not complete if its length is zero.
     */
    @Test
    public void zeroLengthSegmentIsNotComplete() {
        Segment newSegment = new Segment(POINT_ZERO, 1, SHA256);
        assertFalse(newSegment.isComplete());
    }

    /**
     * A segment is not complete if its end point isn't of the same order (or higher) as the segment.
     */
    @Test
    public void segmentNotCompleteIfEndPointNotOfSameOrder() {
        Segment incompleteSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0XFF), 1, 1, SHA256);
        assertFalse(incompleteSegment.isComplete());
    }

    /**
     * A segment is complete if its end point has the same order as the segment.
     */
    @Test
    public void segmentCompleteIfEndPointHasSameOrder() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, SHA256);
        assertTrue(completeSegment.isComplete());
    }

    /**
     * A segment is complete if its end point has a higher order as the segment.
     */
    @Test
    public void segmentCompleteIfEndPointHasHigherOrderThanSegment() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X20), 1, 1, SHA256);
        assertTrue(completeSegment.isComplete());
    }

    /**
     * A complete segment cannot be extended, and an IllegalStateException will be thrown if the extend method is
     * invoked on such a segment.
     */
    @Test
    public void messageOfIllegalStateExceptionAfterExtendOnCompleteSegmentMustBeCorrect() {
        Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, SHA256);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> completeSegment.extend());
        assertEquals("A complete segment cannot be extended.", exception.getMessage());
    }

    /**
     * An incomplete segment can be extended, and its length will be increased by one.
     */
    @Test
    public void extendIncrementsIncompleteSegmentLength() {
        Segment newSegment = new Segment(POINT_ZERO, 1, SHA256);
        newSegment.extend();
        assertEquals(newSegment.getLength(), 1);
    }

    /**
     * An incomplete segment can be extended, and its end point will be updated.
     */
    @Test
    public void extendUpdatesIncompleteSegmentEndPoint() {
        Segment newSegment = new Segment(POINT_ZERO, 1, SHA256);
        newSegment.extend();
        assertEquals(newSegment.getEndPoint(), FIRST_POINT_AFTER_POINT_ZERO);
    }
}

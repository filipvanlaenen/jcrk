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
    private static final byte BYTE_0X20 = 0x20;
    /**
     * The byte 0x40.
     */
    private static final byte BYTE_0X40 = 0x40;
    /**
     * The byte 0xFF.
     */
    private static final int BYTE_0XFF = 0xff;
    /**
     * Point zero.
     */
    private static final Point POINT_ZERO = new Point(new byte[THIRTY_TWO]);
    /**
     * Point 0x00.
     */
    private static final Point POINT_00 = new Point((byte) 0);
    /**
     * Point 0x01.
     */
    private static final Point POINT_01 = new Point((byte) 1);
    /**
     * Point 0x20.
     */
    private static final Point POINT_20 = new Point(BYTE_0X20);
    /**
     * Point 0x40.
     */
    private static final Point POINT_40 = new Point(BYTE_0X40);
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

    /**
     * Returns a segment instance using the short constructor to run the tests on.
     *
     * @return A segment instance using the short constructor to run the tests on.
     */
    public Segment createSegmentUsingShortConstructor() {
        return new Segment(POINT_40, 1, SHA256);
    }

    /**
     * The short constructor should set the start point correctly.
     */
    @Test
    public void shortConstructorSetsStartPointCorrectly() {
        assertEquals(createSegmentUsingShortConstructor().getStartPoint(), POINT_40);
    }

    /**
     * The short constructor should throw an IllegalArgumentException if the start point is not a distinguished point of
     * the provided order.
     */
    @Test
    public void shortConstructorThrowsIllegalArgumentExceptionIfStartPointDoesNotMatchOrder() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Segment(POINT_40, 2, SHA256));
        assertEquals("The start point's order (1) is less than the provided order (2).", exception.getMessage());
    }

    /**
     * The short constructor should set the end point equal to the start point.
     */
    @Test
    public void shortConstructorSetsEndPointEqualToStartPoint() {
        assertEquals(createSegmentUsingShortConstructor().getEndPoint(), POINT_40);
    }

    /**
     * The constructor should set the length to zero.
     */
    @Test
    public void constructorSetsLengthToZero() {
        assertEquals(createSegmentUsingShortConstructor().getLength(), 0);
    }

    /**
     * The constructor should set the order correctly.
     */
    @Test
    public void constructorSetsOrderCorrectly() {
        assertEquals(createSegmentUsingShortConstructor().getOrder(), 1);
    }

    /**
     * The short constructor should throw an IllegalArgumentException if the order is negative.
     */
    @Test
    public void shortConstructorThrowsIllegalArgumentExceptionIfOrderIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Segment(POINT_40, -1, SHA256));
        assertEquals("The order (-1) is negative.", exception.getMessage());

    }

    /**
     * The short constructor should set the hash function correctly.
     */
    @Test
    public void shortConstructorSetsHashFunctionCorrectly() {
        assertEquals(createSegmentUsingShortConstructor().getHashFunction(), SHA256);
    }

    /**
     * Creates a segment instance using the long constructor to run the tests on.
     *
     * @return A segment instance using the long constructor to run the tests on.
     */
    public Segment createSegmentUsingLongConstructor() {
        return new Segment(POINT_40, POINT_20, 2, 1, SHA256);
    }

    /**
     * The long constructor should set the start point correctly.
     */
    @Test
    public void longConstructorSetsStartPointCorrectly() {
        assertEquals(createSegmentUsingLongConstructor().getStartPoint(), POINT_40);
    }

    /**
     * The long constructor should throw an IllegalArgumentException if the start point is not a distinguished point of
     * the provided order.
     */
    @Test
    public void longConstructorThrowsIllegalArgumentExceptionIfStartPointDoesNotMatchOrder() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Segment(POINT_40, POINT_20, 2, 2, SHA256));
        assertEquals("The start point's order (1) is less than the provided order (2).", exception.getMessage());
    }

    /**
     * The long constructor should set the end point correctly.
     */
    @Test
    public void longConstructorSetsEndPointCorrectly() {
        assertEquals(createSegmentUsingLongConstructor().getEndPoint(), POINT_20);
    }

    /**
     * The long constructor should set the length correctly.
     */
    @Test
    public void longConstructorSetsLengthCorrectly() {
        assertEquals(createSegmentUsingLongConstructor().getLength(), 2);
    }

    /**
     * The long constructor should throw an IllegalArgumentException if the length is negative.
     */
    @Test
    public void longConstructorThrowsIllegalArgumentExceptionIfLengthIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Segment(POINT_20, POINT_20, -1, 2, SHA256));
        assertEquals("The length (-1) is negative.", exception.getMessage());
    }

    /**
     * The long constructor should set the hash function correctly.
     */
    @Test
    public void longConstructorSetsHashFunctionCorrectly() {
        assertEquals(createSegmentUsingLongConstructor().getHashFunction(), SHA256);
    }

    /**
     * The long constructor should set the order correctly.
     */
    @Test
    public void longConstructorSetsOrderCorrectly() {
        assertEquals(createSegmentUsingLongConstructor().getOrder(), 1);
    }

    /**
     * The long constructor should throw an IllegalArgumentException if the order is negative.
     */
    @Test
    public void longConstructorThrowsIllegalArgumentExceptionIfOrderIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Segment(POINT_40, POINT_20, 2, -1, SHA256));
        assertEquals("The order (-1) is negative.", exception.getMessage());
    }

    /**
     * Creates a segment to run the unit tests on.
     *
     * @return A segment to run the unit tests on.
     */
    public Segment createSegment() {
        return new Segment(POINT_00, POINT_01, 1, 1, SHA256);
    }

    /**
     * A segment is not equal with a point (as an example of an object of another type).
     */
    @Test
    public void segmentNotEqualWithPoint() {
        assertFalse(createSegment().equals(POINT_00));
    }

    /**
     * A segment is equal with itself.
     */
    @Test
    public void segmentIsEqualWithItself() {
        Segment segment = createSegment();
        assertEquals(segment, segment);
    }

    /**
     * Two segments with the same start and end point, length, order and hash function are equal.
     */
    @Test
    public void equalSegmentsAreEqual() {
        Segment otherSegment = new Segment(POINT_00, POINT_01, 1, 1, StandardHashFunction.SHA256);
        assertEquals(createSegment(), otherSegment);
    }

    /**
     * Equal segments have the same hashCode.
     */
    @Test
    public void equalSegmentsHaveSameHashCode() {
        Segment otherSegment = new Segment(POINT_00, POINT_01, 1, 1, StandardHashFunction.SHA256);
        assertEquals(createSegment().hashCode(), otherSegment.hashCode());
    }

    /**
     * Two segments are not equal if they have different start points.
     */
    @Test
    public void segmentsNotEqualIfStartPointDifferent() {
        Segment otherSegment = new Segment(POINT_01, POINT_01, 1, 1, StandardHashFunction.SHA256);
        assertFalse(createSegment().equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different end points.
     */
    @Test
    public void segmentsNotEqualIfEndPointDifferent() {
        Segment otherSegment = new Segment(POINT_00, POINT_00, 1, 1, StandardHashFunction.SHA256);
        assertFalse(createSegment().equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different lengths.
     */
    @Test
    public void segmentsNotEqualIfLengthDifferent() {
        Segment otherSegment = new Segment(POINT_00, POINT_01, 2, 1, StandardHashFunction.SHA256);
        assertFalse(createSegment().equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different orders.
     */
    @Test
    public void segmentsNotEqualIfOrderDifferent() {
        Segment otherSegment = new Segment(POINT_00, POINT_01, 1, 2, StandardHashFunction.SHA256);
        assertFalse(createSegment().equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different hash functions.
     */
    @Test
    public void segmentsNotEqualIfHashFunctionDifferent() {
        Segment otherSegment = new Segment(POINT_00, POINT_01, 1, 1, StandardHashFunction.SHA1);
        assertFalse(createSegment().equals(otherSegment));
    }
}

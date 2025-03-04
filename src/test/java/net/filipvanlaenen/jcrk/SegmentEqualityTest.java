package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests on the equality of segments.
 */
public class SegmentEqualityTest {
    private static final byte BYTE_0X01 = (byte) 0x01;
    private static final byte BYTE_0X00 = (byte) 0x00;
    private Segment segment;

    /**
     * Creates a segment to run the unit tests on.
     */
    @BeforeMethod
    public void createSegment() {
        segment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1, StandardHashFunction.SHA256);
    }

    /**
     * A segment is not equal with a point (as an example of an object of another type).
     */
    @Test
    public void segmentNotEqualWithPoint() {
        Assert.assertFalse(segment.equals(new Point(BYTE_0X00)));
    }

    /**
     * A segment is equal with itself.
     */
    @Test
    public void segmentIsEqualWithItself() {
        Assert.assertEquals(segment, segment);
    }

    /**
     * Two segments with the same start and end point, length, order and hash function are equal.
     */
    @Test
    public void equalSegmentsAreEqual() {
        Segment otherSegment =
                new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1, StandardHashFunction.SHA256);
        Assert.assertEquals(segment, otherSegment);
    }

    /**
     * Equal segments have the same hashCode.
     */
    @Test
    public void equalSegmentsHaveSameHashCode() {
        Segment otherSegment =
                new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1, StandardHashFunction.SHA256);
        Assert.assertEquals(segment.hashCode(), otherSegment.hashCode());
    }

    /**
     * Two segments are not equal if they have different start points.
     */
    @Test
    public void segmentsNotEqualIfStartPointDifferent() {
        Segment otherSegment =
                new Segment(new Point(BYTE_0X01), new Point(BYTE_0X01), 1, 1, StandardHashFunction.SHA256);
        Assert.assertFalse(segment.equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different end points.
     */
    @Test
    public void segmentsNotEqualIfEndPointDifferent() {
        Segment otherSegment =
                new Segment(new Point(BYTE_0X00), new Point(BYTE_0X00), 1, 1, StandardHashFunction.SHA256);
        Assert.assertFalse(segment.equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different lengths.
     */
    @Test
    public void segmentsNotEqualIfLengthDifferent() {
        Segment otherSegment =
                new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 2, 1, StandardHashFunction.SHA256);
        Assert.assertFalse(segment.equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different orders.
     */
    @Test
    public void segmentsNotEqualIfOrderDifferent() {
        Segment otherSegment =
                new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 2, StandardHashFunction.SHA256);
        Assert.assertFalse(segment.equals(otherSegment));
    }

    /**
     * Two segments are not equal if they have different hash functions.
     */
    @Test
    public void segmentsNotEqualIfHashFunctionDifferent() {
        Segment otherSegment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1, StandardHashFunction.SHA1);
        Assert.assertFalse(segment.equals(otherSegment));
    }
}

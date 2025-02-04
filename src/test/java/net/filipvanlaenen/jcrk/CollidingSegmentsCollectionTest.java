package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;

/**
 * Unit tests on the class <code>CollidingSegmentsCollection</code>.
 */
public class CollidingSegmentsCollectionTest {
    /**
     * The hash function SHA-1 truncated to 8 bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1_8_BITS =
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
     * The segment going from point 0x02 to point 0xC4.
     */
    private static final Segment SEGMENT_02_C4 = new Segment(POINT_02, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);
    /**
     * The segment going from point 0x3C to point 0xC4.
     */
    private static final Segment SEGMENT_3C_C4 = new Segment(POINT_3C, POINT_C4, 1, 1, TRUNCATED_SHA1_8_BITS);

    /**
     * Verifies that the constructor throws an IllegalArgumentException if only one segment is provided.
     */
    @Test
    public void constructorShouldThrowIllegalArgumentExceptionIfOnlyOneSegmentProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4)));
        assertEquals("There should be at least two segments provided to the constructor, but found only 1.",
                exception.getMessage());
    }

    /**
     * Verifies that the end point of two segments is extracted correctly.
     */
    @Test
    public void getEndPointShouldExtractedTheEndPointCorrectly() {
        CollidingSegmentsCollection collidingSegments =
                new CollidingSegmentsCollection(Collection.of(SEGMENT_02_C4, SEGMENT_3C_C4));
        assertEquals(POINT_C4, collidingSegments.getEndPoint());
    }
}

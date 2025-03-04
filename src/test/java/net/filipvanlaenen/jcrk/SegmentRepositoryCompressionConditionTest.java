package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the SegmentRepositoryCompressionCondition enumeration.
 */
public class SegmentRepositoryCompressionConditionTest {
    /**
     * The SHA-1 hash algorithm truncated to eight bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_SHA1 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    /**
     * Byte 0x00.
     */
    private static final byte BYTE_0X00 = (byte) 0x00;
    /**
     * Byte 0x01.
     */
    private static final byte BYTE_0X01 = (byte) 0x01;
    /**
     * Byte 0x02.
     */
    private static final byte BYTE_0X02 = (byte) 0x02;

    /**
     * An empty repository should not be compressed.
     */
    @Test
    public void emptyRepositoryShouldNotBeCompressed() {
        SegmentRepository repository = new InMemorySegmentRepository(TRUNCATED_SHA1);
        assertFalse(SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo.evaluate(repository));
    }

    /**
     * At order 0, a repository with one segment should not be compressed.
     */
    @Test
    public void atOrderZeroARepositoryWithOneSegmentShouldNotBeCompressed() {
        SegmentRepository repository = new InMemorySegmentRepository(TRUNCATED_SHA1);
        repository.add(new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 0, TRUNCATED_SHA1));
        assertFalse(SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo.evaluate(repository));
    }

    /**
     * At order 0, a repository with two segments should be compressed.
     */
    @Test
    public void atOrderZeroARepositoryWithTwoSegmentsShouldBeCompressed() {
        SegmentRepository repository = new InMemorySegmentRepository(TRUNCATED_SHA1);
        repository.add(new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 0, TRUNCATED_SHA1));
        repository.add(new Segment(new Point(BYTE_0X01), new Point(BYTE_0X02), 1, 0, TRUNCATED_SHA1));
        assertTrue(SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo.evaluate(repository));
    }
}

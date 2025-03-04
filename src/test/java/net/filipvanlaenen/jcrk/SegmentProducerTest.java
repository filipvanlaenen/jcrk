package net.filipvanlaenen.jcrk;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SegmentProducer</code> enumeration.
 */
public class SegmentProducerTest {
    /**
     * Point zero.
     */
    private static final Point ZERO_POINT = new Point((byte) 0x00);
    /**
     * The SHA-1 hash algorithm truncated to one bit.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_1_SHA1 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 1);
    /**
     * The SHA-1 hash algorithm truncated to eight bits.
     */
    private static final TruncatedStandardHashFunction TRUNCATED_8_SHA1 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);

    /**
     * Create an empty segment repository for the tests.
     *
     * @return An empty in-memory segment repository.
     */
    public SegmentRepository createEmptyInMemorySegmentRepository() {
        return new InMemorySegmentRepository(TRUNCATED_8_SHA1);
    }

    /**
     * If the segment repository is empty, produce the zero point as the new start point.
     */
    @Test
    public void shouldProduceZeroPointIfSegmentRepositoryIsEmpty() {
        SegmentRepository repository = createEmptyInMemorySegmentRepository();
        Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
        Assert.assertEquals(ZERO_POINT, point);
    }

    /**
     * If the segment repository contains a segment with the zero point as its start point, produce the segment's end
     * point as the new start point.
     */
    @Test
    public void shouldProduceNewPointIfSegmentRepositoryContainsZeroPointSegment() {
        SegmentRepository repository = createEmptyInMemorySegmentRepository();
        Segment segment = new Segment(ZERO_POINT, 0, TRUNCATED_8_SHA1);
        segment.extend();
        Point expectedNewStartPoint = segment.getEndPoint();
        repository.add(segment);
        Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
        Assert.assertEquals(expectedNewStartPoint, point);
    }

    /**
     * If the segment repository contains two first two segments of the zero point segment segment, produce the second
     * segment's end point as the new start point.
     */
    @Test
    public void shouldProduceNewPointIfSegmentRepositoryContainsZeroPointSegmentChain() {
        SegmentRepository repository = createEmptyInMemorySegmentRepository();
        Segment segment1 = new Segment(ZERO_POINT, 0, TRUNCATED_8_SHA1);
        segment1.extend();
        repository.add(segment1);
        Segment segment2 = new Segment(segment1.getEndPoint(), 0, TRUNCATED_8_SHA1);
        segment2.extend();
        repository.add(segment2);
        Point expectedNewStartPoint = segment2.getEndPoint();
        Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
        Assert.assertEquals(expectedNewStartPoint, point);
    }

    /**
     * If the segment repository doesn't contain a segment with the zero point as its start point, produce the zero
     * point as the new start point.
     */
    @Test
    public void shouldProduceZeroPointIfSegmentRepositoryContainsOtherSegmentsThanWithZeroPoint() {
        SegmentRepository repository = createEmptyInMemorySegmentRepository();
        Segment segment = new Segment(new Point((byte) 0x01), 0, TRUNCATED_8_SHA1);
        segment.extend();
        repository.add(segment);
        Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
        Assert.assertEquals(ZERO_POINT, point);
    }

    /**
     * If point zero is part of a loop, return null.
     */
    @Test
    public void shouldReturnNullIfPointZeroIsPartOfALoop() {
        SegmentRepository repositoryForOneBit = new InMemorySegmentRepository(TRUNCATED_1_SHA1);
        repositoryForOneBit.add(new Segment(ZERO_POINT, ZERO_POINT, 1, 0, TRUNCATED_1_SHA1));
        Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repositoryForOneBit);
        Assert.assertNull(point);
    }
}

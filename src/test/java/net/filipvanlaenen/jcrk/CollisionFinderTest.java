package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * An integration test on the CollisionFinder class.
 */
public class CollisionFinderTest {
    /**
     * The hash function SHA-1 truncated to 1 bit.
     */
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_1_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 1);
    /**
     * The hash function SHA-1 truncated to 8 bits.
     */
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    /**
     * Point 0x02.
     */
    private static final Point POINT_02 = new Point((byte) 0x02);
    /**
     * Point 0x4C.
     */
    private static final Point POINT_3C = new Point((byte) 0x3c);
    /**
     * The magic number four.
     */
    private static final int FOUR = 4;

    /**
     * Verifies that if the segment producer can't produce a collision, null is returned.
     */
    @Test
    public void collisionFinderReturnsNullIfSegmentProducerCanNotProduceACollision() {
        SegmentRepository repository = new InMemorySegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        CollisionFinder finder =
                new CollisionFinder(repository, OrderedCollection.of(SegmentProducer.ZeroPointSegmentChainExtension),
                        SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        assertNull(finder.findCollision());
    }

    /**
     * Verifies that the collision found is correct.
     */
    @Test
    public void collisionFinderMustFindCorrectCollision() {
        SegmentRepository segmentRepository = new InMemorySegmentRepository(SHA1_TRUNCATED_TO_8_BITS);
        CollisionFinder finder = new CollisionFinder(segmentRepository,
                OrderedCollection.of(SegmentProducer.ZeroPointSegmentChainExtension),
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        Collision collision = finder.findCollision();
        assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_8_BITS, POINT_02, POINT_3C));
        assertEquals(segmentRepository.getOrder(), FOUR);
    }
}

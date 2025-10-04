package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

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
     * The hash function SHA-1 truncated to 9 bits.
     */
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_9_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 9);
    /**
     * The hash function SHA-1 truncated to 8 bits.
     */
    private static final TruncatedStandardHashFunction SHA224_TRUNCATED_TO_3_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA224, 3);
    /**
     * Point 0x02.
     */
    private static final Point POINT_02 = new Point((byte) 0x02);
    /**
     * Point 0x4C.
     */
    private static final Point POINT_3C = new Point((byte) 0x3c);
    /**
     * Point 0x0C00.
     */
    private static final Point POINT_0C00 = new Point((byte) 0x0c, (byte) 0x00);
    /**
     * Point 0x3680.
     */
    private static final Point POINT_3680 = new Point((byte) 0x36, (byte) 0x80);

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
        CollisionFinder finder = new CollisionFinder(repository,
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
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        Collision collision = finder.findCollision();
        assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_8_BITS, POINT_02, POINT_3C));
        assertEquals(segmentRepository.getOrder(), FOUR);
    }

    /**
     * Verifies correct reporting when a collision is found in a cyclic segment.
     */
    @Test
    public void shouldDetectACollisionFromACyclicSegment() {
        SegmentRepository segmentRepository = new InMemorySegmentRepository(SHA1_TRUNCATED_TO_9_BITS);
        CollisionFinder finder = new CollisionFinder(segmentRepository,
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        Collision collision = finder.findCollision();
        assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_9_BITS, POINT_0C00, POINT_3680));
        assertEquals(segmentRepository.getOrder(), FOUR);
    }

    /**
     * Verifies correct reporting when no collision can be found due to a cyclic result space for the hash function.
     */
    @Test
    public void shouldDetectACyclicResultSpace() {
        ByteArrayOutputStream outputStream = LaconicConfigurator.resetLaconicOutputStream();
        SegmentRepository segmentRepository = new InMemorySegmentRepository(SHA224_TRUNCATED_TO_3_BITS);
        CollisionFinder finder = new CollisionFinder(segmentRepository,
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        finder.findCollision();
        assertTrue(
                outputStream.toString().contains("No collision found -- the hash function has a cyclic result space."));
    }
}

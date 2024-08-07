package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * An integration test on the CollisionFinder class.
 */
public class CollisionFinderTest {
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_1_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 1);
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 8);
    private static final Point POINT_02 = new Point((byte) 0x02);
    private static final Point POINT_3C = new Point((byte) 0x3c);
    private static final int SEGMENT_REPOSITORY_ORDER_AT_COLLISION = 4;
    private SegmentRepository segmentRepository;
    private Collision collision;

    /**
     * Runs the CollisionFinder for SHA-1 truncated to 8 bits.
     */
    @BeforeTest
    public void findCollision() {
        segmentRepository = new InMemorySegmentRepository(SHA1_TRUNCATED_TO_8_BITS);
        CollisionFinder finder = new CollisionFinder(segmentRepository, SegmentProducer.ZeroPointSegmentChainExtension,
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        collision = finder.findCollision();
    }

    /**
     * Verifies that if the segment producer can't produce a collision, null is returned.
     */
    @Test
    public void collisionFinderReturnsNullIfSegmentProducerCanNotProduceACollision() {
        SegmentRepository repository = new InMemorySegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        CollisionFinder finder = new CollisionFinder(repository, SegmentProducer.ZeroPointSegmentChainExtension,
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        Assert.assertNull(finder.findCollision());
    }

    /**
     * Verifies that the collision found is correct.
     */
    @Test
    public void collisionFinderMustFindCorrectCollision() {
        Assert.assertEquals(collision, new Collision(SHA1_TRUNCATED_TO_8_BITS, POINT_02, POINT_3C));
    }

    /**
     * Verifies that the repository was compressed while searching for a collision.
     */
    @Test
    public void segmentRepositoryWasCompressed() {
        Assert.assertEquals(segmentRepository.getOrder(), SEGMENT_REPOSITORY_ORDER_AT_COLLISION);
    }
}

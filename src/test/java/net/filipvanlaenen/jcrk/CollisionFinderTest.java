package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * An integration test on the CollisionFinder class.
 */
public class CollisionFinderTest {
	private static final TruncatedStandardHashFunction TRUNCATED_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 8);
	private InMemorySegmentRepository segmentRepository;
	private Collision collision;

	/**
	 * Runs the CollisionFinder for SHA-1 truncated to 8 bits.
	 */
	@BeforeTest
	public void findCollision() {
		segmentRepository = new InMemorySegmentRepository(TRUNCATED_SHA1);
		CollisionFinder finder = new CollisionFinder(segmentRepository,
				SegmentProducer.ZeroPointSegmentChainExtension, SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
		collision = finder.findCollision();
	}

	/**
	 * Verifies that the collision found is correct.
	 */
	@Test
	public void collisionFinderMustFindCorrectCollision() {
		Assert.assertEquals(collision, new Collision(TRUNCATED_SHA1, new Point((byte) 0x01), new Point((byte) 0x02)));
	}
	
	/**
	 * Verifies that the repository was compressed while searching for a collision.
	 */
	@Test
	public void segmentRepositoryWasCompressed() {
		Assert.assertEquals(segmentRepository.getOrder(), 2);
	}
}

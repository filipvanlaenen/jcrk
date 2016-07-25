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
	private Collision collision;

	/**
	 * Runs the CollisionFinder for SHA-1 truncated to 8 bits.
	 */
	@BeforeTest(enabled = false)
	public void findCollision() {
		CollisionFinder finder = new CollisionFinder(new InMemorySegmentRepository(TRUNCATED_SHA1),
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
}

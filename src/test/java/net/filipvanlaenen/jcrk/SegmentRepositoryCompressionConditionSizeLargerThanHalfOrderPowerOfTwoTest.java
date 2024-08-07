package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests on the SegmentRepositoryCompressionCondition
 * SizeLargerThanHalfOrderPowerOfTwo.
 */
public class SegmentRepositoryCompressionConditionSizeLargerThanHalfOrderPowerOfTwoTest {
	private static final TruncatedStandardHashFunction TRUNCATED_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 8);
	private static final byte BYTE_0X00 = (byte) 0x00;
	private static final byte BYTE_0X01 = (byte) 0x01;
	private static final byte BYTE_0X02 = (byte) 0x02;

	/**
	 * An empty repository should not be compressed.
	 */
	@Test
	public void emptyRepositoryShouldNotBeCompressed() {
		SegmentRepository repository = new InMemorySegmentRepository(
				TRUNCATED_SHA1);
		Assert.assertFalse(SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo
				.evaluate(repository));
	}

	/**
	 * At order 0, a repository with one segment should not be compressed.
	 */
	@Test
	public void atOrderZeroARepositoryWithOneSegmentShouldNotBeCompressed() {
		SegmentRepository repository = new InMemorySegmentRepository(
				TRUNCATED_SHA1);
		repository.add(new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01),
				1, 0, TRUNCATED_SHA1));
		Assert.assertFalse(SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo
				.evaluate(repository));
	}

	/**
	 * At order 0, a repository with two segments should be compressed.
	 */
	@Test
	public void atOrderZeroARepositoryWithTwoSegmentsShouldBeCompressed() {
		SegmentRepository repository = new InMemorySegmentRepository(
				TRUNCATED_SHA1);
		repository.add(new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01),
				1, 0, TRUNCATED_SHA1));
		repository.add(new Segment(new Point(BYTE_0X01), new Point(BYTE_0X02),
				1, 0, TRUNCATED_SHA1));
		Assert.assertTrue(SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo
				.evaluate(repository));
	}
}

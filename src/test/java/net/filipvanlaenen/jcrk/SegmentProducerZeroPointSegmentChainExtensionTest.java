package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests on the SegmentProducer ZeroPointSegmentChainExtension.
 */
public class SegmentProducerZeroPointSegmentChainExtensionTest {
	private static final Point ZERO_POINT = new Point((byte) 0x00);
	private static final TruncatedStandardHashFunction TRUNCATED_1_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 1);
	private static final TruncatedStandardHashFunction TRUNCATED_8_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 8);
	private static final TruncatedStandardHashFunction TRUNCATED_9_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 9);
	private SegmentRepository repository;

	/**
	 * Create an empty segment repository for the tests.
	 */
	@BeforeMethod
	public void createEmptyInMemorySegmentRepository() {
		repository = new InMemorySegmentRepository(TRUNCATED_8_SHA1);
	}

	/**
	 * If the segment repository is empty, produce the zero point as the new
	 * start point.
	 */
	@Test
	public void produceZeroPointIfSegmentRepositoryIsEmpty() {
		Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
		Assert.assertEquals(point, ZERO_POINT);
	}

	/**
	 * If the segment repository is empty, produce the zero point as the new
	 * start point, but two byte wide if the truncation is 9 bits.
	 */
	@Test
	public void produceTwoByteZeroPointIfTruncationIsNineBits() {
		Point twoByteZeroPoint = new Point((byte) 0x00, (byte) 0x00);
		SegmentRepository repositoryForNineBits = new InMemorySegmentRepository(TRUNCATED_9_SHA1);
		Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repositoryForNineBits);
		Assert.assertEquals(point, twoByteZeroPoint);
	}

	/**
	 * If the segment repository doesn't contain a segment with the zero point
	 * as its start point, produce the zero point as the new start point.
	 */
	@Test
	public void produceZeroPointIfSegmentRepositoryDoesNotContainZeroPointSegment() {
		Segment segment = new Segment(new Point((byte) 0x01), 0, TRUNCATED_8_SHA1);
		segment.extend();
		repository.add(segment);
		Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
		Assert.assertEquals(point, ZERO_POINT);
	}

	/**
	 * If the segment repository contains a segment with the zero point as its
	 * start point, produce the segment's end point as the new start point.
	 */
	@Test
	public void produceNewPointIfSegmentRepositoryContainsZeroPointSegment() {
		Segment segment = new Segment(ZERO_POINT, 0, TRUNCATED_8_SHA1);
		segment.extend();
		Point expectedNewStartPoint = segment.getEndPoint();
		repository.add(segment);
		Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
		Assert.assertEquals(point, expectedNewStartPoint);
	}

	/**
	 * If the segment repository contains two first two segments of the zero
	 * point segment segment, produce the second segment's end point as the new
	 * start point.
	 */
	@Test
	public void produceNewPointIfSegmentRepositoryContainsZeroPointSegmentChain() {
		Segment segment1 = new Segment(ZERO_POINT, 0, TRUNCATED_8_SHA1);
		segment1.extend();
		repository.add(segment1);
		Segment segment2 = new Segment(segment1.getEndPoint(), 0, TRUNCATED_8_SHA1);
		segment2.extend();
		repository.add(segment2);
		Point expectedNewStartPoint = segment2.getEndPoint();
		Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repository);
		Assert.assertEquals(point, expectedNewStartPoint);
	}
	
	/**
	 * If point zero is part of a loop, return null.
	 */
	@Test
	public void returnNullIfPointZeroIsPartOfALoop() {
		SegmentRepository repositoryForOneBit = new InMemorySegmentRepository(TRUNCATED_1_SHA1);
		repositoryForOneBit.add(new Segment(ZERO_POINT, ZERO_POINT, 1, 0, TRUNCATED_1_SHA1));
		Point point = SegmentProducer.ZeroPointSegmentChainExtension.findNewStartPoint(repositoryForOneBit);
		Assert.assertNull(point);
	}
}

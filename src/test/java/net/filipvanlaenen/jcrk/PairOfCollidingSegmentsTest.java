package net.filipvanlaenen.jcrk;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests on the class PairOfCollidingSegments.
 */
public class PairOfCollidingSegmentsTest {
	private static final TruncatedStandardHashFunction TRUNCATED_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 8);
	private static final TruncatedStandardHashFunction TRUNCATED_SHA256 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA256, 8);
	private static final Point START_POINT_02 = new Point((byte) 0x02);
	private static final Point START_POINT_3C = new Point((byte) 0x3c);
	private static final Point END_POINT = new Point((byte) 0xC4);

	private PairOfCollidingSegments createPairOfCollidingSegments(Segment... segments) {
		Set<Segment> segmentSet = new HashSet<Segment>();
		for (Segment segment : segments) {
			segmentSet.add(segment);
		}
		return new PairOfCollidingSegments(segmentSet);
	}

	private void createPairOfCollingsSegmentsWithOneSegment() {
		Segment segment = new Segment(START_POINT_02, END_POINT, 1, 1, TRUNCATED_SHA1);
		createPairOfCollidingSegments(segment);
	}

	/**
	 * The constructor throws an IllegalArgumentException if the set contains
	 * only one segment.
	 */
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorThrowsIllegalArgumentExceptionIfOnlyOneSegmentProvided() {
		createPairOfCollingsSegmentsWithOneSegment();
	}

	/**
	 * The message of the IllegalArgumentException thrown when only one segment
	 * is provided is correct.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfOnlyOneSegmentProvided() {
		try {
			createPairOfCollingsSegmentsWithOneSegment();
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "There should be two segments, but found 1.");
		}
	}

	/**
	 * The constructor extracts the end point correctly from the two segments.
	 */
	@Test
	public void constructorExtractsTheEndPointCorrectly() {
		Assert.assertEquals(createCorrectPairOfCollidingSegments().getEndPoint(), END_POINT);
	}

	private PairOfCollidingSegments createCorrectPairOfCollidingSegments() {
		Segment s1 = new Segment(START_POINT_02, END_POINT, 1, 1, TRUNCATED_SHA1);
		Segment s2 = new Segment(START_POINT_3C, END_POINT, 1, 1, TRUNCATED_SHA1);
		return createPairOfCollidingSegments(s1, s2);
	}

	private void createPairOfCollidingSegmentsWithDifferentEndPoints() {
		Segment s1 = new Segment(START_POINT_02, END_POINT, 1, 1, TRUNCATED_SHA1);
		Segment s2 = new Segment(START_POINT_3C, START_POINT_02, 1, 1, TRUNCATED_SHA1);
		createPairOfCollidingSegments(s1, s2);
	}

	/**
	 * The constructor throws an IllegalArgumentException if the two segments
	 * don't have the same end point.
	 */
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorThrowsIllegalArgumentExceptionIfEndPointsDiffer() {
		createPairOfCollidingSegmentsWithDifferentEndPoints();
	}

	/**
	 * The message of the IllegalArgumentException thrown when the two segments
	 * have different end points is correct.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfSegmentsHaveDifferentEndPoints() {
		try {
			createPairOfCollidingSegmentsWithDifferentEndPoints();
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The end points of the two segments aren't equal (0x02 ≠ 0xc4).");
		}
	}

	private void createPairOfCollidingSegmentsWithSameStartPoints() {
		Segment s1 = new Segment(START_POINT_02, END_POINT, 1, 1, TRUNCATED_SHA1);
		Segment s2 = new Segment(START_POINT_02, END_POINT, 2, 1, TRUNCATED_SHA1);
		createPairOfCollidingSegments(s1, s2);
	}

	/**
	 * The constructor throws an IllegalArgumentException if the two segments
	 * have the same start point.
	 */
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorThrowsIllegalArgumentExceptionIfSegmentsHaveSameStartEndPoint() {
		createPairOfCollidingSegmentsWithSameStartPoints();
	}

	/**
	 * The message of the IllegalArgumentException thrown when the two segments
	 * have the same start point is correct.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfSegmentsHaveSameStartEndPoint() {
		try {
			createPairOfCollidingSegmentsWithSameStartPoints();
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The start points of the two segments are equal (0x02 = 0x02).");
		}
	}

	/**
	 * The constructor extracts the hash function correctly from the two
	 * segments.
	 */
	@Test
	public void constructorExtractsTheHashFunctionCorrectly() {
		Assert.assertEquals(createCorrectPairOfCollidingSegments().getHashFunction(), TRUNCATED_SHA1);
	}

	private void createPairOfCollidingSegmentsWithDifferentHashFunctions() {
		Segment s1 = new Segment(START_POINT_02, END_POINT, 1, 1, TRUNCATED_SHA1);
		Segment s2 = new Segment(START_POINT_3C, END_POINT, 1, 1, TRUNCATED_SHA256);
		createPairOfCollidingSegments(s1, s2);
	}

	/**
	 * The constructor throws an IllegalArgumentException if the two segments
	 * don't have the same hash function.
	 */
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorThrowsIllegalArgumentExceptionIfHashFunctionsDiffer() {
		createPairOfCollidingSegmentsWithDifferentHashFunctions();
	}

	/**
	 * The message of the IllegalArgumentException thrown when the two segments
	 * have different hash functions is correct.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfSegmentsHaveDifferentHashFunctions() {
		try {
			createPairOfCollidingSegmentsWithDifferentHashFunctions();
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(),
					"The hash functions of the two segments aren't equal (TRUNC(SHA-1, 8) ≠ TRUNC(SHA-256, 8)).");
		}
	}

	/**
	 * The CollisionFinder finds the collision.
	 */
	@Test
	public void findsCollisionCorrectly() {
		PairOfCollidingSegments pair = createCorrectPairOfCollidingSegments();
		Collision collision = pair.resolveCollidingSegmentsToCollision();
		Assert.assertEquals(collision, new Collision(TRUNCATED_SHA1, START_POINT_02, START_POINT_3C));
	}
}

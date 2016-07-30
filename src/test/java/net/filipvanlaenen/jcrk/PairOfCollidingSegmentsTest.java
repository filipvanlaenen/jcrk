package net.filipvanlaenen.jcrk;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests on the class PairOfCollidingSegments.
 */
public class PairOfCollidingSegmentsTest {
	private static final Point END_POINT = new Point((byte) 0x01);

	private PairOfCollidingSegments createPairOfCollidingSegments(Segment... segments) {
		Set<Segment> segmentSet = new HashSet<Segment>();
		for (Segment segment : segments) {
			segmentSet.add(segment);
		}
		return new PairOfCollidingSegments(segmentSet);
	}

	private void createPairOfCollingsSegmentsWithOneSegment() {
		Segment segment = new Segment(new Point((byte) 0x00), END_POINT, 1, 1, StandardHashFunction.SHA1);
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
		Segment s1 = new Segment(new Point((byte) 0x00), END_POINT, 1, 1, StandardHashFunction.SHA1);
		Segment s2 = new Segment(new Point((byte) 0x02), END_POINT, 1, 1, StandardHashFunction.SHA1);
		PairOfCollidingSegments pair = createPairOfCollidingSegments(s1, s2);
		Assert.assertEquals(pair.getEndPoint(), END_POINT);
	}

	private void createPairOfCollidingSegmentsWithDifferentEndPoints() {
		Segment s1 = new Segment(new Point((byte) 0x00), END_POINT, 1, 1, StandardHashFunction.SHA1);
		Segment s2 = new Segment(new Point((byte) 0x02), new Point((byte) 0x03), 1, 1, StandardHashFunction.SHA1);
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
			Assert.assertEquals(iae.getMessage(), "The end points of the two segments aren't equal (0x01 ≠ 0x03).");
		}
	}

	private void createPairOfCollidingSegmentsWithSameStartPoints() {
		Segment s1 = new Segment(new Point((byte) 0x00), END_POINT, 1, 1, StandardHashFunction.SHA1);
		Segment s2 = new Segment(new Point((byte) 0x00), END_POINT, 2, 1, StandardHashFunction.SHA1);
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
			Assert.assertEquals(iae.getMessage(), "The start points of the two segments are equal (0x00 = 0x00).");
		}
	}

	/**
	 * The constructor extracts the hash function correctly from the two segments.
	 */
	@Test
	public void constructorExtractsTheHashFunctionCorrectly() {
		Segment s1 = new Segment(new Point((byte) 0x00), END_POINT, 1, 1, StandardHashFunction.SHA1);
		Segment s2 = new Segment(new Point((byte) 0x02), END_POINT, 1, 1, StandardHashFunction.SHA1);
		PairOfCollidingSegments pair = createPairOfCollidingSegments(s1, s2);
		Assert.assertEquals(pair.getHashFunction(), StandardHashFunction.SHA1);
	}

	private void createPairOfCollidingSegmentsWithDifferentHashFunctions() {
		Segment s1 = new Segment(new Point((byte) 0x00), END_POINT, 1, 1, StandardHashFunction.SHA1);
		Segment s2 = new Segment(new Point((byte) 0x02), END_POINT, 1, 1, StandardHashFunction.SHA256);
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
			Assert.assertEquals(iae.getMessage(), "The hash functions of the two segments aren't equal (SHA-1 ≠ SHA-256).");
		}
	}
}

package net.filipvanlaenen.jcrk;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests on the Collision class.
 */
public class CollisionTest {
	private static final TruncatedStandardHashFunction TRUNCATED_SHA1 = new TruncatedStandardHashFunction(
			StandardHashFunction.SHA1, 8);
	private static final Point START_POINT_02 = new Point((byte) 0x02);
	private static final Point START_POINT_3C = new Point((byte) 0x3c);
	private static final Point END_POINT = new Point((byte) 0xC4);
	private Collision collision;

	/**
	 * Creates a collision instance to run the unit tests on.
	 */
	@BeforeMethod
	public void createCollision() {
		this.collision = new Collision(TRUNCATED_SHA1, START_POINT_02, START_POINT_3C);
	}

	/**
	 * The constructor sets the hash function correctly.
	 */
	@Test
	public void constructorSetsHashFunctionCorrectly() {
		Assert.assertEquals(collision.getHashFunction(), TRUNCATED_SHA1);
	}

	/**
	 * The constructor sets the start points correctly.
	 */
	@Test
	public void constructorSetsStartPointsCorrectly() {
		Set<Point> expectedPoints = new HashSet<Point>();
		expectedPoints.add(START_POINT_02);
		expectedPoints.add(START_POINT_3C);
		Assert.assertEquals(collision.getPoints(), expectedPoints);
	}

	/**
	 * The method getPoints returns an unmodifiable set.
	 */
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void getPointsReturnsAnUnmodifiableSet() {
		collision.getPoints().add(END_POINT);
	}

	/**
	 * The constructor throws an IllegalArgumentException if the set contains
	 * only one point.
	 */
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorThrowsIllegalArgumentExceptionIfOnlyOnePointProvided() {
		new Collision(TRUNCATED_SHA1, START_POINT_02);
	}

	/**
	 * The message of the IllegalArgumentException thrown when only one point is
	 * provided is correct.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfOnlyOnePointProvided() {
		try {
			new Collision(TRUNCATED_SHA1, START_POINT_02);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "There should be at least two points, but found only 1.");
		}
	}

	/**
	 * The constructor throws an IllegalArgumentException if the set contains a
	 * point that has a different hash value than the first point.
	 */
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorThrowsIllegalArgumentExceptionIfPointsHaveDifferentHashValues() {
		new Collision(TRUNCATED_SHA1, START_POINT_02, END_POINT);
	}

	/**
	 * The message of the IllegalArgumentException thrown when the set contains
	 * a point that has a different hash value than the first point.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfPointsHaveDifferentHashValues() {
		try {
			new Collision(TRUNCATED_SHA1, START_POINT_02, END_POINT);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(),
					"One of the points (0xc4) has a different hash value than the first point (0x02): 0xeb ≠ 0xc4.");
		}
	}
}

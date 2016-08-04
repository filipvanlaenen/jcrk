/**
 * jCRK – Cracking cryptographic hash functions implemented in Java.
 * Copyright © 2016 Filip van Laenen <f.a.vanlaenen@ieee.org>
 * 
 * This file is part of jCRK.
 *
 * jCRK is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *  
 * jCRK is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You can find a copy of the GNU General Public License in /doc/gpl.txt
 * 
 */
package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests on the class InMemorySegmentRepository.
 */
public class InMemorySegmentRepositoryTest {
	private static final int SEVEN = 7;
	private static final HashFunction TRUNCATED_SHA256 = new TruncatedStandardHashFunction(StandardHashFunction.SHA256,
			8);
	private static final Point POINT_00 = new Point((byte) 0x00);
	private static final Point POINT_01 = new Point((byte) 0x01);
	private static final Point POINT_6E = new Point((byte) 0x6e);
	private static final Point POINT_FF = new Point((byte) 0xff);
	private static final Segment SEGMENT_FOR_POINT_ZERO = new Segment(POINT_00, POINT_6E, 1, 0, TRUNCATED_SHA256);
	private SegmentRepository repository;

	/**
	 * Creates a new, empty segment repository.
	 */
	@BeforeMethod
	public void createNewSegmentRepository() {
		repository = new InMemorySegmentRepository(TRUNCATED_SHA256);
	}

	/**
	 * The constructor sets the hash function correctly.
	 */
	@Test
	public void constructorSetsTheHashFunctionCorrectly() {
		Assert.assertEquals(repository.getHashFunction(), TRUNCATED_SHA256);
	}

	/**
	 * The repository isn't empty after adding a segment.
	 */
	@Test
	public void repositoryIsNotEmptyAfterAddingASegment() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertFalse(repository.isEmpty());
	}

	/**
	 * The repository isn't full when it has order zero and one segment.
	 */
	@Test
	public void repositoryIsNotFullAfterAddingASegment() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertFalse(repository.isFull());
	}

	/**
	 * The repository has size one after adding a segment.
	 */
	@Test
	public void repositoryHasSizeOneAfterAddingASegment() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertEquals(repository.size(), 1);
	}

	/**
	 * The method add returns false if a segment was already present.
	 */
	@Test
	public void addReturnsFalseIfSegmentWasAlreadyPresent() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertFalse(repository.add(SEGMENT_FOR_POINT_ZERO));
	}

	/**
	 * The repository has size one after adding the same segment twice.
	 */
	@Test
	public void repositoryHasSizeOneAfterAddingTheSameSegmentTwice() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertEquals(repository.size(), 1);
	}

	/**
	 * The method add returns true if a segment is added.
	 */
	@Test
	public void addReturnsTrueIfSegmentIsAdded() {
		Assert.assertTrue(repository.add(SEGMENT_FOR_POINT_ZERO));
	}

	/**
	 * After adding a segment, it can be retrieved by its start point.
	 */
	@Test
	public void segmentCanBeRetrievedByItsStartPointAfterAddingIt() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertEquals(repository.getSegmentWithStartPoint(POINT_00), SEGMENT_FOR_POINT_ZERO);
	}

	/**
	 * After adding a segment, containsSegmentWithStartPoint returns true for
	 * its start point.
	 */
	@Test
	public void containsSegmentWithStartPointReturnsTrueAfterAddingASegmentWithThePointAsItsStartPoint() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertTrue(repository.containsSegmentWithStartPoint(POINT_00));
	}

	/**
	 * After adding a segment, it can be retrieved by its end point.
	 */
	@Test
	public void segmentIsIncludedInResultRetrievedByEndPointAfterAddingIt() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(SEGMENT_FOR_POINT_ZERO));
	}

	/**
	 * After adding a segment, containsSegmentsWithEndPoint returns true for its
	 * end point.
	 */
	@Test
	public void containsSegmentsWithEndPointReturnsTrueAfterAddingASegmentWithThePointAsItsEndPoint() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Assert.assertTrue(repository.containsSegmentsWithEndPoint(POINT_6E));
	}

	/**
	 * After adding two segments with the same end point, both can be retrieved
	 * by their end point.
	 */
	@Test
	public void segmentsAreIncludedInResultRetrievedByEndPointAfterAddingThem() {
		repository.add(SEGMENT_FOR_POINT_ZERO);
		Segment otherSegment = new Segment(POINT_6E, POINT_6E, 1, 0, TRUNCATED_SHA256);
		repository.add(otherSegment);
		Assert.assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(SEGMENT_FOR_POINT_ZERO));
		Assert.assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(otherSegment));
	}

	/**
	 * The repository rejects segments with the wrong order.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void repositoryThrowsIllegalArgumentExceptionIfTheSegmentHasTheWrongOrder() {
		Segment segment = new Segment(POINT_6E, POINT_6E, 1, 1, TRUNCATED_SHA256);
		repository.add(segment);
	}

	/**
	 * The message of the IllegalArgumentException when the segment has the
	 * wrong order is correct.
	 */
	@Test
	public void messageOfIllegalArgumentExceptionWhenWrongOrderMustBeCorrect() {
		Segment segment = new Segment(POINT_6E, POINT_6E, 1, 1, TRUNCATED_SHA256);
		try {
			repository.add(segment);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(),
					"The order of the segment (1) isn't the same as the order of the repository (0).");
		}
	}

	/**
	 * The repository rejects segments with the wrong hash function.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void repositoryThrowsIllegalArgumentExceptionIfTheSegmentHasTheWrongHashFunction() {
		Segment segment = new Segment(POINT_6E, POINT_6E, 1, 0, StandardHashFunction.SHA256);
		repository.add(segment);
	}

	/**
	 * The message of the IllegalArgumentException when the segment has the
	 * wrong hash function is correct.
	 */
	@Test
	public void messageOfIllegalArgumentExceptionWhenWrongHashFunctionMustBeCorrect() {
		Segment segment = new Segment(POINT_6E, POINT_6E, 1, 0, StandardHashFunction.SHA256);
		try {
			repository.add(segment);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(),
					"The hash function of the segment (SHA-256) isn't the same as the hash function of the repository (TRUNC(SHA-256, 8)).");
		}
	}

	/**
	 * The repository rejects incomplete segments.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void repositoryThrowsIllegalArgumentExceptionIfSegmentIsIncomplete() {
		repository.compressToNextOrder();
		Segment segment = new Segment(POINT_00, POINT_FF, 1, 1, TRUNCATED_SHA256);
		repository.add(segment);
	}

	/**
	 * The message of the IllegalArgumentException when the segment is
	 * incomplete must be correct.
	 */
	@Test
	public void messageOfIllegalArgumentExceptionWhenSegmentIsIncompleteMustBeCorrect() {
		repository.compressToNextOrder();
		Segment segment = new Segment(POINT_00, POINT_FF, 1, 1, TRUNCATED_SHA256);
		try {
			repository.add(segment);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The segment isn't complete.");
		}
	}

	/**
	 * The repository is full when it contains all segments that can be found at
	 * the given order.
	 */
	@Test
	public void repositoryOfOrderSevenIsFullIfItContainsTwoSegmentsForAnEightBitHashfunction() {
		for (int i = 1; i <= SEVEN; i++) {
			repository.compressToNextOrder();
		}
		repository.add(new Segment(POINT_00, POINT_01, 1, SEVEN, TRUNCATED_SHA256));
		repository.add(new Segment(POINT_01, POINT_00, 1, SEVEN, TRUNCATED_SHA256));
		Assert.assertTrue(repository.isFull());
	}
}

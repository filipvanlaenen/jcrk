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
public class InMemorySegmentRepositoryEmptyRepositoryTest {
	private static final HashFunction TRUNCATED_SHA256 = new TruncatedStandardHashFunction(StandardHashFunction.SHA256,
			8);
	private static final int BYTE_LENGTH = 1;
	private static final Point POINT_ZERO = new Point(new byte[BYTE_LENGTH]);
	private static final byte[] HASH_OF_POINT_ZERO = new byte[] { 0x6e };
	private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(HASH_OF_POINT_ZERO);
	private SegmentRepository repository;

	/**
	 * Creates a new, empty segment repository.
	 */
	@BeforeMethod
	public void createNewSegmentRepository() {
		repository = new InMemorySegmentRepository(TRUNCATED_SHA256);
	}

	/**
	 * By default, the repository is empty.
	 */
	@Test
	public void byDefaultTheRepositoryIsEmpty() {
		Assert.assertTrue(repository.isEmpty());
	}

	/**
	 * By default, the repository's size is zero.
	 */
	@Test
	public void byDefaultTheRepositoryHasSizeZero() {
		Assert.assertEquals(repository.size(), 0);
	}

	/**
	 * If there's no segment with the start point, the method
	 * getSegmentWithStartPoint returns null.
	 */
	@Test
	public void getSegmentWithStartPointReturnsNullIfStartPointIsAbsent() {
		Assert.assertNull(repository.getSegmentWithStartPoint(POINT_ZERO));
	}

	/**
	 * If there's no segment with the start point, the method
	 * containsSegmentWithStartPoint returns false.
	 */
	@Test
	public void containsSegmentWithStartPointReturnsFalseIfStartPointIsAbsent() {
		Assert.assertFalse(repository.containsSegmentWithStartPoint(POINT_ZERO));
	}

	/**
	 * If there's no segment with the end point, the method
	 * getSegmentsWithEndPoint returns an empty set.
	 */
	@Test
	public void getSegmentsWithEndPointReturnsEmptySetIfEndPointIsAbsent() {
		Assert.assertTrue(repository.getSegmentsWithEndPoint(FIRST_POINT_AFTER_POINT_ZERO).isEmpty());
	}

	/**
	 * If there's no segment with the end point, the method
	 * containsSegmentsWithEndPoint returns false.
	 */
	@Test
	public void containsSegmentsWithEndPointReturnsFalseIfEndPointIsAbsent() {
		Assert.assertFalse(repository.containsSegmentsWithEndPoint(FIRST_POINT_AFTER_POINT_ZERO));
	}

	/**
	 * By default, the repository's order is zero.
	 */
	@Test
	public void byDefaultTheRepositoryHasOrderZero() {
		Assert.assertEquals(repository.getOrder(), 0);
	}
	
	/**
	 * An empty repository isn't full.
	 */
	@Test
	public void emptyRepositoryIsNotFull() {
		Assert.assertFalse(repository.isFull());
	}

}

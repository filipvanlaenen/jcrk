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
 * Unit tests on InMemorySegmentRepository related to the compression method.
 */
public class InMemorySegmentRepositoryCompressionTest {
	private static final StandardHashFunction SHA256 = StandardHashFunction.SHA256;
	private static final Point POINT_00 = new Point((byte) 0x00);
	private static final Point POINT_01 = new Point((byte) 0x01);
	private static final Point POINT_FE = new Point((byte) 0xFE);
	private static final Point POINT_FF = new Point((byte) 0xFF);
	private SegmentRepository repository;

	/**
	 * Creates a new, empty segment repository.
	 */
	@BeforeMethod
	public void createNewSegmentRepository() {
		repository = new InMemorySegmentRepository(SHA256);
	}

	/**
	 * Compressing a repository increases its order.
	 */
	public void compressionIncreasesRepositorysOrder() {
		repository.compressToNextOrder();
		Assert.assertEquals(repository.getOrder(), 1);
	}

	/**
	 * Compressing an empty repository results in an empty repository.
	 */
	@Test
	public void compressionOfEmptyRepositoryProducesEmptyRepository() {
		repository.compressToNextOrder();
		Assert.assertTrue(repository.isEmpty());
	}

	/**
	 * Compression keeps segments of a higher order.
	 */
	@Test
	public void segmentOfHigherOrderIsKeptDuringCompression() {
		repository.compressToNextOrder();
		repository.add(new Segment(POINT_00, POINT_01, 1, 1, SHA256));
		repository.compressToNextOrder();
		Assert.assertTrue(repository.contains(new Segment(POINT_00, POINT_01, 1, 2, SHA256)));
	}

	/**
	 * Compression doesn't keep lower order segments.
	 */
	@Test
	public void lowerOrderSegmentsAreRemovedDuringCompression() {
		repository.add(new Segment(POINT_00, POINT_FF, 1, 0, SHA256));
		repository.add(new Segment(POINT_FF, POINT_00, 1, 0, SHA256));
		repository.add(new Segment(POINT_FE, POINT_FF, 1, 0, SHA256));
		repository.compressToNextOrder();
		Assert.assertTrue(repository.isEmpty());
	}
	
	/**
	 * Compression combines two lower order segments into a higher segment if possible.
	 */
	@Test
	public void compressionCombinesTwoLowerOrderSegmentsIntoAHigherOrderSegment() {
		repository.add(new Segment(POINT_00, POINT_FF, 1, 0, SHA256));
		repository.add(new Segment(POINT_FF, POINT_01, 1, 0, SHA256));
		repository.compressToNextOrder();
		Assert.assertTrue(repository.contains(new Segment(POINT_00, POINT_01, 2, 1, SHA256)));
	}

	/**
	 * Compression combines three lower order segments into a higher segment if possible.
	 */
	@Test
	public void compressionCombinesThreeLowerOrderSegmentsIntoAHigherOrderSegment() {
		repository.add(new Segment(POINT_00, POINT_FF, 1, 0, SHA256));
		repository.add(new Segment(POINT_FF, POINT_FE, 1, 0, SHA256));
		repository.add(new Segment(POINT_FE, POINT_01, 1, 0, SHA256));
		repository.compressToNextOrder();
		Assert.assertTrue(repository.contains(new Segment(POINT_00, POINT_01, 3, 1, SHA256)));
	}
}

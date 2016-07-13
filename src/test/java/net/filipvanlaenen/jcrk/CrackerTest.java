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
 * Class testing the Cracker class.
 */
public class CrackerTest {
	private static final int HASH_TRUNCATION = 8;
	private HashFunction hashFunction;
	private Cracker cracker;
	private SegmentRepository segmentRepository;

	/**
	 * Creates a new Cracker instance for the unit tests. The cracker uses
	 * an 8 bit truncated version of SHA-256 as the hash function and a new in-memory segment repository.
	 */
	@BeforeMethod
	public void createNewSegmentRepositoryAndCrackerInstance() {
		hashFunction = new TruncatedStandardHashFunction(StandardHashFunction.SHA256, HASH_TRUNCATION);
		segmentRepository = new InMemorySegmentRepository();
		cracker = new Cracker(hashFunction, segmentRepository);
	}

	/**
	 * Constructor sets the hash function correctly.
	 */
	@Test
	public void constructorSetsTheHashFunctionCorrectly() {
		Assert.assertEquals(cracker.getHashFunction(), hashFunction);
	}
	
	/**
	 * Constructor sets the segment repository correctly.
	 */
	@Test
	public void constructorSetsTheSegmentRepositoryCorrectly() {
		Assert.assertEquals(cracker.getSegmentRepository(), segmentRepository);
	}

	/**
	 * By default, a Cracker starts with order 0.
	 */
	@Test
	public void aCrackerHasCurrentOrderZeroByDefault() {
		Assert.assertEquals(cracker.getCurrentOrder(), 0);
	}
}

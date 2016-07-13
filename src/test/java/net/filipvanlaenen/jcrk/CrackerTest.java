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

import java.security.NoSuchAlgorithmException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Class testing the Cracker class.
 */
public class CrackerTest {
	private Cracker cracker;

	/**
	 * Creates a new Cracker instance before all test methods.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             If SHA-256 couldn't be instantiated.
	 */
	@BeforeMethod
	public void createNewCrackerInstance() throws NoSuchAlgorithmException {
		cracker = new Cracker(StandardHashFunction.SHA256);
	}
	
	/**
	 * Constructor sets the hash function correctly.
	 */
	@Test
	public void constructorSetsTheHashFunctionCorrectly() {
		Assert.assertEquals(cracker.getHashFunction(), StandardHashFunction.SHA256);
	}

	/**
	 * By default, no segments should be present in a Cracker instance.
	 */
	@Test
	public void aCrackerHasNoSegmentsByDefault() {
		Assert.assertEquals(cracker.getNumberOfSegments(), 0);
	}

	/**
	 * By default, a Cracker starts with order 0.
	 */
	@Test
	public void aCrackerHasCurrentOrderZeroByDefault() {
		Assert.assertEquals(cracker.getCurrentOrder(), 0);
	}
}

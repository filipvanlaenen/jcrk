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
import org.testng.annotations.Test;

/**
 * Unit tests on a SHA-256 truncated to 9 bits.
 */
public class TruncatedStandardHashFunctionSHA256To9BitsTest {
	private static final HashFunction TRUNCATED_SHA256 = new TruncatedStandardHashFunction(StandardHashFunction.SHA256, 9);
	private static final int BYTE_LENGTH = 2;
	private static final Point POINT_ZERO = new Point(new byte[BYTE_LENGTH]);
	private static final byte[] HASH_OF_POINT_ZERO = new byte[] { (byte) 0x96, (byte) 0x80 };
	private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(HASH_OF_POINT_ZERO);

	/**
	 * Verifies that the 9 bit version of SHA-256 can produce a new point.
	 */
	@Test
	public void sha256CanProduceANewPoint() {
		Assert.assertEquals(POINT_ZERO.hash(TRUNCATED_SHA256), FIRST_POINT_AFTER_POINT_ZERO);
	}
}

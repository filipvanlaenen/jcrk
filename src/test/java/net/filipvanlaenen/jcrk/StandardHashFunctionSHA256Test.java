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
 * Unit tests on SHA-256.
 */
public class StandardHashFunctionSHA256Test {
	private static final HashFunction SHA256 = StandardHashFunction.SHA256;
	private static final int BYTE_LENGTH = 32;
	private static final Point POINT_ZERO = new Point(new byte[BYTE_LENGTH]);
	private static final byte[] HASH_OF_EMPTY_STRING = new byte[]{(byte) 0xe3,
			(byte) 0xb0, (byte) 0xc4, 0x42, (byte) 0x98, (byte) 0xfc, 0x1c,
			0x14, (byte) 0x9a, (byte) 0xfb, (byte) 0xf4, (byte) 0xc8,
			(byte) 0x99, 0x6f, (byte) 0xb9, 0x24, 0x27, (byte) 0xae, 0x41,
			(byte) 0xe4, 0x64, (byte) 0x9b, (byte) 0x93, 0x4c, (byte) 0xa4,
			(byte) 0x95, (byte) 0x99, 0x1b, 0x78, 0x52, (byte) 0xb8, 0x55};
	private static final byte[] HASH_OF_POINT_ZERO = new byte[]{0x66, 0x68,
			0x7a, (byte) 0xad, (byte) 0xf8, 0x62, (byte) 0xbd, 0x77, 0x6c,
			(byte) 0x8f, (byte) 0xc1, (byte) 0x8b, (byte) 0x8e, (byte) 0x9f,
			(byte) 0x8e, 0x20, 0x08, (byte) 0x97, 0x14, (byte) 0x85, 0x6e,
			(byte) 0xe2, 0x33, (byte) 0xb3, (byte) 0x90, 0x2a, 0x59, 0x1d,
			0x0d, 0x5f, 0x29, 0x25};
	private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(
			HASH_OF_POINT_ZERO);

	/**
	 * Verifies that SHA-256 hashes the empty string correctly.
	 */
	@Test
	public void sha256HashesEmptyStringCorrectly() {
		byte[] hash = SHA256.hash(new byte[]{});
		Assert.assertEquals(hash, HASH_OF_EMPTY_STRING);
	}

	/**
	 * Verifies that SHA-256 can produce a new point.
	 */
	@Test
	public void sha256CanProduceANewPoint() {
		Assert.assertEquals(POINT_ZERO.hash(SHA256),
				FIRST_POINT_AFTER_POINT_ZERO);
	}
}

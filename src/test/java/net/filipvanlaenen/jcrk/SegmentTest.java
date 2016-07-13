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
 * Unit tests on the Segment class.
 */
public class SegmentTest {
	private static final int BYTE_0X20 = 0x20;
	private static final int BYTE_0X40 = 0x40;
	private static final int BYTE_0XFF = 0xff;
	private static final int BYTE_LENGTH = 32;
	private static final Point POINT_ZERO = new Point(new byte[BYTE_LENGTH]);
	private static final byte[] HASH_OF_POINT_ZERO = new byte[] { 0x66, 0x68, 0x7a, (byte) 0xad, (byte) 0xf8, 0x62,
			(byte) 0xbd, 0x77, 0x6c, (byte) 0x8f, (byte) 0xc1, (byte) 0x8b, (byte) 0x8e, (byte) 0x9f, (byte) 0x8e, 0x20,
			0x08, (byte) 0x97, 0x14, (byte) 0x85, 0x6e, (byte) 0xe2, 0x33, (byte) 0xb3, (byte) 0x90, 0x2a, 0x59, 0x1d,
			0x0d, 0x5f, 0x29, 0x25 };
	private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(HASH_OF_POINT_ZERO);
	private HashFunction sha256;

	/**
	 * Creates the hash function to use in the unit tests.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             If SHA-256 couldn't be instantiated.
	 */
	@BeforeMethod
	public void createSha256HashFunction() throws NoSuchAlgorithmException {
		sha256 = new SHA256();
	}
	
	/**
	 * A segment is not complete if its length is zero.
	 */
	@Test
	public void zeroLengthSegmentIsNotComplete() {
		Segment newSegment = new Segment(POINT_ZERO, 1, sha256);
		Assert.assertFalse(newSegment.isComplete());
	}

	/**
	 * A segment is not complete if its end point isn't of the same order (or
	 * higher) as the segment.
	 */
	@Test
	public void segmentNotCompleteIfEndPointNotOfSameOrder() {
		Segment incompleteSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0XFF), 1, 1, sha256);
		Assert.assertFalse(incompleteSegment.isComplete());
	}

	/**
	 * A segment is complete if its end point has the same order as the segment.
	 */
	@Test
	public void segmentCompleteIfEndPointHasSameOrder() {
		Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, sha256);
		Assert.assertTrue(completeSegment.isComplete());
	}

	/**
	 * A segment is complete if its end point has a higher order as the segment.
	 */
	@Test
	public void segmentCompleteIfEndPointHasHigherOrderThanSegment() {
		Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X20), 1, 1, sha256);
		Assert.assertTrue(completeSegment.isComplete());
	}

	/**
	 * A complete segment cannot be extended, and an IllegalStateException will
	 * be thrown if the extend method is invoked on such a segment.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void extendOnCompleteSegmentThrowsIllegalStateException() {
		Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, sha256);
		completeSegment.extend();
	}
	
	/**
	 * The message of the IllegalStateException thrown when extend is called on a complete segment must be correct.
	 */
	@Test
	public void messageOfIllegalStateExceptionAfterExtendOnCompleteSegmentMustBeCorrect() {
		Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1, sha256);
		try {
			completeSegment.extend();
			Assert.fail();
		} catch (IllegalStateException ise) {
			Assert.assertEquals(ise.getMessage(), "A complete segment cannot be extended.");
		}
	}
	
	/**
	 * An incomplete segment can be extended, and its length will be increased by one. 
	 */
	@Test
	public void extendIncrementsIncompleteSegmentLength() {
		Segment newSegment = new Segment(POINT_ZERO, 1, sha256);
		newSegment.extend();
		Assert.assertEquals(newSegment.getLength(), 1);
	}

	/**
	 * An incomplete segment can be extended, and its end point will be updated. 
	 */
	@Test
	public void extendUpdatesIncompleteSegmentEndPoint() {
		Segment newSegment = new Segment(POINT_ZERO, 1, sha256);
		newSegment.extend();
		Assert.assertEquals(newSegment.getEndPoint(), FIRST_POINT_AFTER_POINT_ZERO);
	}
}

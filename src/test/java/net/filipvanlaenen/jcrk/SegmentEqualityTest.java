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
 * Unit tests on the equality of segments.
 */
public class SegmentEqualityTest {
	private static final byte BYTE_0X01 = (byte) 0x01;
	private static final byte BYTE_0X00 = (byte) 0x00;
	private Segment segment;

	/**
	 * Creates a segment to run the unit tests on.
	 */
	@BeforeMethod
	public void createSegment() {
		segment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1, StandardHashFunction.SHA256);
	}

	/**
	 * A segment is not equal with the empty string (as an example of an object
	 * of another type).
	 */
	@Test
	public void segmentNotEqualWithEmptyString() {
		Assert.assertFalse(segment.equals(""));
	}

	/**
	 * A segment is equal with itself
	 */
	@Test
	public void segmentIsEqualWithItself() {
		Assert.assertEquals(segment, segment);
	}

	/**
	 * Two segments with the same start and end point, length, order and hash
	 * function are equal.
	 */
	@Test
	public void equalSegmentsAreEqual() {
		Segment otherSegment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1,
				StandardHashFunction.SHA256);
		Assert.assertEquals(segment, otherSegment);
	}

	/**
	 * Two segments are not equal if they have different start points.
	 */
	@Test
	public void segmentsNotEqualIfStartPointDifferent() {
		Segment otherSegment = new Segment(new Point(BYTE_0X01), new Point(BYTE_0X01), 1, 1,
				StandardHashFunction.SHA256);
		Assert.assertFalse(segment.equals(otherSegment));
	}

	/**
	 * Two segments are not equal if they have different end points.
	 */
	@Test
	public void segmentsNotEqualIfEndPointDifferent() {
		Segment otherSegment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X00), 1, 1,
				StandardHashFunction.SHA256);
		Assert.assertFalse(segment.equals(otherSegment));
	}

	/**
	 * Two segments are not equal if they have different lengths.
	 */
	@Test
	public void segmentsNotEqualIfLengthDifferent() {
		Segment otherSegment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 2, 1,
				StandardHashFunction.SHA256);
		Assert.assertFalse(segment.equals(otherSegment));
	}

	/**
	 * Two segments are not equal if they have different orders.
	 */
	@Test
	public void segmentsNotEqualIfOrderDifferent() {
		Segment otherSegment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 2,
				StandardHashFunction.SHA256);
		Assert.assertFalse(segment.equals(otherSegment));
	}

	/**
	 * Two segments are not equal if they have different hash functions.
	 */
	@Test
	public void segmentsNotEqualIfHashFunctionDifferent() {
		Segment otherSegment = new Segment(new Point(BYTE_0X00), new Point(BYTE_0X01), 1, 1,
				StandardHashFunction.SHA1);
		Assert.assertFalse(segment.equals(otherSegment));
	}
}

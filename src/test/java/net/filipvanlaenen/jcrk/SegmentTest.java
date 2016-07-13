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
 * Unit tests on the Segment class.
 */
public class SegmentTest {	
	private static final int BYTE_0X20 = 0x20;
	private static final int BYTE_0X40 = 0x40;
	private static final int BYTE_0XFF = 0xff;
	private static final Point POINT_ZERO = new Point((byte)0);

	/**
	 * A segment is not complete if its length is zero.
	 */
	@Test
	public void zeroLengthSegmentIsNotComplete() {
		Segment newSegment = new Segment(POINT_ZERO, 0);
		Assert.assertFalse(newSegment.isComplete());
	}
	
	/**
	 * A segment is not complete if its end point isn't of the same order (or higher) as the segment.
	 */
	@Test
	public void segmentNotCompleteIfEndPointNotOfSameOrder() {
		Segment incompleteSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0XFF), 1, 1);
		Assert.assertFalse(incompleteSegment.isComplete());
	}
	
	/**
	 * A segment is complete if its end point has the same order as the segment.
	 */
	@Test
	public void segmentCompleteIfEndPointHasSameOrder() {
		Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X40), 1, 1);
		Assert.assertTrue(completeSegment.isComplete());
	}

	/**
	 * A segment is complete if its end point has a higher order as the segment.
	 */
	@Test
	public void segmentCompleteIfEndPointHasHigherOrderThanSegment() {
		Segment completeSegment = new Segment(POINT_ZERO, new Point((byte) BYTE_0X20), 1, 1);
		Assert.assertTrue(completeSegment.isComplete());
	}
}

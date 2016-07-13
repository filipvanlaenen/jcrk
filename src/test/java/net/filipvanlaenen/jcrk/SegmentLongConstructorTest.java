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
 * Class testing the long constructor of the Segment class.
 */
public class SegmentLongConstructorTest {
	private static final byte START_POINT_BYTE = 0x40;
	private static final byte END_POINT_BYTE = 0x20;
	private static final Point START_POINT = new Point(START_POINT_BYTE);
	private static final Point END_POINT = new Point(END_POINT_BYTE);
	private static final long LENGTH = 2;
	private static final int ORDER = 1;
	private static final HashFunction HASH_FUNCTION = StandardHashFunction.SHA256;
	private Segment segment;
	
	/**
	 * Creates a segment instance using the long constructor to run the tests on.
	 */
	@BeforeMethod
	public void createSegmentUsingLongConstructor() {
		segment = new Segment(START_POINT, END_POINT, LENGTH, ORDER, HASH_FUNCTION);
	}
	
	/**
	 * The long constructor should set the start point correctly.
	 */
	@Test
	public void longConstructorSetsStartPointCorrectly() {
		Assert.assertEquals(segment.getStartPoint(), START_POINT);
	}

	/**
	 * The long constructor should throw an IllegalArgumentException if the start point
	 * is not a distinguished point of the provided order.
	 */
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void longConstructorThrowsIllegalArgumentExceptionIfStartPointDoesNotMatchOrder() {
		new Segment(START_POINT, END_POINT, LENGTH, 2, HASH_FUNCTION);
	}

	/**
	 * The message of the IllegalArgumentException should be correct when the start point's order
	 * doesn't match the segment order.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfStartPointOrderDoesNotMatchSegmentOrder() {
		try {
			new Segment(START_POINT, END_POINT, LENGTH, 2, HASH_FUNCTION);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The start point's order (1) is less than the provided order (2).");
		}
	}
	
	/**
	 * The long constructor should set the end point correctly.
	 */
	@Test
	public void longConstructorSetsEndPointCorrectly() {
		Assert.assertEquals(segment.getEndPoint(), END_POINT);
	}
	
	/**
	 * The long constructor should set the length correctly.
	 */
	@Test
	public void longConstructorSetsLengthCorrectly() {
		Assert.assertEquals(segment.getLength(), LENGTH);
	}

	/**
	 * The long constructor should throw an IllegalArgumentException if the length is negative.
	 */
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void longConstructorThrowsIllegalArgumentExceptionIfLengthIsNegative() {
		new Segment(START_POINT, END_POINT, -1, ORDER, HASH_FUNCTION);
	}

	/**
	 * The message of the IllegalArgumentException should be correct when the length is negative.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfLengthIsNegative() {
		try {
			new Segment(START_POINT, END_POINT, -1, ORDER, HASH_FUNCTION);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The length (-1) is negative.");
		}
	}

	/**
	 * The long constructor should set the order correctly.
	 */
	@Test
	public void longConstructorSetsOrderCorrectly() {
		Assert.assertEquals(segment.getOrder(), ORDER);
	}

	/**
	 * The long constructor should throw an IllegalArgumentException if the order is negative.
	 */
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void longConstructorThrowsIllegalArgumentExceptionIfOrderIsNegative() {
		new Segment(START_POINT, END_POINT, LENGTH, -1, HASH_FUNCTION);
	}

	/**
	 * The message of the IllegalArgumentException should be correct when the order is negative.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfOrderIsNegative() {
		try {
			new Segment(START_POINT, END_POINT, LENGTH, -1, HASH_FUNCTION);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The order (-1) is negative.");
		}
	}
}

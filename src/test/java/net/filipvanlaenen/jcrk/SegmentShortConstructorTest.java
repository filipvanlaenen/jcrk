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
 * Class testing the short constructor of the Segment class.
 */
public class SegmentShortConstructorTest {
	private static final int EIGHT = 8;
	private static final byte START_POINT_BYTE = 0x01;
	private static final Point START_POINT = new Point(START_POINT_BYTE);
	private static final int ORDER = 3;
	private Segment segment;
	
	/**
	 * Creates a segment instance using the short constructor to run the tests on.
	 */
	@BeforeMethod
	public void createSegmentUsingShortConstructor() {
		segment = new Segment(START_POINT, ORDER);
	}
	
	/**
	 * The short constructor should set the start point correctly.
	 */
	@Test
	public void shortConstructorSetsStartPointCorrectly() {
		Assert.assertEquals(segment.getStartPoint(), START_POINT);
	}
	
	/**
	 * The short constructor should throw an IllegalArgumentException if the start point
	 * is not a distinguished point of the provided order.
	 */
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void shortConstructorThrowsIllegalArgumentExceptionIfStartPointDoesNotMatchOrder() {
		new Segment(START_POINT, EIGHT);
	}
	
	/**
	 * The message of the IllegalArgumentException should be correct when the start point's order
	 * doesn't match the segment order.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfStartPointOrderDoesNotMatchSegmentOrder() {
		try {
			new Segment(START_POINT, EIGHT);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The start point's order (7) is less than the provided order (8).");
		}
	}

	/**
	 * The short constructor should set the end point equal to the start point.
	 */
	@Test
	public void shortConstructorSetsEndPointEqualToStartPoint() {
		Assert.assertEquals(segment.getEndPoint(), START_POINT);
	}

	/**
	 * The constructor should set the length to zero.
	 */
	@Test
	public void constructorSetsLengthToZero() {
		Assert.assertEquals(segment.getLength(), 0);
	}

	/**
	 * The constructor should set the order correctly.
	 */
	@Test
	public void constructorSetsOrderCorrectly() {
		Assert.assertEquals(segment.getOrder(), ORDER);
	}
	
	/**
	 * The short constructor should throw an IllegalArgumentException if the order is negative.
	 */
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void shortConstructorThrowsIllegalArgumentExceptionIfOrderIsNegative() {
		new Segment(START_POINT, -1);
	}

	/**
	 * The message of the IllegalArgumentException should be correct when the order is negative.
	 */
	@Test
	public void illegalArgumentExceptionMessageCorrectIfOrderIsNegative() {
		try {
			new Segment(START_POINT, -1);
			Assert.fail();
		} catch (IllegalArgumentException iae) {
			Assert.assertEquals(iae.getMessage(), "The order (-1) is negative.");
		}
	}

}

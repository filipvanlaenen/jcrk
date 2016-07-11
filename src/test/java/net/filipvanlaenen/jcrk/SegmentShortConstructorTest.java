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
	private static final byte START_POINT_BYTE = 1;
	private static final byte OTHER_BYTE = -1;
	private static final byte[] START_POINT = new byte[]{START_POINT_BYTE};
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
	 * The short constructor removes access to the internal start point.
	 */
	@Test
	public void shortConstructorRemovesAccessToInternalStartPoint() {
		byte[] myStartPoint = START_POINT.clone();
		Segment mySegment = new Segment(myStartPoint, ORDER);
		myStartPoint[0] = OTHER_BYTE;
		Assert.assertEquals(mySegment.getStartPoint()[0], START_POINT_BYTE);		
	}	

	/**
	 * The short constructor should set the end point equal to the start point.
	 */
	@Test
	public void shortConstructorSetsEndPointEqualToStartPoint() {
		Assert.assertEquals(segment.getEndPoint(), START_POINT);
	}

	/**
	 * The constructor removes access to the internal end point.
	 */
	@Test
	public void shortConstructorRemovesAccessToInternalEndPoint() {
		byte[] myStartPoint = START_POINT.clone();
		Segment mySegment = new Segment(myStartPoint, ORDER);
		myStartPoint[0] = OTHER_BYTE;
		Assert.assertEquals(mySegment.getEndPoint()[0], START_POINT_BYTE);		
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
}
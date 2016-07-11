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
 * Class testing the getters of the Segment class.
 */
public class SegmentGettersTest {
	private static final byte START_POINT_BYTE = 1;
	private static final byte END_POINT_BYTE = 2;
	private static final byte OTHER_BYTE = -1;
	private static final byte[] START_POINT = new byte[]{START_POINT_BYTE};
	private static final byte[] END_POINT = new byte[]{END_POINT_BYTE};
	private static final long LENGTH = 2;
	private static final int ORDER = 3;
	private Segment segment;
	
	/**
	 * Creates a segment instance using the long constructor to run the tests on.
	 */
	@BeforeMethod
	public void createSegment() {
		segment = new Segment(START_POINT, END_POINT, LENGTH, ORDER);
	}
	
	/**
	 * The method getStartPoint should not give access to the internal
	 * start point.
	 */
	@Test
	public void getStartPointDoesNotGiveAccessToInternalStartPoint() {
		segment.getStartPoint()[0] = OTHER_BYTE;
		Assert.assertEquals(segment.getStartPoint()[0], START_POINT_BYTE);		
	}

	/**
	 * The method getEndPoint should not give access to the internal
	 * end point.
	 */
	@Test
	public void getEndPointDoesNotGiveAccessToInternalEndPoint() {
		segment.getEndPoint()[0] = OTHER_BYTE;
		Assert.assertEquals(segment.getEndPoint()[0], END_POINT_BYTE);		
	}

}

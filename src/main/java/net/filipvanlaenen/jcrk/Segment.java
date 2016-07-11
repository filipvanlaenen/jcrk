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

/**
 * A segment on a path in Pollard's rho collision search. A segment has a start
 * point, an end point, a length and an order.
 */
public class Segment {

	private final byte[] startPoint;
	private final byte[] endPoint;
	private final long length;
	private final int order;

	Segment(byte[] startPoint, int order) {
		this(startPoint, startPoint, 0, order);
	}

	Segment(byte[] startPoint, byte[] endPoint, long length, int order) {
		this.startPoint = startPoint.clone();
		this.endPoint = endPoint.clone();
		this.length = length;
		this.order = order;
	}

	byte[] getStartPoint() {
		return startPoint.clone();
	}

	byte[] getEndPoint() {
		return endPoint.clone();
	}

	long getLength() {
		return length;
	}

	int getOrder() {
		return order;
	}
}

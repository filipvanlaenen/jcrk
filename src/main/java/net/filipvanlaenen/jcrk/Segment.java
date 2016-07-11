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

	private final Point startPoint;
	private final Point endPoint;
	private final long length;
	private final int order;

	Segment(Point startPoint, int order) {
		this(startPoint, startPoint, 0, order);
	}

	Segment(Point startPoint, Point endPoint, long length, int order) {
		if (startPoint.order() < order) {
			throw new IllegalArgumentException(
					String.format(
							"The start point's order (%d) is less than the provided order (%d).",
							startPoint.order(), order));
		}
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.length = length;
		this.order = order;
	}

	Point getStartPoint() {
		return startPoint;
	}

	Point getEndPoint() {
		return endPoint;
	}

	long getLength() {
		return length;
	}

	int getOrder() {
		return order;
	}
}

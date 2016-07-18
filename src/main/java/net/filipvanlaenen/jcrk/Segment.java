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
 * point, an end point, a length, an order and a hash function.
 */
public class Segment {

	private final Point startPoint;
	private Point endPoint;
	private long length;
	private final int order;
	private final HashFunction hashFunction;

	Segment(Point startPoint, int order, HashFunction hashFunction) {
		this(startPoint, startPoint, 0, order, hashFunction);
	}

	Segment(Point startPoint, Point endPoint, long length, int order, HashFunction hashFunction) {
		if (startPoint.order() < order) {
			throw new IllegalArgumentException(String.format(
					"The start point's order (%d) is less than the provided order (%d).", startPoint.order(), order));
		} else if (length < 0) {
			throw new IllegalArgumentException(String.format("The length (%d) is negative.", length));
		} else if (order < 0) {
			throw new IllegalArgumentException(String.format("The order (%d) is negative.", order));
		}
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.length = length;
		this.order = order;
		this.hashFunction = hashFunction;
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

	boolean isComplete() {
		return length > 0 && endPoint.order() >= order;
	}

	void extend() {
		if (isComplete()) {
			throw new IllegalStateException("A complete segment cannot be extended.");
		}
		endPoint = endPoint.hash(hashFunction);
		length++;
	}

	HashFunction getHashFunction() {
		return hashFunction;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Segment && isEqual((Segment) other);
	}

	private boolean isEqual(Segment other) {
		return startPoint.equals(other.startPoint) && endPoint.equals(other.endPoint) && length == other.length
				&& order == other.order && hashFunction == other.hashFunction;
	}
}

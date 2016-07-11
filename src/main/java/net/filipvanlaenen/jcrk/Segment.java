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
		if (pointOrder(startPoint) < order) {
			throw new IllegalArgumentException(
					String.format(
							"The start point's order (%d) is less than the provided order (%d).",
							pointOrder(startPoint), order));
		}
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

	private static String pointAsBinaryString(byte[] point) {
		String hex = String.format(String.format("%%0%dx", point.length * 2),
				new java.math.BigInteger(1, point));
		return hex.replaceAll("0", "0000").replaceAll("1", "0001")
				.replaceAll("2", "0010").replaceAll("3", "0011")
				.replaceAll("4", "0100").replaceAll("5", "0101")
				.replaceAll("6", "0110").replaceAll("7", "0111")
				.replaceAll("8", "1000").replaceAll("9", "1001")
				.replaceAll("a", "1010").replaceAll("b", "1011")
				.replaceAll("c", "1100").replaceAll("d", "1101")
				.replaceAll("e", "1110").replaceAll("f", "1111");
	}

	private static int pointOrder(byte[] point) {
		String binary = pointAsBinaryString(point);
		int order = 0;
		while ((binary.charAt(order) == '0') && order < binary.length()) {
			order += 1;
		}
		return order;
	}
}

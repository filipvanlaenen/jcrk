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

import java.util.Arrays;

/**
 * A point in Pollard's rho collision search.
 */
public class Point {
	private final byte[] bytes;

	Point(byte... bytes) {
		this.bytes = bytes.clone();
	}

	byte byteAt(int i) {
		return bytes[i];
	}

	/**
	 * Produces a new point with the hash value of this point under the given
	 * hash function.
	 * 
	 * @param hashFunction
	 *            The hash function that should be used to hash this point.
	 * 
	 * @return A new point that's the hash value of this point under the given
	 *         hash function.
	 */
	public Point hash(HashFunction hashFunction) {
		return new Point(hashFunction.hash(bytes));
	}

	/**
	 * Returns a hexadecimal representation of the point.
	 * 
	 * @return A string representing the point in hexadecimal notation.
	 */
	public String asHexadecimalString() {
		return String.format(String.format("%%0%dx", bytes.length * 2), new java.math.BigInteger(1, bytes));
	}

	String asBinaryString() {
		return asHexadecimalString().replaceAll("0", "0000").replaceAll("1", "0001").replaceAll("2", "0010")
				.replaceAll("3", "0011").replaceAll("4", "0100").replaceAll("5", "0101").replaceAll("6", "0110")
				.replaceAll("7", "0111").replaceAll("8", "1000").replaceAll("9", "1001").replaceAll("a", "1010")
				.replaceAll("b", "1011").replaceAll("c", "1100").replaceAll("d", "1101").replaceAll("e", "1110")
				.replaceAll("f", "1111");
	}

	int order() {
		String binary = asBinaryString();
		int order = 0;
		while ((order < binary.length() && binary.charAt(order) == '0')) {
			order += 1;
		}
		return order;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Point && Arrays.equals(bytes, ((Point) other).bytes);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(bytes);
	}

}

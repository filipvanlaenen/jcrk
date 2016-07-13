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
 * A truncation of a standard hash function. Instances of this class refer to
 * the base standard hash function, and have a bit length defining the
 * truncation.
 */
public class TruncatedStandardHashFunction implements HashFunction {
	private static final int BYTE_0XFF = 0xff;
	private static final int BITS_IN_A_BYTE = 8;
	private final StandardHashFunction standardHashFunction;
	private final int byteArrayLength;
	private final byte lastByteMask;

	TruncatedStandardHashFunction(StandardHashFunction standardHashFunction, int bitLength) {
		this.standardHashFunction = standardHashFunction;
		this.byteArrayLength = bitLength / BITS_IN_A_BYTE + ((bitLength % BITS_IN_A_BYTE == 0) ? 0 : 1);
		lastByteMask = (byte) (BYTE_0XFF << ((bitLength % BITS_IN_A_BYTE == 0) ? 0
				: BITS_IN_A_BYTE - bitLength % BITS_IN_A_BYTE));
	}

	@Override
	public byte[] hash(byte[] source) {
		return truncate(standardHashFunction.hash(source));
	}

	private byte[] truncate(byte[] original) {
		byte[] result = Arrays.copyOf(original, byteArrayLength);
		result[byteArrayLength - 1] = (byte) (result[byteArrayLength - 1] & lastByteMask);
		return result;
	}
}

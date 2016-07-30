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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Enumeration with some standard hash functions.
 */
public enum StandardHashFunction implements HashFunction {
	SHA1("SHA-1"), SHA256("SHA-256");

	private static final int EIGHT = 8;
	private final MessageDigest digest;
	
	StandardHashFunction(String algorithm) {
		digest = getMessageDigest(algorithm);
	}

	private MessageDigest getMessageDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	@Override
	public byte[] hash(byte[] source) {
		return digest.digest(source);
	}
	
	@Override
	public String toString() {
		return digest.getAlgorithm();
	}

	@Override
	public int getBitLength() {
		return getByteLength() * EIGHT;
	}

	@Override
	public int getByteLength() {
		return digest.getDigestLength();
	}
}

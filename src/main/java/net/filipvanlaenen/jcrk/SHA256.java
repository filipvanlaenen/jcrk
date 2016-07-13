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
 * The hash function SHA-256. 
 */
public final class SHA256 implements HashFunction {
	private static final String SHA_256_NAME = "SHA-256";
	private final MessageDigest digest;
	
	SHA256() throws NoSuchAlgorithmException {
		digest = MessageDigest.getInstance(SHA_256_NAME);
	}
	
	@Override
	public byte[] hash(byte[] source) {	
		return digest.digest(source);
	}
	
	@Override
	public String getName() {
		return SHA_256_NAME;
	}

}

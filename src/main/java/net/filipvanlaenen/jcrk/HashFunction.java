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
 * Definition of a hash function. It takes a byte array, and produces a new byte
 * array.
 */
public interface HashFunction {
	/**
	 * The hash function. It takes a byte array, and produces a new byte array.
	 * 
	 * @param source
	 *            The source byte array.
	 * @return The resulting byte array.
	 */
	byte[] hash(byte[] source);

	/**
	 * Returns the name of the hash function.
	 * 
	 * @return The name of the hash function.
	 */
	String getName();
}

package net.filipvanlaenen.jcrk;

import java.util.Arrays;

/**
 * A point in Pollard's rho collision search.
 */
public final class Point {
    /**
     * The bytes of this point.
     */
    private final byte[] bytes;

    /**
     * Constructor taking a byte array as its parameter.
     *
     * @param bytes The bytes of this point.
     */
    Point(final byte... bytes) {
        this.bytes = bytes.clone();
    }

    /**
     * Returns the point represented as a binary string.
     *
     * @return A binary string representing this point.
     */
    String asBinaryString() {
        return asHexadecimalString().replaceAll("0", "0000").replaceAll("1", "0001").replaceAll("2", "0010")
                .replaceAll("3", "0011").replaceAll("4", "0100").replaceAll("5", "0101").replaceAll("6", "0110")
                .replaceAll("7", "0111").replaceAll("8", "1000").replaceAll("9", "1001").replaceAll("a", "1010")
                .replaceAll("b", "1011").replaceAll("c", "1100").replaceAll("d", "1101").replaceAll("e", "1110")
                .replaceAll("f", "1111");
    }

    /**
     * Returns a hexadecimal representation of the point.
     *
     * @return A string representing the point in hexadecimal notation.
     */
    public String asHexadecimalString() {
        return String.format(String.format("%%0%dx", bytes.length * 2), new java.math.BigInteger(1, bytes));
    }

    /**
     * Returns this point's byte at a given index.
     *
     * @param i The index of the requested byte.
     * @return The byte at a given index.
     */
    byte byteAt(final int i) {
        return bytes[i];
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Point && Arrays.equals(bytes, ((Point) other).bytes);
    }

    public byte[] getBytes() {
        return bytes.clone();
    }

    /**
     * Calculates the Hamming distance between this point and another point.
     *
     * @param other The other point.
     * @return The Hamming distance between this point and the other.
     */
    public int hammingDistanceTo(final Point other) {
        String binaryString = asBinaryString();
        String otherBinaryString = other.asBinaryString();
        if (binaryString.length() != otherBinaryString.length()) {
            throw new IllegalArgumentException();
        }
        int distance = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) != otherBinaryString.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    /**
     * Produces a new point with the hash value of this point under the given hash function.
     *
     * @param hashFunction The hash function that should be used to hash this point.
     * @return A new point that's the hash value of this point under the given hash function.
     */
    public Point hash(final HashFunction hashFunction) {
        return new Point(hashFunction.hash(bytes));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    /**
     * Returns the order of this point.
     *
     * @return The order.
     */
    int order() {
        String binary = asBinaryString();
        int order = 0;
        while (order < binary.length() && binary.charAt(order) == '0') {
            order += 1;
        }
        return order;
    }
}

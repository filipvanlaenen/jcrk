package net.filipvanlaenen.jcrk;

import java.util.Arrays;

/**
 * A truncation of a standard hash function. Instances of this class refer to the base standard hash function, and have
 * a bit length defining the truncation.
 */
public final class TruncatedStandardHashFunction implements HashFunction {
    /**
     * The byte 0xFF.
     */
    private static final int BYTE_0XFF = 0xff;
    /**
     * The number of bits in a byte.
     */
    private static final int BITS_IN_A_BYTE = 8;

    /**
     * The bit length.
     */
    private final int bitLength;
    /**
     * The byte array length.
     */
    private final int byteArrayLength;
    /**
     * The mask for the last byte.
     */
    private final byte lastByteMask;
    /**
     * The standard hash function.
     */
    private final StandardHashFunction standardHashFunction;

    /**
     * Constructor using a standard hash function and a bit length to create a truncated standard hash function.
     *
     * @param standardHashFunction The standard hash function to truncate.
     * @param bitLength            The length of the truncation in bits.
     */
    public TruncatedStandardHashFunction(final StandardHashFunction standardHashFunction, final int bitLength) {
        this.standardHashFunction = standardHashFunction;
        this.bitLength = bitLength;
        this.byteArrayLength = bitLength / BITS_IN_A_BYTE + ((bitLength % BITS_IN_A_BYTE == 0) ? 0 : 1);
        lastByteMask = (byte) (BYTE_0XFF << ((bitLength % BITS_IN_A_BYTE == 0) ? 0
                : BITS_IN_A_BYTE - bitLength % BITS_IN_A_BYTE));
    }

    @Override
    public byte[] hash(final byte[] source) {
        return truncate(standardHashFunction.hash(source));
    }

    /**
     * Truncates a byte array.
     *
     * @param original The original byte array.
     * @return The truncated byte array.
     */
    private byte[] truncate(final byte[] original) {
        byte[] result = Arrays.copyOf(original, byteArrayLength);
        result[byteArrayLength - 1] = (byte) (result[byteArrayLength - 1] & lastByteMask);
        return result;
    }

    @Override
    public String toString() {
        return String.format("TRUNC(%s, %d)", standardHashFunction, bitLength);
    }

    @Override
    public int getBitLength() {
        return bitLength;
    }

    @Override
    public int getByteLength() {
        return byteArrayLength;
    }
}

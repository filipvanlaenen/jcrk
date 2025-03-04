package net.filipvanlaenen.jcrk;

/**
 * Definition of a hash function. It takes a byte array, and produces a new byte array.
 */
public interface HashFunction {
    /**
     * The hash function. It takes a byte array, and produces a new byte array.
     *
     * @param source The source byte array.
     * @return The resulting byte array.
     */
    byte[] hash(byte[] source);

    /**
     * The bit length of the hash function.
     *
     * @return The bit length of the hash function.
     */
    int getBitLength();

    /**
     * The byte length of the hash function.
     *
     * @return The byte length of the hash function.
     */
    int getByteLength();
}

package net.filipvanlaenen.jcrk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Enumeration with some standard hash functions.
 */
public enum StandardHashFunction implements HashFunction {
    /**
     * The SHA-1 hash function.
     */
    SHA1("SHA-1"),
    /**
     * The SHA-224 hash function.
     */
    SHA224("SHA-224"),
    /**
     * The SHA-256 hash function.
     */
    SHA256("SHA-256"),
    /**
     * The SHA-384 hash function.
     */
    SHA384("SHA-384"),
    /**
     * The SHA-256 hash function.
     */
    SHA512("SHA-512");

    /**
     * The magic number eight.
     */
    private static final int EIGHT = 8;
    /**
     * The message digest.
     */
    private final MessageDigest digest;

    /**
     * Constructor taking the name of the algorithm as its parameter.
     *
     * @param algorithm The name of the algorithm.
     */
    StandardHashFunction(final String algorithm) {
        digest = getMessageDigest(algorithm);
    }

    @Override
    public int getBitLength() {
        return getByteLength() * EIGHT;
    }

    @Override
    public int getByteLength() {
        return digest.getDigestLength();
    }

    /**
     * Tries to instantiate the message digest based on the algorithm's name.
     *
     * @param algorithm The name of the algorithm.
     * @return The message digest for the algorithm, or <code>null</code> if none could be found for the given name.
     */
    private MessageDigest getMessageDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public byte[] hash(final byte[] source) {
        return digest.digest(source);
    }

    @Override
    public String toString() {
        return digest.getAlgorithm();
    }
}

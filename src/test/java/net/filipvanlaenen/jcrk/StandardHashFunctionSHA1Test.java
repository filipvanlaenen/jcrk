package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests on SHA-1.
 */
public class StandardHashFunctionSHA1Test {
    private static final HashFunction SHA1 = StandardHashFunction.SHA1;
    private static final int BYTE_LENGTH = 20;
    private static final Point POINT_ZERO = new Point(new byte[BYTE_LENGTH]);
    private static final byte[] HASH_OF_EMPTY_STRING =
            new byte[] {(byte) 0xda, 0x39, (byte) 0xa3, (byte) 0xee, 0x5e, 0x6b, 0x4b, 0x0d, 0x32, 0x55, (byte) 0xbf,
                    (byte) 0xef, (byte) 0x95, 0x60, 0x18, (byte) 0x90, (byte) 0xaf, (byte) 0xd8, 0x07, 0x09};
    private static final byte[] HASH_OF_POINT_ZERO =
            new byte[] {0x67, 0x68, 0x03, 0x3e, 0x21, 0x64, 0x68, 0x24, 0x7b, (byte) 0xd0, 0x31, (byte) 0xa0,
                    (byte) 0xa2, (byte) 0xd9, (byte) 0x87, 0x6d, 0x79, (byte) 0x81, (byte) 0x8f, (byte) 0x8f};
    private static final Point FIRST_POINT_AFTER_POINT_ZERO = new Point(HASH_OF_POINT_ZERO);

    /**
     * Verifies that SHA-1 hashes the empty string correctly.
     */
    @Test
    public void sha1HashesEmptyStringCorrectly() {
        byte[] hash = SHA1.hash(new byte[] {});
        Assert.assertEquals(hash, HASH_OF_EMPTY_STRING);
    }

    /**
     * Verifies that SHA-1 can produce a new point.
     */
    @Test
    public void sha1CanProduceANewPoint() {
        Assert.assertEquals(POINT_ZERO.hash(SHA1), FIRST_POINT_AFTER_POINT_ZERO);
    }
}

package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>TruncatedStandardHashFunction</code> class.
 */
public class TruncatedStandardHashFunctionTest {
    /**
     * The SHA-256 hash algorithm truncated to eight bits.
     */
    private static final HashFunction TRUNCATED_8_SHA256 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA256, 8);
    /**
     * The SHA-256 hash algorithm truncated to nine bits.
     */
    private static final HashFunction TRUNCATED_9_SHA256 =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA256, 9);
    /**
     * Point zero for eight bits.
     */
    private static final Point POINT_ZERO_8 = new Point(new byte[1]);
    /**
     * Point zero for nine bits.
     */
    private static final Point POINT_ZERO_9 = new Point(new byte[2]);
    /**
     * Hash value of point zero for eight bits.
     */
    private static final byte[] HASH_OF_POINT_ZERO_8 = new byte[] {0x6e};
    /**
     * Hash value of point zero for nine bits.
     */
    private static final byte[] HASH_OF_POINT_ZERO_9 = new byte[] {(byte) 0x96, (byte) 0x80};
    /**
     * The first point after point zero for eight bits.
     */
    private static final Point FIRST_POINT_AFTER_POINT_ZERO_8 = new Point(HASH_OF_POINT_ZERO_8);
    /**
     * The first point after point zero for nine bits.
     */
    private static final Point FIRST_POINT_AFTER_POINT_ZERO_9 = new Point(HASH_OF_POINT_ZERO_9);

    /**
     * Verifies that the 8 bite version of SHA-256 can produce a new point.
     */
    @Test
    public void sha256TruncatedTo8ShouldProduceANewPoint() {
        assertEquals(POINT_ZERO_8.hash(TRUNCATED_8_SHA256), FIRST_POINT_AFTER_POINT_ZERO_8);
    }

    /**
     * Verifies that the 9 bit version of SHA-256 can produce a new point.
     */
    @Test
    public void sha256TruncatedTo9ShouldProduceANewPoint() {
        assertEquals(POINT_ZERO_9.hash(TRUNCATED_9_SHA256), FIRST_POINT_AFTER_POINT_ZERO_9);
    }

    /**
     * The String representation of the hash function must be correct.
     */
    @Test
    public void toStringShouldPrintTheNameCorrectly() {
        assertEquals(TRUNCATED_8_SHA256.toString(), "TRUNC(SHA-256, 8)");
    }
}

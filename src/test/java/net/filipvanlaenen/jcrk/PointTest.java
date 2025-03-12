package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.Map.Entry;

/**
 * Unit tests on the Point class.
 */
public class PointTest {
    /**
     * The magic number eight.
     */
    private static final int EIGHT = 8;
    /**
     * The byte 0x00.
     */
    private static final Byte BYTE_0X00 = 0x00;
    /**
     * The byte 0x01.
     */
    private static final Byte BYTE_0X01 = 0x01;
    /**
     * The byte 0x23.
     */
    private static final Byte BYTE_0X23 = 0x23;
    /**
     * The byte 0x45.
     */
    private static final Byte BYTE_0X45 = 0x45;
    /**
     * The byte 0x67.
     */
    private static final Byte BYTE_0X67 = 0x67;
    /**
     * The byte 0x89.
     */
    private static final Byte BYTE_0X89 = (byte) 0x89;
    /**
     * The byte 0xAB.
     */
    private static final Byte BYTE_0XAB = (byte) 0xAB;
    /**
     * The byte 0xCD.
     */
    private static final Byte BYTE_0XCD = (byte) 0xCD;
    /**
     * The byte 0xEF.
     */
    private static final Byte BYTE_0XEF = (byte) 0xEF;
    /**
     * A map with binary representations of bytes.
     */
    private static final Map<Byte, String> BINARY_MAP = Map.<Byte, String>of(
            new Entry<Byte, String>(BYTE_0X01, "00000001"), new Entry<Byte, String>(BYTE_0X23, "00100011"),
            new Entry<Byte, String>(BYTE_0X45, "01000101"), new Entry<Byte, String>(BYTE_0X67, "01100111"),
            new Entry<Byte, String>(BYTE_0X89, "10001001"), new Entry<Byte, String>(BYTE_0XAB, "10101011"),
            new Entry<Byte, String>(BYTE_0XCD, "11001101"), new Entry<Byte, String>(BYTE_0XEF, "11101111"));
    /**
     * A map with hexadecimal representations of bytes.
     */
    private static final Map<Byte, String> HEXADECIMAL_MAP =
            Map.<Byte, String>of(new Entry<Byte, String>(BYTE_0X01, "01"), new Entry<Byte, String>(BYTE_0X23, "23"),
                    new Entry<Byte, String>(BYTE_0X45, "45"), new Entry<Byte, String>(BYTE_0X67, "67"),
                    new Entry<Byte, String>(BYTE_0X89, "89"), new Entry<Byte, String>(BYTE_0XAB, "ab"),
                    new Entry<Byte, String>(BYTE_0XCD, "cd"), new Entry<Byte, String>(BYTE_0XEF, "ef"));

    /**
     * The constructor sets the bytes correctly.
     */
    @Test
    public void constructorSetsTheBytesCorrectly() {
        assertEquals(new Point(BYTE_0X00).byteAt(0), BYTE_0X00);
    }

    /**
     * The constructor should make sure that the internal state of the point can't be changed by keeping a reference to
     * the byte array used as the constructor parameter.
     */
    @Test
    public void constructorDetachesTheInternalBytesFromTheParameter() {
        byte[] byteArray = new byte[] {BYTE_0X00};
        Point point = new Point(byteArray);
        byteArray[0] = BYTE_0X01;
        assertEquals(point.byteAt(0), BYTE_0X00);
    }

    /**
     * The method asHexadecimalString produces all hexadecimal digits correctly.
     */
    @Test
    public void asHexadecimalStringProducesHexadecimalDigitsCorrectly() {
        for (Entry<Byte, String> entry : HEXADECIMAL_MAP) {
            assertEquals(new Point(entry.key()).asHexadecimalString(), entry.value());
        }
    }

    /**
     * The method asBinaryString produces all binary digits correctly.
     */
    @Test
    public void asBinaryStringProducesBinarylDigitsCorrectly() {
        for (Entry<Byte, String> entry : BINARY_MAP) {
            assertEquals(new Point(entry.key()).asBinaryString(), entry.value());
        }
    }

    /**
     * A Point starting with a 1 has order 0.
     */
    @Test
    public void pointStartingWithOneHasOrderZero() {
        assertEquals(new Point(BYTE_0XEF).order(), 0);
    }

    /**
     * A Point starting with a 01 has order 1.
     */
    @Test
    public void pointStartingWithZeroOneHasOrderOne() {
        assertEquals(new Point(BYTE_0X45).order(), 1);
    }

    /**
     * A Point with eight zeros has order 8.
     */
    @Test
    public void pointWithEightZeroesHasOrderEight() {
        assertEquals(new Point(BYTE_0X00).order(), EIGHT);
    }

    /**
     * A Point is not equal to a String.
     */
    @Test
    public void aPointIsNotEqualToAString() {
        assertFalse(new Point().equals(""));
    }

    /**
     * A Point is equal to itself.
     */
    @Test
    public void aPointIsEqualToItself() {
        Point point = new Point();
        assertEquals(point, point);
    }

    /**
     * A Point is equal to a Point with the same byte array.
     */
    @Test
    public void aPointIsEqualToAPointWithTheSameByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 0, (byte) 1, (byte) 2);
        assertEquals(a, b);
    }

    /**
     * A Point is not equal to a Point with a different byte array.
     */
    @Test
    public void aPointIsNotEqualToAPointWithADifferentByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 2, (byte) 1, (byte) 0);
        assertFalse(a.equals(b));
    }

    /**
     * A Point has the same hash code as itself.
     */
    @Test
    public void aPointHasTheSameHashcodeAsItself() {
        Point point = new Point();
        assertEquals(point.hashCode(), point.hashCode());
    }

    /**
     * A Point has the same hash code as a Point with the same byte array.
     */
    @Test
    public void aPointHasTheSameHashcodeAsAPointWithTheSameByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 0, (byte) 1, (byte) 2);
        assertEquals(a.hashCode(), b.hashCode());
    }

    /**
     * A Point should (in general) not have the same hash code as a Point with a different byte array.
     */
    @Test
    public void aPointShouldNotHaveTheSameHashcodeAsAPointWithADifferentByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 2, (byte) 1, (byte) 0);
        assertFalse(a.hashCode() == b.hashCode());
    }
}

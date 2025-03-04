package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
    private static final byte BYTE_0X00 = 0x00;
    /**
     * The byte 0x01.
     */
    private static final byte BYTE_0X01 = 0x01;
    /**
     * The byte 0x23.
     */
    private static final byte BYTE_0X23 = 0x23;
    /**
     * The byte 0x45.
     */
    private static final byte BYTE_0X45 = 0x45;
    /**
     * The byte 0x67.
     */
    private static final byte BYTE_0X67 = 0x67;
    /**
     * The byte 0x89.
     */
    private static final byte BYTE_0X89 = (byte) 0x89;
    /**
     * The byte 0xAB.
     */
    private static final byte BYTE_0XAB = (byte) 0xAB;
    /**
     * The byte 0xCD.
     */
    private static final byte BYTE_0XCD = (byte) 0xCD;
    /**
     * The byte 0xEF.
     */
    private static final byte BYTE_0XEF = (byte) 0xEF;

    /**
     * The constructor sets the bytes correctly.
     */
    @Test
    public void constructorSetsTheBytesCorrectly() {
        Assert.assertEquals(new Point(BYTE_0X00).byteAt(0), BYTE_0X00);
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
        Assert.assertEquals(point.byteAt(0), BYTE_0X00);
    }

    /**
     * Produces the data to test the method asBinaryString and asHexadecimalString.
     *
     * @return The data to test the methods asBinaryString and asHexadecimalString.
     */
    @DataProvider(name = "binaryAndHexadecimalTestData")
    public Object[][] bytesAndBinaryNumbers() {
        return new Object[][] {{BYTE_0X01, "00000001", "01"}, {BYTE_0X23, "00100011", "23"},
                {BYTE_0X45, "01000101", "45"}, {BYTE_0X67, "01100111", "67"}, {BYTE_0X89, "10001001", "89"},
                {BYTE_0XAB, "10101011", "ab"}, {BYTE_0XCD, "11001101", "cd"}, {BYTE_0XEF, "11101111", "ef"}};
    }

    /**
     * The method asHexadecimalString produces all hexadecimal digits correctly.
     *
     * @param aByte               A byte to be converted.
     * @param expectedBinary      The expected binary String representation of the byte.
     * @param expectedHexadecimal The expected hexadecimal String representation of the byte.
     */
    @Test(dataProvider = "binaryAndHexadecimalTestData")
    public void asHexadecimalStringProducesHexadecimalDigitsCorrectly(final byte aByte, final String expectedBinary,
            final String expectedHexadecimal) {
        Assert.assertEquals(new Point(aByte).asHexadecimalString(), expectedHexadecimal);
    }

    /**
     * The method asBinaryString converts all hexadecimal digits correctly.
     *
     * @param aByte               A byte to be converted.
     * @param expectedBinary      The expected binary String representation of the byte.
     * @param expectedHexadecimal The expected hexadecimal String representation of the byte.
     */
    @Test(dataProvider = "binaryAndHexadecimalTestData")
    public void asBinaryStringConvertsHexadecimalDigitsCorrectly(final byte aByte, final String expectedBinary,
            final String expectedHexadecimal) {
        Assert.assertEquals(new Point(aByte).asBinaryString(), expectedBinary);
    }

    /**
     * A Point starting with a 1 has order 0.
     */
    @Test
    public void pointStartingWithOneHasOrderZero() {
        Assert.assertEquals(new Point(BYTE_0XEF).order(), 0);
    }

    /**
     * A Point starting with a 01 has order 1.
     */
    @Test
    public void pointStartingWithZeroOneHasOrderOne() {
        Assert.assertEquals(new Point(BYTE_0X45).order(), 1);
    }

    /**
     * A Point with eight zeros has order 8.
     */
    @Test
    public void pointWithEightZeroesHasOrderEight() {
        Assert.assertEquals(new Point(BYTE_0X00).order(), EIGHT);
    }

    /**
     * A Point is not equal to a String.
     */
    @Test
    public void aPointIsNotEqualToAString() {
        Assert.assertFalse(new Point().equals(""));
    }

    /**
     * A Point is equal to itself.
     */
    @Test
    public void aPointIsEqualToItself() {
        Point point = new Point();
        Assert.assertEquals(point, point);
    }

    /**
     * A Point is equal to a Point with the same byte array.
     */
    @Test
    public void aPointIsEqualToAPointWithTheSameByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 0, (byte) 1, (byte) 2);
        Assert.assertEquals(a, b);
    }

    /**
     * A Point is not equal to a Point with a different byte array.
     */
    @Test
    public void aPointIsNotEqualToAPointWithADifferentByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 2, (byte) 1, (byte) 0);
        Assert.assertFalse(a.equals(b));
    }

    /**
     * A Point has the same hash code as itself.
     */
    @Test
    public void aPointHasTheSameHashcodeAsItself() {
        Point point = new Point();
        Assert.assertEquals(point.hashCode(), point.hashCode());
    }

    /**
     * A Point has the same hash code as a Point with the same byte array.
     */
    @Test
    public void aPointHasTheSameHashcodeAsAPointWithTheSameByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 0, (byte) 1, (byte) 2);
        Assert.assertEquals(a.hashCode(), b.hashCode());
    }

    /**
     * A Point should (in general) not have the same hash code as a Point with a different byte array.
     */
    @Test
    public void aPointShouldNotHaveTheSameHashcodeAsAPointWithADifferentByteArray() {
        Point a = new Point((byte) 0, (byte) 1, (byte) 2);
        Point b = new Point((byte) 2, (byte) 1, (byte) 0);
        Assert.assertFalse(a.hashCode() == b.hashCode());
    }
}

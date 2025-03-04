package net.filipvanlaenen.jcrk;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests on InMemorySegmentRepository related to the compression method.
 */
public class InMemorySegmentRepositoryCompressionTest {
    private static final int THREE = 3;
    private static final StandardHashFunction SHA256 = StandardHashFunction.SHA256;
    private static final Point POINT_20 = new Point((byte) 0x20);
    private static final Point POINT_21 = new Point((byte) 0x21);
    private static final Point POINT_40 = new Point((byte) 0x40);
    private static final Point POINT_41 = new Point((byte) 0x41);
    private static final Point POINT_FD = new Point((byte) 0xFD);
    private static final Point POINT_FE = new Point((byte) 0xFE);
    private static final Point POINT_FF = new Point((byte) 0xFF);
    private SegmentRepository repository;

    /**
     * Creates a new, empty segment repository.
     */
    @BeforeMethod
    public void createNewSegmentRepository() {
        repository = new InMemorySegmentRepository(SHA256);
    }

    /**
     * Compressing a repository increases its order.
     */
    public void compressionIncreasesRepositorysOrder() {
        repository.compressToNextOrder();
        Assert.assertEquals(repository.getOrder(), 1);
    }

    /**
     * Compressing an empty repository results in an empty repository.
     */
    @Test
    public void compressionOfEmptyRepositoryProducesEmptyRepository() {
        repository.compressToNextOrder();
        Assert.assertTrue(repository.isEmpty());
    }

    /**
     * Compression keeps segments of a higher order.
     */
    @Test
    public void segmentOfHigherOrderIsKeptDuringCompression() {
        repository.compressToNextOrder();
        repository.add(new Segment(POINT_20, POINT_21, 1, 1, SHA256));
        repository.compressToNextOrder();
        Assert.assertTrue(repository.contains(new Segment(POINT_20, POINT_21, 1, 2, SHA256)));
    }

    /**
     * Compression doesn't keep lower order segments.
     */
    @Test
    public void lowerOrderSegmentsAreRemovedDuringCompression() {
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FE, POINT_40, 1, 0, SHA256));
        repository.add(new Segment(POINT_FD, POINT_FF, 1, 0, SHA256));
        repository.compressToNextOrder();
        Assert.assertTrue(repository.isEmpty());
    }

    /**
     * Compression clears the map with the end points.
     */
    @Test
    public void compressionClearsMapWithEndPoints() {
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.compressToNextOrder();
        Assert.assertFalse(repository.containsSegmentsWithEndPoint(POINT_FF));
    }

    /**
     * Compression combines two lower order segments into a higher segment if possible.
     */
    @Test
    public void compressionCombinesTwoLowerOrderSegmentsIntoAHigherOrderSegment() {
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FF, POINT_41, 1, 0, SHA256));
        repository.compressToNextOrder();
        Assert.assertTrue(repository.contains(new Segment(POINT_40, POINT_41, 2, 1, SHA256)));
    }

    /**
     * Compression combines two lower order segments into a higher segment if possible, but progress after the new
     * distinguished point.
     */
    @Test
    public void compressionStopsCombiningLowerOrderSegmentsAfterFirstHigherOrderDistinguishedPoint() {
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FF, POINT_41, 1, 0, SHA256));
        repository.add(new Segment(POINT_41, POINT_FE, 1, 0, SHA256));
        repository.compressToNextOrder();
        Assert.assertTrue(repository.contains(new Segment(POINT_40, POINT_41, 2, 1, SHA256)));
    }

    /**
     * Compression combines three lower order segments into a higher segment if possible.
     */
    @Test
    public void compressionCombinesThreeLowerOrderSegmentsIntoAHigherOrderSegment() {
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FF, POINT_FE, 1, 0, SHA256));
        repository.add(new Segment(POINT_FE, POINT_41, 1, 0, SHA256));
        repository.compressToNextOrder();
        Assert.assertTrue(repository.contains(new Segment(POINT_40, POINT_41, THREE, 1, SHA256)));
    }
}

package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static net.filipvanlaenen.jcrk.StandardHashFunction.SHA256;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the class InMemorySegmentRepository.
 */
public class InMemorySegmentRepositoryTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The magic number seven.
     */
    private static final int SEVEN = 7;
    /**
     * The hash function SHA-256 truncated to 8 bits.
     */
    private static final HashFunction SHA256_TRUNCATED_TO_8_BITS = new TruncatedStandardHashFunction(SHA256, 8);
    /**
     * The point 0x00.
     */
    private static final Point POINT_00 = new Point((byte) 0x00);
    /**
     * The point 0x01.
     */
    private static final Point POINT_01 = new Point((byte) 0x01);
    /**
     * The point 0x20.
     */
    private static final Point POINT_20 = new Point((byte) 0x20);
    /**
     * The point 0x21.
     */
    private static final Point POINT_21 = new Point((byte) 0x21);
    /**
     * The point 0x40.
     */
    private static final Point POINT_40 = new Point((byte) 0x40);
    /**
     * The point 0x41.
     */
    private static final Point POINT_41 = new Point((byte) 0x41);
    /**
     * The point 0x6E.
     */
    private static final Point POINT_6E = new Point((byte) 0x6e);
    /**
     * The point 0xFD.
     */
    private static final Point POINT_FD = new Point((byte) 0xFD);
    /**
     * The point 0xFE.
     */
    private static final Point POINT_FE = new Point((byte) 0xFE);
    /**
     * The point 0xFF.
     */
    private static final Point POINT_FF = new Point((byte) 0xff);
    /**
     * The segment starting from point zero.
     */
    private static final Segment SEGMENT_FOR_POINT_ZERO =
            new Segment(POINT_00, POINT_6E, 1, 0, SHA256_TRUNCATED_TO_8_BITS);

    /**
     * Creates a new, empty segment repository for SHA-256.
     *
     * @return A new, empty segment repository for SHA-256.
     */
    public SegmentRepository createNewSha256SegmentRepository() {
        return new InMemorySegmentRepository(SHA256);
    }

    /**
     * Creates a new, empty segment repository for truncated SHA-256.
     *
     * @return A new, empty segment repository for truncated SHA-256.
     */
    public SegmentRepository createNewTruncatedSha256SegmentRepository() {
        return new InMemorySegmentRepository(SHA256_TRUNCATED_TO_8_BITS);
    }

    /**
     * The constructor sets the hash function correctly.
     */
    @Test
    public void constructorSetsTheHashFunctionCorrectly() {
        assertEquals(createNewTruncatedSha256SegmentRepository().getHashFunction(), SHA256_TRUNCATED_TO_8_BITS);
    }

    /**
     * The repository isn't empty after adding a segment.
     */
    @Test
    public void repositoryIsNotEmptyAfterAddingASegment() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertFalse(repository.isEmpty());
    }

    /**
     * The repository isn't full when it has order zero and one segment.
     */
    @Test
    public void repositoryIsNotFullAfterAddingASegment() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertFalse(repository.isFull());
    }

    /**
     * The repository has size one after adding a segment.
     */
    @Test
    public void repositoryHasSizeOneAfterAddingASegment() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertEquals(repository.size(), 1);
    }

    /**
     * The method add returns false if a segment was already present.
     */
    @Test
    public void addReturnsFalseIfSegmentWasAlreadyPresent() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertFalse(repository.add(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * The repository has size one after adding the same segment twice.
     */
    @Test
    public void repositoryHasSizeOneAfterAddingTheSameSegmentTwice() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertEquals(repository.size(), 1);
    }

    /**
     * The method add returns true if a segment is added.
     */
    @Test
    public void addReturnsTrueIfSegmentIsAdded() {
        assertTrue(createNewTruncatedSha256SegmentRepository().add(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * After adding a segment, it can be retrieved by its start point.
     */
    @Test
    public void segmentCanBeRetrievedByItsStartPointAfterAddingIt() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertEquals(repository.getSegmentWithStartPoint(POINT_00), SEGMENT_FOR_POINT_ZERO);
    }

    /**
     * After adding a segment, containsSegmentWithStartPoint returns true for its start point.
     */
    @Test
    public void containsSegmentWithStartPointReturnsTrueAfterAddingASegmentWithThePointAsItsStartPoint() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.containsSegmentWithStartPoint(POINT_00));
    }

    /**
     * After adding a segment, it can be retrieved by its end point.
     */
    @Test
    public void segmentIsIncludedInResultRetrievedByEndPointAfterAddingIt() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * After adding a segment, containsSegmentsWithEndPoint returns true for its end point.
     */
    @Test
    public void containsSegmentsWithEndPointReturnsTrueAfterAddingASegmentWithThePointAsItsEndPoint() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.containsSegmentsWithEndPoint(POINT_6E));
    }

    /**
     * After adding two segments with the same end point, both can be retrieved by their end point.
     */
    @Test
    public void segmentsAreIncludedInResultRetrievedByEndPointAfterAddingThem() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        Segment otherSegment = new Segment(POINT_6E, POINT_6E, 1, 0, SHA256_TRUNCATED_TO_8_BITS);
        repository.add(otherSegment);
        assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(SEGMENT_FOR_POINT_ZERO));
        assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(otherSegment));
    }

    /**
     * The repository rejects segments with the wrong order.
     */
    @Test
    public void repositoryThrowsIllegalArgumentExceptionIfTheSegmentHasTheWrongOrder() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        Segment segment = new Segment(POINT_6E, POINT_6E, 1, 1, SHA256_TRUNCATED_TO_8_BITS);
        assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
    }

    /**
     * The message of the IllegalArgumentException when the segment has the wrong order is correct.
     */
    @Test
    public void messageOfIllegalArgumentExceptionWhenWrongOrderMustBeCorrect() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        Segment segment = new Segment(POINT_6E, POINT_6E, 1, 1, SHA256_TRUNCATED_TO_8_BITS);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
        assertEquals("The order of the segment (1) isn't the same as the order of the repository (0).",
                exception.getMessage());
    }

    /**
     * The repository rejects segments with the wrong hash function.
     */
    @Test
    public void repositoryThrowsIllegalArgumentExceptionIfTheSegmentHasTheWrongHashFunction() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        Segment segment = new Segment(POINT_6E, POINT_6E, 1, 0, StandardHashFunction.SHA256);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
        assertEquals("The hash function of the segment (SHA-256) isn't the same as the hash function of the repository"
                + " (TRUNC(SHA-256, 8)).", exception.getMessage());
    }

    /**
     * The message of the IllegalArgumentException when the segment has the wrong hash function is correct.
     */
    @Test
    public void messageOfIllegalArgumentExceptionWhenWrongHashFunctionMustBeCorrect() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        Segment segment = new Segment(POINT_6E, POINT_6E, 1, 0, StandardHashFunction.SHA256);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
        assertEquals("The hash function of the segment (SHA-256) isn't the same as the hash function of the repository"
                + " (TRUNC(SHA-256, 8)).", exception.getMessage());
    }

    /**
     * The repository rejects incomplete segments.
     */
    @Test
    public void repositoryThrowsIllegalArgumentExceptionIfSegmentIsIncomplete() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.compressToNextOrder();
        Segment segment = new Segment(POINT_00, POINT_FF, 1, 1, SHA256_TRUNCATED_TO_8_BITS);
        assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
    }

    /**
     * The message of the IllegalArgumentException when the segment is incomplete must be correct.
     */
    @Test
    public void messageOfIllegalArgumentExceptionWhenSegmentIsIncompleteMustBeCorrect() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        repository.compressToNextOrder();
        Segment segment = new Segment(POINT_00, POINT_FF, 1, 1, SHA256_TRUNCATED_TO_8_BITS);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
        assertEquals("The segment isn't complete.", exception.getMessage());
    }

    /**
     * The repository is full when it contains all segments that can be found at the given order.
     */
    @Test
    public void repositoryOfOrderSevenIsFullIfItContainsTwoSegmentsForAnEightBitHashfunction() {
        SegmentRepository repository = createNewTruncatedSha256SegmentRepository();
        for (int i = 1; i <= SEVEN; i++) {
            repository.compressToNextOrder();
        }
        repository.add(new Segment(POINT_00, POINT_01, 1, SEVEN, SHA256_TRUNCATED_TO_8_BITS));
        repository.add(new Segment(POINT_01, POINT_00, 1, SEVEN, SHA256_TRUNCATED_TO_8_BITS));
        assertTrue(repository.isFull());
    }

    /**
     * By default, the repository is empty.
     */
    @Test
    public void byDefaultTheRepositoryIsEmpty() {
        assertTrue(createNewTruncatedSha256SegmentRepository().isEmpty());
    }

    /**
     * By default, the repository's size is zero.
     */
    @Test
    public void byDefaultTheRepositoryHasSizeZero() {
        assertEquals(createNewTruncatedSha256SegmentRepository().size(), 0);
    }

    /**
     * If there's no segment with the start point, the method getSegmentWithStartPoint returns null.
     */
    @Test
    public void getSegmentWithStartPointReturnsNullIfStartPointIsAbsent() {
        assertNull(createNewTruncatedSha256SegmentRepository().getSegmentWithStartPoint(POINT_00));
    }

    /**
     * If there's no segment with the start point, the method containsSegmentWithStartPoint returns false.
     */
    @Test
    public void containsSegmentWithStartPointReturnsFalseIfStartPointIsAbsent() {
        assertFalse(createNewTruncatedSha256SegmentRepository().containsSegmentWithStartPoint(POINT_00));
    }

    /**
     * If there's no segment with the end point, the method getSegmentsWithEndPoint returns an empty set.
     */
    @Test
    public void getSegmentsWithEndPointReturnsEmptySetIfEndPointIsAbsent() {
        assertTrue(createNewTruncatedSha256SegmentRepository().getSegmentsWithEndPoint(POINT_6E).isEmpty());
    }

    /**
     * If there's no segment with the end point, the method containsSegmentsWithEndPoint returns false.
     */
    @Test
    public void containsSegmentsWithEndPointReturnsFalseIfEndPointIsAbsent() {
        assertFalse(createNewTruncatedSha256SegmentRepository().containsSegmentsWithEndPoint(POINT_6E));
    }

    /**
     * By default, the repository's order is zero.
     */
    @Test
    public void byDefaultTheRepositoryHasOrderZero() {
        assertEquals(createNewTruncatedSha256SegmentRepository().getOrder(), 0);
    }

    /**
     * An empty repository isn't full.
     */
    @Test
    public void emptyRepositoryIsNotFull() {
        assertFalse(createNewTruncatedSha256SegmentRepository().isFull());
    }

    /**
     * Compressing a repository increases its order.
     */
    public void compressionIncreasesRepositorysOrder() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.compressToNextOrder();
        assertEquals(repository.getOrder(), 1);
    }

    /**
     * Compressing an empty repository results in an empty repository.
     */
    @Test
    public void compressionOfEmptyRepositoryProducesEmptyRepository() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.compressToNextOrder();
        assertTrue(repository.isEmpty());
    }

    /**
     * Compression keeps segments of a higher order.
     */
    @Test
    public void segmentOfHigherOrderIsKeptDuringCompression() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.compressToNextOrder();
        repository.add(new Segment(POINT_20, POINT_21, 1, 1, SHA256));
        repository.compressToNextOrder();
        assertTrue(repository.contains(new Segment(POINT_20, POINT_21, 1, 2, SHA256)));
    }

    /**
     * Compression doesn't keep lower order segments.
     */
    @Test
    public void lowerOrderSegmentsAreRemovedDuringCompression() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FE, POINT_40, 1, 0, SHA256));
        repository.add(new Segment(POINT_FD, POINT_FF, 1, 0, SHA256));
        repository.compressToNextOrder();
        assertTrue(repository.isEmpty());
    }

    /**
     * Compression clears the map with the end points.
     */
    @Test
    public void compressionClearsMapWithEndPoints() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.compressToNextOrder();
        assertFalse(repository.containsSegmentsWithEndPoint(POINT_FF));
    }

    /**
     * Compression combines two lower order segments into a higher segment if possible.
     */
    @Test
    public void compressionCombinesTwoLowerOrderSegmentsIntoAHigherOrderSegment() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FF, POINT_41, 1, 0, SHA256));
        repository.compressToNextOrder();
        assertTrue(repository.contains(new Segment(POINT_40, POINT_41, 2, 1, SHA256)));
    }

    /**
     * Compression combines two lower order segments into a higher segment if possible, but progress after the new
     * distinguished point.
     */
    @Test
    public void compressionStopsCombiningLowerOrderSegmentsAfterFirstHigherOrderDistinguishedPoint() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FF, POINT_41, 1, 0, SHA256));
        repository.add(new Segment(POINT_41, POINT_FE, 1, 0, SHA256));
        repository.compressToNextOrder();
        assertTrue(repository.contains(new Segment(POINT_40, POINT_41, 2, 1, SHA256)));
    }

    /**
     * Compression combines three lower order segments into a higher segment if possible.
     */
    @Test
    public void compressionCombinesThreeLowerOrderSegmentsIntoAHigherOrderSegment() {
        SegmentRepository repository = createNewSha256SegmentRepository();
        repository.add(new Segment(POINT_40, POINT_FF, 1, 0, SHA256));
        repository.add(new Segment(POINT_FF, POINT_FE, 1, 0, SHA256));
        repository.add(new Segment(POINT_FE, POINT_41, 1, 0, SHA256));
        repository.compressToNextOrder();
        assertTrue(repository.contains(new Segment(POINT_40, POINT_41, THREE, 1, SHA256)));
    }
}

package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the class InMemorySegmentRepository.
 */
public class InMemorySegmentRepositoryTest {
    /**
     * The magic number seven.
     */
    private static final int SEVEN = 7;
    /**
     * The hash function SHA-256 truncated to 8 bits.
     */
    private static final HashFunction SHA256_TRUNCATED_TO_8_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA256, 8);
    /**
     * The point 0x00.
     */
    private static final Point POINT_00 = new Point((byte) 0x00);
    /**
     * The point 0x01.
     */
    private static final Point POINT_01 = new Point((byte) 0x01);
    /**
     * The point 0x6E.
     */
    private static final Point POINT_6E = new Point((byte) 0x6e);
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
     * Creates a new, empty segment repository.
     *
     * @return A new, empty segment repository.
     */
    public SegmentRepository createNewSegmentRepository() {
        return new InMemorySegmentRepository(SHA256_TRUNCATED_TO_8_BITS);
    }

    /**
     * The constructor sets the hash function correctly.
     */
    @Test
    public void constructorSetsTheHashFunctionCorrectly() {
        assertEquals(createNewSegmentRepository().getHashFunction(), SHA256_TRUNCATED_TO_8_BITS);
    }

    /**
     * The repository isn't empty after adding a segment.
     */
    @Test
    public void repositoryIsNotEmptyAfterAddingASegment() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertFalse(repository.isEmpty());
    }

    /**
     * The repository isn't full when it has order zero and one segment.
     */
    @Test
    public void repositoryIsNotFullAfterAddingASegment() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertFalse(repository.isFull());
    }

    /**
     * The repository has size one after adding a segment.
     */
    @Test
    public void repositoryHasSizeOneAfterAddingASegment() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertEquals(repository.size(), 1);
    }

    /**
     * The method add returns false if a segment was already present.
     */
    @Test
    public void addReturnsFalseIfSegmentWasAlreadyPresent() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertFalse(repository.add(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * The repository has size one after adding the same segment twice.
     */
    @Test
    public void repositoryHasSizeOneAfterAddingTheSameSegmentTwice() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertEquals(repository.size(), 1);
    }

    /**
     * The method add returns true if a segment is added.
     */
    @Test
    public void addReturnsTrueIfSegmentIsAdded() {
        assertTrue(createNewSegmentRepository().add(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * After adding a segment, it can be retrieved by its start point.
     */
    @Test
    public void segmentCanBeRetrievedByItsStartPointAfterAddingIt() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertEquals(repository.getSegmentWithStartPoint(POINT_00), SEGMENT_FOR_POINT_ZERO);
    }

    /**
     * After adding a segment, containsSegmentWithStartPoint returns true for its start point.
     */
    @Test
    public void containsSegmentWithStartPointReturnsTrueAfterAddingASegmentWithThePointAsItsStartPoint() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.containsSegmentWithStartPoint(POINT_00));
    }

    /**
     * After adding a segment, it can be retrieved by its end point.
     */
    @Test
    public void segmentIsIncludedInResultRetrievedByEndPointAfterAddingIt() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.getSegmentsWithEndPoint(POINT_6E).contains(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * After adding a segment, containsSegmentsWithEndPoint returns true for its end point.
     */
    @Test
    public void containsSegmentsWithEndPointReturnsTrueAfterAddingASegmentWithThePointAsItsEndPoint() {
        SegmentRepository repository = createNewSegmentRepository();
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.containsSegmentsWithEndPoint(POINT_6E));
    }

    /**
     * After adding two segments with the same end point, both can be retrieved by their end point.
     */
    @Test
    public void segmentsAreIncludedInResultRetrievedByEndPointAfterAddingThem() {
        SegmentRepository repository = createNewSegmentRepository();
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
        SegmentRepository repository = createNewSegmentRepository();
        Segment segment = new Segment(POINT_6E, POINT_6E, 1, 1, SHA256_TRUNCATED_TO_8_BITS);
        assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
    }

    /**
     * The message of the IllegalArgumentException when the segment has the wrong order is correct.
     */
    @Test
    public void messageOfIllegalArgumentExceptionWhenWrongOrderMustBeCorrect() {
        SegmentRepository repository = createNewSegmentRepository();
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
        SegmentRepository repository = createNewSegmentRepository();
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
        SegmentRepository repository = createNewSegmentRepository();
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
        SegmentRepository repository = createNewSegmentRepository();
        repository.compressToNextOrder();
        Segment segment = new Segment(POINT_00, POINT_FF, 1, 1, SHA256_TRUNCATED_TO_8_BITS);
        assertThrows(IllegalArgumentException.class, () -> repository.add(segment));
    }

    /**
     * The message of the IllegalArgumentException when the segment is incomplete must be correct.
     */
    @Test
    public void messageOfIllegalArgumentExceptionWhenSegmentIsIncompleteMustBeCorrect() {
        SegmentRepository repository = createNewSegmentRepository();
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
        SegmentRepository repository = createNewSegmentRepository();
        for (int i = 1; i <= SEVEN; i++) {
            repository.compressToNextOrder();
        }
        repository.add(new Segment(POINT_00, POINT_01, 1, SEVEN, SHA256_TRUNCATED_TO_8_BITS));
        repository.add(new Segment(POINT_01, POINT_00, 1, SEVEN, SHA256_TRUNCATED_TO_8_BITS));
        assertTrue(repository.isFull());
    }
}

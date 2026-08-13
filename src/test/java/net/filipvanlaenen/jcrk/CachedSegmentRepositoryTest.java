package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.jcrk.CachedSegmentRepository.Cache;

/**
 * An integration test on the CachedSegmentRepository class.
 */
public class CachedSegmentRepositoryTest {
    /**
     * The point 0x00.
     */
    private static final Point POINT_00 = new Point((byte) 0x00);
    /**
     * The hash function SHA-1 truncated to 1 bit.
     */
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_1_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 1);
    /**
     * The segment starting from point zero.
     */
    private static final Segment SEGMENT_FOR_POINT_ZERO =
            new Segment(POINT_00, POINT_00, 1, 0, SHA1_TRUNCATED_TO_1_BITS);

    /**
     * A local implementation of the Cache interface for testing purposes.
     */
    private final class MyCache implements Cache {
        /**
         * The content of the cache.
         */
        private String[] content = null;

        @Override
        public String[] getContent() throws IOException {
            return content;
        }

        @Override
        public void setContent(final String content) throws IOException {
            this.content = content.split("\n");
        }
    }

    /**
     * A local implementation of the abstract class CachedSegmentRepository for testing purposes.
     */
    private class MyCachedSegmentRepository extends CachedSegmentRepository {
        /**
         * Constructor taking a cache and a hash function as parameters.
         *
         * @param cache        The cache.
         * @param hashFunction The hash function.
         */
        protected MyCachedSegmentRepository(final Cache cache, final HashFunction hashFunction) {
            super(cache, hashFunction);
        }

        /**
         * Constructor taking a hash function as a parameter.
         *
         * @param hashFunction The hash function.
         */
        protected MyCachedSegmentRepository(final HashFunction hashFunction) {
            this(new MyCache(), hashFunction);
        }
    }

    /**
     * Verifies that when there's no cache, a message about that is being logged.
     */
    @Test
    public void shouldLogMessageAboutEmptyCache() {
        ByteArrayOutputStream outputStream = LaconicConfigurator.resetLaconicOutputStream();
        new MyCachedSegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        assertTrue(outputStream.toString().contains("Empty cache."));
    }

    /**
     * Verifies that the add method is wired correctly to the inner repository.
     */
    @Test
    public void addShouldBeWiredCorrectlyToInnerRepository() {
        CachedSegmentRepository repository = new MyCachedSegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        assertTrue(repository.add(SEGMENT_FOR_POINT_ZERO));
        assertFalse(repository.add(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * Verifies that the add method updates the content of the cache correctly.
     *
     * @throws IOException Thrown if an IOException occurs.
     */
    @Test
    public void addShouldUpdateTheContentOfTheCacheCorrectly() throws IOException {
        Cache cache = new MyCache();
        MyCachedSegmentRepository repository = new MyCachedSegmentRepository(cache, SHA1_TRUNCATED_TO_1_BITS);
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertArrayEquals(new String[] {"0", "TRUNC(SHA-1, 1)", "00.00.1"}, cache.getContent());
    }

    /**
     * Verifies that the contains method is wired correctly to the inner repository.
     */
    @Test
    public void containsShouldBeWiredCorrectlyToInnerRepository() {
        CachedSegmentRepository repository = new MyCachedSegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        assertFalse(repository.contains(SEGMENT_FOR_POINT_ZERO));
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.contains(SEGMENT_FOR_POINT_ZERO));
    }

    /**
     * Verifies that the containsSegmentsWithEndPoint method is wired correctly to the inner repository.
     */
    @Test
    public void containsSegmentsWithEndPointShouldBeWiredCorrectlyToInnerRepository() {
        CachedSegmentRepository repository = new MyCachedSegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        assertFalse(repository.containsSegmentsWithEndPoint(POINT_00));
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.containsSegmentsWithEndPoint(POINT_00));
    }

    /**
     * Verifies that the containsSegmentWithStartPoint method is wired correctly to the inner repository.
     */
    @Test
    public void containsSegmentsWithStartPointShouldBeWiredCorrectlyToInnerRepository() {
        CachedSegmentRepository repository = new MyCachedSegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        assertFalse(repository.containsSegmentWithStartPoint(POINT_00));
        repository.add(SEGMENT_FOR_POINT_ZERO);
        assertTrue(repository.containsSegmentWithStartPoint(POINT_00));
    }

    /**
     * Verifies that the compressToNextOrder and getOrder method is wired correctly to the inner repository.
     */
    @Test
    public void compressToNextOrderAndgetOrderShouldBeWiredCorrectlyToInnerRepository() {
        CachedSegmentRepository repository = new MyCachedSegmentRepository(SHA1_TRUNCATED_TO_1_BITS);
        assertEquals(0, repository.getOrder());
        repository.compressToNextOrder();
        assertEquals(1, repository.getOrder());
    }
}

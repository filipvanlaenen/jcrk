package net.filipvanlaenen.jcrk;

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
     * The hash function SHA-1 truncated to 1 bit.
     */
    private static final TruncatedStandardHashFunction SHA1_TRUNCATED_TO_1_BITS =
            new TruncatedStandardHashFunction(StandardHashFunction.SHA1, 1);

    /**
     * A local implementation of the Cache interface for testing purposes.
     */
    private final class MyCache implements Cache {
        @Override
        public String[] getContent() throws IOException {
            return null;
        }

        @Override
        public void setContent(final String content) throws IOException {
        }
    }

    /**
     * A local implementation of the abstract class CachedSegmentRepository for testing purposes.
     */
    private class MyCachedSegmentRepository extends CachedSegmentRepository {
        /**
         * Constructor taking a hash function as a parameter.
         *
         * @param hashFunction The hash function.
         */
        protected MyCachedSegmentRepository(final HashFunction hashFunction) {
            super(new MyCache(), hashFunction);
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
}

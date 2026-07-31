package net.filipvanlaenen.jcrk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A segment repository cached to a file in the file system.
 */
public final class FileBasedSegmentRepository extends CachedSegmentRepository {
    /**
     * A cache backed by a file in the file system.
     */
    static final class FileCache implements Cache {
        /**
         * The path to the cache file.
         */
        private final Path cacheFilePath;

        /**
         * Constructor taking the name of the file in the file system.
         *
         * @param cacheFileName The name of the file in the file system.
         */
        FileCache(final String cacheFileName) {
            cacheFilePath = Paths.get(cacheFileName);
        }

        @Override
        public String[] getContent() throws IOException {
            return Files.readAllLines(cacheFilePath, StandardCharsets.UTF_8).toArray(new String[] {});
        }

        @Override
        public void setContent(final String content) throws IOException {
            Files.writeString(cacheFilePath, content, StandardCharsets.UTF_8);
        }
    }

    /**
     * Constructor using the file name of the cache and the hash function.
     *
     * @param cacheFileName The file name for the cache.
     * @param hashFunction  The hash function.
     */
    public FileBasedSegmentRepository(final String cacheFileName, final HashFunction hashFunction) {
        super(new FileCache(cacheFileName), hashFunction);
    }
}

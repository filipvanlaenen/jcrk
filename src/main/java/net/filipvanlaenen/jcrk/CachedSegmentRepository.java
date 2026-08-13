package net.filipvanlaenen.jcrk;

import java.io.IOException;
import java.util.HexFormat;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.laconic.Laconic;

/**
 * A segment repository cached to a medium.
 */
public abstract class CachedSegmentRepository implements SegmentRepository {
    /**
     * Interface defining a class that acts as the medium for the cache.
     */
    protected interface Cache {
        /**
         * Returns the content of the cache. For a cache backed by a file, this means reading the content of the file.
         * If nothing is cached, <code>null</code> is returned.
         *
         * @return The content of the cache, or <code>null</code>.
         * @throws IOException Thrown if an exception occurs related to IO.
         */
        String[] getContent() throws IOException;

        /**
         * Sets the content of the cache. For a cache backed by a file, this means writing the content to the file.
         *
         * @param content The new content of the cache.
         * @throws IOException Thrown if an exception occurs related to IO.
         */
        void setContent(String content) throws IOException;
    }

    /**
     * The cache object.
     */
    private final Cache cache;
    /**
     * The internal in-memory segment repository.
     */
    private final InMemorySegmentRepository inMemorySegmentRepository;

    /**
     * Constructor taking a cache object and the hash function.
     *
     * @param cache        The cache object.
     * @param hashFunction The hash function.
     */
    protected CachedSegmentRepository(final Cache cache, final HashFunction hashFunction) {
        this.cache = cache;
        inMemorySegmentRepository = new InMemorySegmentRepository(hashFunction);
        loadFromCache();
    }

    @Override
    public final boolean add(final Segment segment) throws IllegalArgumentException {
        boolean result = inMemorySegmentRepository.add(segment);
        try {
            cache.setContent(calculateCacheContent());
        } catch (IOException ioe) {
            Laconic.LOGGER.logError("IOException while trying to write a segment repository to a file: %s",
                    ioe.getMessage());
        }
        return result;
    }

    /**
     * Produces the content for the cache to be written to the file.
     *
     * @return A string with content to be written to the file.
     */
    private String calculateCacheContent() {
        StringBuffer sb = new StringBuffer();
        sb.append(getOrder());
        sb.append("\n");
        sb.append(getHashFunction().toString());
        sb.append("\n");
        for (Segment segment : inMemorySegmentRepository.getSegments()) {
            sb.append(segment.getStartPoint().asHexadecimalString());
            sb.append(".");
            sb.append(segment.getEndPoint().asHexadecimalString());
            sb.append(".");
            sb.append(segment.getLength());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public final void compressToNextOrder() {
        inMemorySegmentRepository.compressToNextOrder();
    }

    @Override
    public final boolean contains(final Segment segment) {
        return inMemorySegmentRepository.contains(segment);
    }

    @Override
    public final boolean containsSegmentsWithEndPoint(final Point point) {
        return inMemorySegmentRepository.containsSegmentsWithEndPoint(point);
    }

    @Override
    public final boolean containsSegmentWithStartPoint(final Point point) {
        return inMemorySegmentRepository.containsSegmentWithStartPoint(point);
    }

    @Override
    public final Collection<Collision> getCollisions() {
        return inMemorySegmentRepository.getCollisions();
    }

    @Override
    public final HashFunction getHashFunction() {
        return inMemorySegmentRepository.getHashFunction();
    }

    @Override
    public final int getOrder() {
        return inMemorySegmentRepository.getOrder();
    }

    @Override
    public final Collection<Segment> getSegmentsWithEndPoint(final Point point) {
        return inMemorySegmentRepository.getSegmentsWithEndPoint(point);
    }

    @Override
    public final Segment getSegmentWithStartPoint(final Point point) {
        return inMemorySegmentRepository.getSegmentWithStartPoint(point);
    }

    @Override
    public final boolean isEmpty() {
        return inMemorySegmentRepository.isEmpty();
    }

    @Override
    public final boolean isFull() {
        return inMemorySegmentRepository.isFull();
    }

    @Override
    public final boolean isPointFull() {
        return inMemorySegmentRepository.isPointFull();
    }

    /**
     * Loads content from a cache file.
     */
    private void loadFromCache() {
        try {
            String[] lines = cache.getContent();
            HashFunction hashFunction = getHashFunction();
            String hashFunctionName = hashFunction.toString();
            if (lines == null) {
                Laconic.LOGGER.logProgress("Empty cache.");
            } else {
                if (lines[1].equals(hashFunctionName)) {
                    int order = Integer.parseInt(lines[0]);
                    inMemorySegmentRepository.setOrder(order);
                    for (int i = 2; i < lines.length; i++) {
                        String[] parts = lines[i].split("\\.");
                        Point startPoint = new Point(HexFormat.of().parseHex(parts[0]));
                        Point endPoint = new Point(HexFormat.of().parseHex(parts[1]));
                        long length = Long.parseLong(parts[2]);
                        Segment segment = new Segment(startPoint, endPoint, length, order, hashFunction);
                        inMemorySegmentRepository.add(segment);
                    }
                    Laconic.LOGGER.logProgress("Loaded %d segments from the cache.", inMemorySegmentRepository.size());
                } else {
                    Laconic.LOGGER.logError("The name of the hash function in the cache file doesn't match the"
                            + " requested hash function name %s.", hashFunctionName);
                }
            }
        } catch (IOException ioe) {
            Laconic.LOGGER.logError("IOException while trying to load a segment repository from a file: %s",
                    ioe.getMessage());
        }
    }

    @Override
    public final void relaxToPreviousOrder() {
        inMemorySegmentRepository.relaxToPreviousOrder();
    }

    @Override
    public final int size() {
        return inMemorySegmentRepository.size();
    }
}

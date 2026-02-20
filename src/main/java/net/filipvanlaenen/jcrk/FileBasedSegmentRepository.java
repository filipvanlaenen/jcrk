package net.filipvanlaenen.jcrk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HexFormat;

import net.filipvanlaenen.kolektoj.Collection;

public final class FileBasedSegmentRepository implements SegmentRepository {
    private final InMemorySegmentRepository inMemorySegmentRepository;
    private final String cacheFileName;

    public FileBasedSegmentRepository(final String cacheFileName, final HashFunction hashFunction) throws IOException {
        this.cacheFileName = cacheFileName;
        this.inMemorySegmentRepository = new InMemorySegmentRepository(hashFunction);
        loadFromCache();
    }

    @Override
    public boolean add(final Segment segment) throws IllegalArgumentException, IOException {
        boolean result = inMemorySegmentRepository.add(segment);
        writeFile(cacheFileName, calculateCacheContent());
        return result;
    }

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
    public void compressToNextOrder() {
        inMemorySegmentRepository.compressToNextOrder();
    }

    @Override
    public boolean contains(final Segment segment) {
        return inMemorySegmentRepository.contains(segment);
    }

    @Override
    public boolean containsSegmentsWithEndPoint(final Point point) {
        return inMemorySegmentRepository.containsSegmentsWithEndPoint(point);
    }

    @Override
    public boolean containsSegmentWithStartPoint(final Point point) {
        return inMemorySegmentRepository.containsSegmentWithStartPoint(point);
    }

    @Override
    public HashFunction getHashFunction() {
        return inMemorySegmentRepository.getHashFunction();
    }

    @Override
    public int getOrder() {
        return inMemorySegmentRepository.getOrder();
    }

    @Override
    public Collection<Segment> getSegmentsWithEndPoint(final Point point) {
        return inMemorySegmentRepository.getSegmentsWithEndPoint(point);
    }

    @Override
    public Segment getSegmentWithStartPoint(final Point point) {
        return inMemorySegmentRepository.getSegmentWithStartPoint(point);
    }

    @Override
    public boolean isEmpty() {
        return inMemorySegmentRepository.isEmpty();
    }

    @Override
    public boolean isFull() {
        return inMemorySegmentRepository.isFull();
    }

    private void loadFromCache() throws IOException {
        try {
            String[] lines = readFile(cacheFileName);
            HashFunction hashFunction = getHashFunction();
            if (lines[1].equals(hashFunction.toString())) {
                int order = Integer.parseInt(lines[0]);
                inMemorySegmentRepository.setOrder(order);
                for (int i = 2; i < lines.length - 1; i++) { // TODO: Remove -1
                    String[] parts = lines[i].split("\\.");
                    Point startPoint = new Point(HexFormat.of().parseHex(parts[0]));
                    Point endPoint = new Point(HexFormat.of().parseHex(parts[1]));
                    long length = Long.parseLong(parts[2]);
                    Segment segment = new Segment(startPoint, endPoint, length, order, hashFunction);
                    inMemorySegmentRepository.add(segment);
                }
            }
        } catch (NoSuchFileException nsfe) {
        }
    }

    /**
     * Utility method to read a file into an array of strings.
     *
     * @param fileName The name of the file to read from.
     * @return The content of the file, as an array of strings.
     * @throws IOException Thrown if an exception occurs related to IO.
     */
    private static String[] readFile(final String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8).toArray(new String[] {});
    }

    @Override
    public int size() {
        return inMemorySegmentRepository.size();
    }

    /**
     * Utility method to write a string to a file.
     *
     * @param fileName The name for the file.
     * @param content  The string to be written to the file.
     * @throws IOException Thrown if an exception occurs related to IO.
     */
    private static void writeFile(final String fileName, final String content) throws IOException {
        Files.writeString(Paths.get(fileName), content, StandardCharsets.UTF_8);
    }

}

package net.filipvanlaenen.jcrk;

import java.math.BigDecimal;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.hash.ModifiableHashMap;

/**
 * An in-memory implementation of the segment repository.
 */
public final class InMemorySegmentRepository implements SegmentRepository {
    /**
     * The hash function for this segment repository.
     */
    private final HashFunction hashFunction;
    /**
     * The segments mapped by their starting point.
     */
    private final ModifiableMap<Point, Segment> startPointMap = new ModifiableHashMap<Point, Segment>();
    /**
     * The segments mapped by their ending point.
     */
    private final ModifiableMap<Point, ModifiableCollection<Segment>> endPointMap =
            new ModifiableHashMap<Point, ModifiableCollection<Segment>>();
    /**
     * The order of the segment repository.
     */
    private int order;
    /**
     * The maximum size of the segment repository.
     */
    private BigDecimal maxSize;

    /**
     * Constructor creating an empty in-memory repository for a hash function.
     *
     * @param hashFunction The hash function for the in-memory segment repository.
     */
    public InMemorySegmentRepository(final HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        this.maxSize = new BigDecimal(2).pow(hashFunction.getBitLength());
    }

    @Override
    public boolean add(final Segment segment) throws IllegalArgumentException {
        if (!segment.isComplete()) {
            throw new IllegalArgumentException("The segment isn't complete.");
        }
        if (segment.getOrder() != order) {
            throw new IllegalArgumentException(
                    String.format("The order of the segment (%d) isn't the same as the order of the repository (%d).",
                            segment.getOrder(), order));
        }
        if (segment.getHashFunction() != hashFunction) {
            throw new IllegalArgumentException(String.format(
                    "The hash function of the segment (%s) isn't the same as the hash function of the repository (%s).",
                    segment.getHashFunction(), hashFunction));
        }
        if (contains(segment)) {
            return false;
        } else {
            startPointMap.put(segment.getStartPoint(), segment);
            if (endPointMap.containsKey(segment.getEndPoint())) {
                endPointMap.get(segment.getEndPoint()).add(segment);
            } else {
                ModifiableCollection<Segment> segments = ModifiableCollection.of(segment);
                endPointMap.put(segment.getEndPoint(), segments);
            }
            return true;
        }
    }

    @Override
    public boolean contains(final Segment segment) {
        return startPointMap.containsValue(segment);
    }

    @Override
    public boolean containsSegmentWithStartPoint(final Point point) {
        return startPointMap.containsKey(point);
    }

    @Override
    public boolean containsSegmentsWithEndPoint(final Point point) {
        return endPointMap.containsKey(point);
    }

    @Override
    public Segment getSegmentWithStartPoint(final Point point) {
        if (startPointMap.containsKey(point)) {
            return startPointMap.get(point);
        } else {
            return null;
        }
    }

    @Override
    public Collection<Segment> getSegmentsWithEndPoint(final Point point) {
        if (endPointMap.containsKey(point)) {
            return endPointMap.get(point);
        } else {
            return Collection.of();
        }
    }

    @Override
    public boolean isEmpty() {
        return startPointMap.isEmpty();
    }

    @Override
    public int size() {
        return startPointMap.size();
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void compressToNextOrder() {
        ModifiableMap<Point, Segment> lowerOrderStartPointMap = new ModifiableHashMap<Point, Segment>(startPointMap);
        order++;
        maxSize = new BigDecimal(2).pow(hashFunction.getBitLength() - order);
        startPointMap.clear();
        endPointMap.clear();
        for (Segment segment : lowerOrderStartPointMap.getValues()) {
            if (segment.getStartPoint().order() >= order) {
                Segment lastSegment = segment;
                long newLength = segment.getLength();
                while (lastSegment.getEndPoint().order() < order
                        && lowerOrderStartPointMap.containsKey(lastSegment.getEndPoint())) {
                    lastSegment = lowerOrderStartPointMap.get(lastSegment.getEndPoint());
                    newLength += lastSegment.getLength();
                }
                if (lastSegment.getEndPoint().order() >= order) {
                    Segment newSegment = new Segment(segment.getStartPoint(), lastSegment.getEndPoint(), newLength,
                            order, hashFunction);
                    add(newSegment);
                }
            }
        }
    }

    @Override
    public HashFunction getHashFunction() {
        return hashFunction;
    }

    Collection<Segment> getSegments() {
        return startPointMap.getValues();
    }

    @Override
    public boolean isFull() {
        return maxSize.equals(new BigDecimal(size()));
    }

    void setOrder(int newOrder) {
        order = newOrder;
        maxSize = new BigDecimal(2).pow(hashFunction.getBitLength() - order);
        startPointMap.clear();
        endPointMap.clear();
    }
}

package net.filipvanlaenen.jcrk;

import net.filipvanlaenen.kolektoj.Collection;

/**
 * Interface defining the behavior of a segment repository. Segment repositories behave loosely as a set of segments,
 * but mapped by start point and the possibility to retrieve segments by end point. In addition, segment repositories
 * can also be compressed to the next order, i.e. the order is increased, and segments are either combined to form
 * segments of the new order, retained if they by themselves already are of the new order, or are removed.
 */
public interface SegmentRepository {

    /**
     * Adds the segment to this repository if it has the same order and hash function, and if it isn't already present.
     *
     * @param segment The segment to be added.
     * @return True if the segment wasn't yet present, false otherwise.
     * @throws IllegalArgumentException Thrown if the segment doesn't have the same order and hash function as the
     *                                  repository.
     */
    boolean add(Segment segment) throws IllegalArgumentException;

    /**
     * Returns true if this repository contains the segment.
     *
     * @param segment The segment whose presence in this repository is to be tested.
     * @return True if the repository contains the segment, false otherwise.
     */
    boolean contains(Segment segment);

    /**
     * Returns true if this repository contains a segment with the point as its start point.
     *
     * @param point The point whose presence as the start point of a segment in this repository is to be tested.
     * @return True if the repository contains a segment with this point as its start point, false otherwise.
     */
    boolean containsSegmentWithStartPoint(Point point);

    /**
     * Returns true if this repository contains segments with the point as its end point.
     *
     * @param point The point whose presence as the end point of at least one segment in this repository is to be
     *              tested.
     * @return True if the repository contains at least one segment with this point as its end point, false otherwise.
     */
    boolean containsSegmentsWithEndPoint(Point point);

    /**
     * Returns the segment having the specified point as its start point, or null if this repository doesn't contain the
     * segment with this point as its start point.
     *
     * @param point The point for which the segment having this point as its start point should be returned.
     * @return The segment having the specified point as its start point, or null if this repository doesn't contain a
     *         segment with this point as its start point.
     */
    Segment getSegmentWithStartPoint(Point point);

    /**
     * Returns a set with the segments having the specified point as their end point, or an empty set if the repository
     * doesn't contain any segment with this point as its end point.
     *
     * @param point The point that should be the end point of the segments returned.
     * @return A set with the segments having the specified point as their end point, or an empty set if this repository
     *         doesn't contain any segments with this point as their end point.
     */
    Collection<Segment> getSegmentsWithEndPoint(Point point);

    /**
     * Returns true if this repository doesn't contain any segments.
     *
     * @return True if this repository doesn't contain any segments.
     */
    boolean isEmpty();

    /**
     * Returns the number of segments in this repository.
     *
     * @return The number of segments in this repository.
     */
    int size();

    /**
     * Returns the current order of the repository.
     *
     * @return The current order of the repository.
     */
    int getOrder();

    /**
     * Compresses the repository to the next order. After compression, the order of the repository will have been
     * increased by one. Segments that already are of the new order, are retained. The other segments will be used to
     * try and form new segments of the new order.
     */
    void compressToNextOrder();

    /**
     * Returns the hash function of the repository.
     *
     * @return The hash function of the repository.
     */
    HashFunction getHashFunction();

    /**
     * Returns true if the segment repository is full, i.e. it contains all segments of the given order.
     *
     * @return True if the segment repository contains all segments of the given order.
     */
    boolean isFull();
}

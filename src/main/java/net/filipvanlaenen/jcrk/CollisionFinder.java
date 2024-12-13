package net.filipvanlaenen.jcrk;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.laconic.Laconic;

/**
 * Class that can find a collision in a hash function provided enough time and that there is a collision.
 */
public class CollisionFinder {
    /**
     * The segment repository.
     */
    private final SegmentRepository segmentRepository;
    /**
     * The segment producer.
     */
    private final SegmentProducer segmentProducer;
    /**
     * The segment repository compression condition.
     */
    private final SegmentRepositoryCompressionCondition segmentRepositoryCompressionCondition;

    /**
     * Creates a collision finder that can find a collision.
     *
     * @param segmentRepository                     The repository to be used for the segments found.
     * @param segmentProducer                       The segment producer describing how new segments should be produced.
     * @param segmentRepositoryCompressionCondition The condition specifying when to compress the segment repository.
     */
    public CollisionFinder(final SegmentRepository segmentRepository, final SegmentProducer segmentProducer,
            final SegmentRepositoryCompressionCondition segmentRepositoryCompressionCondition) {
        this.segmentRepository = segmentRepository;
        this.segmentProducer = segmentProducer;
        this.segmentRepositoryCompressionCondition = segmentRepositoryCompressionCondition;
    }

    /**
     * Finds a collision using the segment repository with the segment producer to add new segments and the segment
     * repository compression condition to determine when to compress the segment repository.
     *
     * @return The first collision found.
     */
    public Collision findCollision() {
        Collision collision = null;
        while (collision == null && !segmentRepository.isFull()) {
            if (segmentRepositoryCompressionCondition.evaluate(segmentRepository)) {
                Laconic.LOGGER.logProgress(String.format(
                        "The segment repository has %d segments of order %d -- going to compress it to the next order.",
                        segmentRepository.size(), segmentRepository.getOrder()));
                segmentRepository.compressToNextOrder();
                Laconic.LOGGER.logProgress(String.format(
                        "Compressed the segment repository to order %d -- %d segments were retained and/or created.",
                        segmentRepository.getOrder(), segmentRepository.size()));
            }
            Point newStartPoint = segmentProducer.findNewStartPoint(segmentRepository);
            if (newStartPoint == null) {
                return null;
            }
            Laconic.LOGGER.logProgress(String.format("Starting on a new segment of order %d with start point %s.",
                    segmentRepository.getOrder(), newStartPoint.asHexadecimalString()));
            Segment newSegment =
                    new Segment(newStartPoint, segmentRepository.getOrder(), segmentRepository.getHashFunction());
            while (!newSegment.isComplete()) {
                newSegment.extend();
            }
            Laconic.LOGGER.logProgress(
                    String.format("Completed a segment of order %d with start point %s, end point %s and length %d.",
                            segmentRepository.getOrder(), newSegment.getStartPoint().asHexadecimalString(),
                            newSegment.getEndPoint().asHexadecimalString(), newSegment.getLength()));
            segmentRepository.add(newSegment);
            Collection<Segment> segmentsWithNewEndPoint =
                    segmentRepository.getSegmentsWithEndPoint(newSegment.getEndPoint());
            if (segmentsWithNewEndPoint.size() > 1) {
                PairOfCollidingSegments collidingSegments = new PairOfCollidingSegments(segmentsWithNewEndPoint);
                Laconic.LOGGER.logProgress("Found two colliding segments with end point %s.",
                        newSegment.getEndPoint().asHexadecimalString());
                collision = collidingSegments.resolveCollidingSegmentsToCollision();
            }
        }
        Laconic.LOGGER.logProgress(String.format("Found a collision."));
        return collision;
    }
}

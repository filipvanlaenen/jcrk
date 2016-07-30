package net.filipvanlaenen.jcrk;

import java.util.Set;

/**
 * Class that can find a collision in a hash function provided enough time and
 * that there is a collision.
 */
public class CollisionFinder {
	private final SegmentRepository segmentRepository;
	private final SegmentProducer segmentProducer;
	private final SegmentRepositoryCompressionCondition segmentRepositoryCompressionCondition;

	/**
	 * Creates a collision finder that can find a collision.
	 * 
	 * @param segmentRepository
	 *            The repository to be used for the segments found.
	 * @param segmentProducer
	 *            The segment producer describing how new segments should be
	 *            produced.
	 * @param segmentRepositoryCompressionCondition
	 *            The condition specifying when to compress the segment
	 *            repository.
	 */
	public CollisionFinder(
			SegmentRepository segmentRepository,
			SegmentProducer segmentProducer,
			SegmentRepositoryCompressionCondition segmentRepositoryCompressionCondition) {
		this.segmentRepository = segmentRepository;
		this.segmentProducer = segmentProducer;
		this.segmentRepositoryCompressionCondition = segmentRepositoryCompressionCondition;
	}

	/**
	 * Finds a collision using the segment repository with the segment producer
	 * to add new segments and the segment repository compression condition to
	 * determine when to compress the segment repository.
	 * 
	 * @return The first collision found.
	 */
	public Collision findCollision() {
		Collision collision = null;
		while (collision == null) {
			if (segmentRepositoryCompressionCondition.evaluate(segmentRepository)) {
				segmentRepository.compressToNextOrder();
			}
			Point newStartPoint = segmentProducer.findNewStartPoint(segmentRepository);
			Segment newSegment = new Segment(newStartPoint, segmentRepository.getOrder(),
					segmentRepository.getHashFunction());
			while (!newSegment.isComplete()) {
				newSegment.extend();
			}
			segmentRepository.add(newSegment);
			Set<Segment> segmentsWithNewEndPoint = segmentRepository.getSegmentsWithEndPoint(newSegment.getEndPoint());
			if (segmentsWithNewEndPoint.size() > 1) {
				Segment[] segmentArray = segmentsWithNewEndPoint.toArray(new Segment[] {});
				PairOfCollidingSegments collidingSegments = new PairOfCollidingSegments(segmentsWithNewEndPoint);
				collision = collidingSegments.resolveCollidingSegmentsToCollision();
			}
		}
		return collision;
	}
}

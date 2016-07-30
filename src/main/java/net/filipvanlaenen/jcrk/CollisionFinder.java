package net.filipvanlaenen.jcrk;

import java.util.Set;

public class CollisionFinder {
	private final SegmentRepository segmentRepository;
	private final SegmentProducer segmentProducer;
	private final SegmentRepositoryCompressionCondition segmentRepositoryCompressionCondition;

	CollisionFinder(
			SegmentRepository segmentRepository,
			SegmentProducer segmentProducer,
			SegmentRepositoryCompressionCondition segmentRepositoryCompressionCondition) {
		this.segmentRepository = segmentRepository;
		this.segmentProducer = segmentProducer;
		this.segmentRepositoryCompressionCondition = segmentRepositoryCompressionCondition;
	}

	Collision findCollision() {
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

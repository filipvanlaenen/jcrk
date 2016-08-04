package net.filipvanlaenen.jcrk;

/**
 * Enumeration defining some segment producers.
 */
public enum SegmentProducer {
	ZeroPointSegmentChainExtension {
		@Override
		Point findNewStartPoint(SegmentRepository segmentRepository) {
			int byteLength = segmentRepository.getHashFunction().getByteLength();
			byte[] bytes = new byte[byteLength];
			Point pointZero = new Point(bytes);
			if (segmentRepository.containsSegmentWithStartPoint(pointZero)) {
				return findEndPointMissingAsStartPoint(segmentRepository, pointZero);
			} else {
				return pointZero;
			}
		}

		private Point findEndPointMissingAsStartPoint(SegmentRepository segmentRepository, Point startPoint) {
			Point newStartPoint = segmentRepository.getSegmentWithStartPoint(startPoint).getEndPoint();
			while (segmentRepository.containsSegmentWithStartPoint(newStartPoint)) {
				newStartPoint = segmentRepository.getSegmentWithStartPoint(newStartPoint).getEndPoint();
				if (newStartPoint.equals(startPoint)) {
					return null;
				}
			}
			return newStartPoint;
		}
	};

	abstract Point findNewStartPoint(SegmentRepository segmentRepository);

}

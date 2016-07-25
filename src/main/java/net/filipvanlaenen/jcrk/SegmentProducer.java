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
			Point newStartPoint = new Point(bytes);
			while (segmentRepository.containsSegmentWithStartPoint(newStartPoint)) {
				newStartPoint = segmentRepository.getSegmentWithStartPoint(newStartPoint).getEndPoint();
			}
			return newStartPoint;
		}
	};

	abstract Point findNewStartPoint(SegmentRepository segmentRepository);

}

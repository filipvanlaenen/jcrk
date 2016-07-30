package net.filipvanlaenen.jcrk;

public enum SegmentRepositoryCompressionCondition {
	SizeLargerThanHalfOrderPowerOfTwo;

	boolean evaluate(SegmentRepository segmentRepository) {
		return segmentRepository.size() > Math.pow(2, (segmentRepository.getOrder() / 2));
	}

}

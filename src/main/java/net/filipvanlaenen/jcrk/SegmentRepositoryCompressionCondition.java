package net.filipvanlaenen.jcrk;

/**
 * Enumeration holding different types of conditions to determine whether a
 * segment repository should be compressed or not.
 */
public enum SegmentRepositoryCompressionCondition {
	SizeLargerThanHalfOrderPowerOfTwo;

	boolean evaluate(SegmentRepository segmentRepository) {
		return segmentRepository.size() > Math.pow(2, (segmentRepository.getOrder() / 2));
	}

}

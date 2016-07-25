package net.filipvanlaenen.jcrk;

public enum SegmentRepositoryCompressionCondition {
	SizeLargerThanHalfOrderPowerOfTwo
	;

	boolean evaluate(SegmentRepository segmentRepository) {
		return false;
	}

}

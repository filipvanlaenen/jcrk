package net.filipvanlaenen.jcrk;

/**
 * Enumeration holding different types of conditions to determine whether a segment repository should be compressed or
 * not.
 */
public enum SegmentRepositoryCompressionCondition {
    /**
     * Condition evaluates to true if the size of the repository is larger than the power of two of half of the order.
     */
    SizeLargerThanHalfOrderPowerOfTwo {
        @Override
        boolean evaluate(final SegmentRepository segmentRepository) {
            return segmentRepository.size() > Math.pow(2, (segmentRepository.getOrder() / 2.0));
        }
    };

    /**
     * Evaluates whether the segment should be compressed.
     *
     * @param segmentRepository The segment repository to be evaluated.
     * @return True if the segment repository should be compressed.
     */
    abstract boolean evaluate(SegmentRepository segmentRepository);
}

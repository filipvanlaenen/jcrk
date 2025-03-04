package net.filipvanlaenen.jcrk;

/**
 * Enumeration defining some segment producers.
 */
public enum SegmentProducer {
    /**
     * Produces new segments starting from point zero. If point zero isn't used as a start point yet, then it will be
     * returned as the next start point. Otherwise, it'll go through the chain of segments starting from point zero to
     * find the last end point.
     */
    ZeroPointSegmentChainExtension {
        @Override
        Point findNewStartPoint(final SegmentRepository segmentRepository) {
            int byteLength = segmentRepository.getHashFunction().getByteLength();
            byte[] bytes = new byte[byteLength];
            Point pointZero = new Point(bytes);
            if (segmentRepository.containsSegmentWithStartPoint(pointZero)) {
                return findEndPointMissingAsStartPoint(segmentRepository, pointZero);
            } else {
                return pointZero;
            }
        }

        /**
         * Finds the first end point that isn't already used as a start point.
         *
         * @param segmentRepository The segment repository.
         * @param startPoint        The start point.
         * @return The first end point that isn't used as a start point, or <code>null</code> otherwise.
         */
        private Point findEndPointMissingAsStartPoint(final SegmentRepository segmentRepository,
                final Point startPoint) {
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

    /**
     * Returns a new start point for the segment repository.
     *
     * @param segmentRepository A segment repository.
     * @return A new start point to produce a segment from.
     */
    abstract Point findNewStartPoint(SegmentRepository segmentRepository);
}

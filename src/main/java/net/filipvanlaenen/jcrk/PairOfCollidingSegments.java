package net.filipvanlaenen.jcrk;

import java.util.Set;

/**
 * Class representing a pair of colliding segments. The pair consists of exactly
 * two segments, which have to produced with the same hash function and share
 * the same end point, but have different start points.
 */
public class PairOfCollidingSegments {
	private final Point endPoint;
	private final HashFunction hashFunction;
	private final Point longSegmentStartPoint;
	private final long longSegmentLength;
	private final Point shortSegmentStartPoint;
	private final long shortSegmentLength;

	PairOfCollidingSegments(Set<Segment> segments) {
		if (segments.size() != 2) {
			throw new IllegalArgumentException(
					String.format("There should be two segments, but found %d.", segments.size()));
		}
		Segment[] pair = segments.toArray(new Segment[] {});
		if (!pair[0].getEndPoint().equals(pair[1].getEndPoint())) {
			throw new IllegalArgumentException(
					createExceptionMessage(pair, "The end points of the two segments aren't equal (0x%s ≠ 0x%s).",
							(SegmentFieldExtraction) ((Segment s) -> s.getEndPoint().asHexadecimalString())));
		}
		if (pair[0].getStartPoint().equals(pair[1].getStartPoint())) {
			throw new IllegalArgumentException(
					createExceptionMessage(pair, "The start points of the two segments are equal (0x%s = 0x%s).",
							(SegmentFieldExtraction) ((Segment s) -> s.getStartPoint().asHexadecimalString())));
		}
		if (!pair[0].getHashFunction().equals(pair[1].getHashFunction())) {
			throw new IllegalArgumentException(
					createExceptionMessage(pair, "The hash functions of the two segments aren't equal (%s ≠ %s).",
							(SegmentFieldExtraction) ((Segment s) -> s.getHashFunction().toString())));
		}
		this.hashFunction = pair[0].getHashFunction();
		this.endPoint = pair[0].getEndPoint();
		Segment longSegment = (pair[0].getLength() < pair[1].getLength()) ? pair[1] : pair[0];
		this.longSegmentLength = longSegment.getLength();
		this.longSegmentStartPoint = longSegment.getStartPoint();
		Segment shortSegment = (pair[0].getLength() < pair[1].getLength()) ? pair[0] : pair[1];
		this.shortSegmentLength = shortSegment.getLength();
		this.shortSegmentStartPoint = shortSegment.getStartPoint();
	}

	interface SegmentFieldExtraction {
		String operation(Segment s);
	}

	private String createExceptionMessage(Segment[] pair, String messageFormat,
			SegmentFieldExtraction segmentFieldExtraction) {
		String segment1Field = segmentFieldExtraction.operation(pair[0]);
		String segment2Field = segmentFieldExtraction.operation(pair[1]);
		return String.format(messageFormat,
				(segment1Field.compareTo(segment2Field) < 0) ? segment1Field : segment2Field,
				(segment1Field.compareTo(segment2Field) > 0) ? segment1Field : segment2Field);
	}

	Collision resolveCollidingSegmentsToCollision() {
		long longSegmentHead = longSegmentLength - shortSegmentLength;
		Point p = longSegmentStartPoint;
		for (int i = 0; i < longSegmentHead; i++) {
			p = p.hash(hashFunction);
		}
		Point shortenedLongSegmentStartPoint = p;
		Point longSegmentPoint = shortenedLongSegmentStartPoint;
		Point nextLongSegmentPoint = longSegmentPoint.hash(hashFunction);
		Point shortSegmentPoint = shortSegmentStartPoint;
		Point nextShortSegmentPoint = shortSegmentPoint.hash(hashFunction);
		long remainingLength = shortSegmentLength;
		while ((!nextLongSegmentPoint.equals(nextShortSegmentPoint)) && (remainingLength > 0)) {
			longSegmentPoint = nextLongSegmentPoint;
			nextLongSegmentPoint = longSegmentPoint.hash(hashFunction);
			shortSegmentPoint = nextShortSegmentPoint;
			nextShortSegmentPoint = shortSegmentPoint.hash(hashFunction);
			remainingLength--;
		}
		return new Collision(hashFunction, longSegmentPoint, shortSegmentPoint);
	}

	Point getEndPoint() {
		return endPoint;
	}

	HashFunction getHashFunction() {
		return hashFunction;
	}
}

package net.filipvanlaenen.jcrk;

import java.util.Set;

/**
 * Class representing a pair of colliding segments.
 */
public class PairOfCollidingSegments {
	private final Point endPoint;

	PairOfCollidingSegments(Set<Segment> segments) {
		if (segments.size() != 2) {
			throw new IllegalArgumentException(
					String.format("There should be two segments, but found %d.", segments.size()));
		}
		Segment[] pair = segments.toArray(new Segment[] {});
		if (!pair[0].getEndPoint().equals(pair[1].getEndPoint())) {
			String endPoint1Hex = pair[0].getEndPoint().asHexadecimalString();
			String endPoint2Hex = pair[1].getEndPoint().asHexadecimalString();
			String exceptionMessage = String.format("The end points of the two segments aren't equal (0x%s ≠ 0x%s).",
					(endPoint1Hex.compareTo(endPoint2Hex) < 0) ? endPoint1Hex : endPoint2Hex,
					(endPoint1Hex.compareTo(endPoint2Hex) > 0) ? endPoint1Hex : endPoint2Hex);
			throw new IllegalArgumentException(exceptionMessage);
		}
		this.endPoint = pair[0].getEndPoint();
		if (pair[0].getStartPoint().equals(pair[1].getStartPoint())) {
			String startPoint1Hex = pair[0].getStartPoint().asHexadecimalString();
			String startPoint2Hex = pair[1].getStartPoint().asHexadecimalString();
			String exceptionMessage = String.format("The start points of the two segments are equal (0x%s = 0x%s).",
					startPoint1Hex, startPoint2Hex);
			throw new IllegalArgumentException(exceptionMessage);
		}
	}

	Collision resolveCollidingSegmentsToCollision() {
		return null;
	}

	Point getEndPoint() {
		return endPoint;
	}
}

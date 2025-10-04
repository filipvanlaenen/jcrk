package net.filipvanlaenen.jcrk;

public final class CyclicSegment {
    private final Point startPoint;
    private final Point cyclicPoint;
    private final HashFunction hashFunction;

    public CyclicSegment(Point startPoint, Point cyclicPoint, Long firstLength, long totalLength,
            HashFunction hashFunction) {
        this.startPoint = startPoint;
        this.cyclicPoint = cyclicPoint;
        this.hashFunction = hashFunction;
    }

    Collision findCollision() {
        Point p = startPoint;
        long firstLength = 0;
        while (!p.equals(cyclicPoint)) {
            p = p.hash(hashFunction);
            firstLength++;
        }
        long cycleLength = 1;
        p = p.hash(hashFunction);
        while (!p.equals(cyclicPoint)) {
            p = p.hash(hashFunction);
            cycleLength++;
        }
        Point headP = startPoint;
        Point cycleP = cyclicPoint;
        if (cycleLength > firstLength) {
            for (int i = 0; i < cycleLength - firstLength; i++) {
                cycleP = cycleP.hash(hashFunction);
            }
        } else {
            for (int i = 0; i < firstLength - cycleLength; i++) {
                headP = headP.hash(hashFunction);
            }
        }
        Point nextHeadPoint = headP.hash(hashFunction);
        Point nextCycleP = cycleP.hash(hashFunction);
        while (!nextHeadPoint.equals(nextCycleP)) {
            headP = nextHeadPoint;
            nextHeadPoint = headP.hash(hashFunction);
            cycleP = nextCycleP;
            nextCycleP = cycleP.hash(hashFunction);
        }
        return new Collision(hashFunction, headP, cycleP);
    }
}

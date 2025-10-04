package net.filipvanlaenen.jcrk;

/**
 * A segment on a path in Pollard's rho collision search that has been found to contain a cycle. A cyclic segment has a
 * start point, a cyclic point and a hash function.
 *
 * @param startPoint   The start point of the cyclic segment.
 * @param cyclicPoint  One of the points on the cyclic segment's cycle.
 * @param hashFunction The hash function for the segment.
 */
public record CyclicSegment(Point startPoint, Point cyclicPoint, HashFunction hashFunction) {
    /**
     * Finds the collision in the cyclic segment.
     *
     * @return The collision in the cycle segment.
     */
    Collision findCollision() {
        Point p = startPoint;
        long startLength = 0;
        while (!p.equals(cyclicPoint)) {
            p = p.hash(hashFunction);
            startLength++;
        }
        long cycleLength = 1;
        p = p.hash(hashFunction);
        while (!p.equals(cyclicPoint)) {
            p = p.hash(hashFunction);
            cycleLength++;
        }
        Point startP = startPoint;
        Point cyclicP = cyclicPoint;
        // EQMU: Changing the conditional boundary below produces an equivalent mutant.
        if (cycleLength > startLength) {
            for (int i = 0; i < cycleLength - startLength; i++) {
                cyclicP = cyclicP.hash(hashFunction);
            }
        } else {
            for (int i = 0; i < startLength - cycleLength; i++) {
                startP = startP.hash(hashFunction);
            }
        }
        Point nextStartP = startP.hash(hashFunction);
        Point nextCyclicP = cyclicP.hash(hashFunction);
        while (!nextStartP.equals(nextCyclicP)) {
            startP = nextStartP;
            nextStartP = startP.hash(hashFunction);
            cyclicP = nextCyclicP;
            nextCyclicP = cyclicP.hash(hashFunction);
        }
        return new Collision(hashFunction, startP, cyclicP);
    }
}

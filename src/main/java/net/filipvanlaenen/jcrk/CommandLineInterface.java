package net.filipvanlaenen.jcrk;

/**
 * Class implementing a command line interface.
 */
public final class CommandLineInterface {
    /**
     * The integer number eight.
     */
    private static final int EIGHT = 8;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CommandLineInterface() {
    }

    /**
     * The main entry point for the command line interface.
     *
     * @param args The arguments.
     */
    public static void main(final String[] args) {
        HashFunction hashFunction = new TruncatedStandardHashFunction(StandardHashFunction.SHA1, EIGHT);
        SegmentRepository segmentRepository = new InMemorySegmentRepository(hashFunction);
        CollisionFinder finder = new CollisionFinder(segmentRepository, SegmentProducer.ZeroPointSegmentChainExtension,
                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
        Collision collision = finder.findCollision();
        System.out
                .println("The following points have the same hash value under the hash function " + hashFunction + ":");
        for (Point point : collision.points()) {
            System.out.println(point.asHexadecimalString());
        }
    }
}

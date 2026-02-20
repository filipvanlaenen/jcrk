package net.filipvanlaenen.jcrk;

import java.io.IOException;

import net.filipvanlaenen.laconic.Laconic;

/**
 * Class implementing a command line interface.
 */
public final class CommandLineInterface {
    /**
     * The magic number eight.
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
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        if (args.length < 1) {
            printUsage();
            return;
        }
        try {
            Command.valueOf(args[0].toUpperCase()).execute(args);
        } catch (IllegalArgumentException iae) {
            printUsage();
        }
    }

    /**
     * Prints the usage to the command line.
     */
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  analyze [<hash-function> [<number-of-bits>]]");
    }

    /**
     * Enumeration with the available commands.
     */
    public enum Command {
        /**
         * Command to read a ROPF file and a YAML file with election specific data, analyze the opinion polls and write
         * the results to a YAML file.
         */
        ANALYZE {
            @Override
            void execute(final String[] args) throws IllegalArgumentException, IOException {
                StandardHashFunction baseHashFunction = StandardHashFunction.SHA1;
                if (args.length > 1) {
                    baseHashFunction = StandardHashFunction.valueOf(args[1].toUpperCase());
                }
                int numberOfBits = EIGHT;
                if (args.length > 2) {
                    numberOfBits = Integer.parseInt(args[2]);
                }
                HashFunction hashFunction = new TruncatedStandardHashFunction(baseHashFunction, numberOfBits);
                String cacheFileName = baseHashFunction.toString() + "-" + numberOfBits + ".rcf";
                SegmentRepository segmentRepository = new FileBasedSegmentRepository(cacheFileName, hashFunction);
                CollisionFinder finder = new CollisionFinder(segmentRepository,
                        SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
                Collision collision = finder.findCollision();
                if (collision == null) {
                    Laconic.LOGGER.logProgress("No collision found.");
                } else {
                    Laconic.LOGGER.logProgress(
                            "The following points have the same hash value under the hash function %s:",
                            hashFunction.toString());
                    Point firstPointFullHash = null;
                    for (Point point : collision.points()) {
                        Point fullHash = point.hash(baseHashFunction);
                        Laconic.LOGGER.logProgress(" - Point: %s", point.asHexadecimalString());
                        Laconic.LOGGER.logProgress("   Truncated hash value: %s",
                                point.hash(hashFunction).asHexadecimalString());
                        Laconic.LOGGER.logProgress("   Full hash value: %s", fullHash.asHexadecimalString());
                        if (firstPointFullHash == null) {
                            firstPointFullHash = fullHash;
                        } else {
                            Laconic.LOGGER.logProgress(
                                    "   Hamming distance of the full hash value to the first point’s full hash value:"
                                            + " %d",
                                    firstPointFullHash.hammingDistanceTo(fullHash));
                        }
                    }
                }
            }
        };

        /**
         * Executes the command, passing the arguments from the command line.
         *
         * @param args The arguments from the command line.
         * @throws IOException 
         * @throws IllegalArgumentException 
         */
        abstract void execute(String[] args) throws IllegalArgumentException, IOException;
    }
}

package net.filipvanlaenen.jcrk;

import org.apache.log4j.Logger;

/**
 * Class implementing a command line interface.
 */
public final class CommandLineInterface {
    /**
     * The magic number eight.
     */
    private static final int EIGHT = 8;
    /**
     * Logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(CommandLineInterface.class);

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
            void execute(final String[] args) {
                StandardHashFunction baseHashFunction = StandardHashFunction.SHA1;
                if (args.length > 1) {
                    baseHashFunction = StandardHashFunction.valueOf(args[1].toUpperCase());
                }
                int numberOfBits = EIGHT;
                if (args.length > 2) {
                    numberOfBits = Integer.parseInt(args[2]);
                }
                HashFunction hashFunction = new TruncatedStandardHashFunction(baseHashFunction, numberOfBits);
                SegmentRepository segmentRepository = new InMemorySegmentRepository(hashFunction);
                CollisionFinder finder =
                        new CollisionFinder(segmentRepository, SegmentProducer.ZeroPointSegmentChainExtension,
                                SegmentRepositoryCompressionCondition.SizeLargerThanHalfOrderPowerOfTwo);
                Collision collision = finder.findCollision();
                LOGGER.info(
                        "The following points have the same hash value under the hash function " + hashFunction + ":");
                for (Point point : collision.points()) {
                    LOGGER.info(point.asHexadecimalString() + ", hash value: "
                            + point.hash(hashFunction).asHexadecimalString() + " "
                            + point.hash(baseHashFunction).asHexadecimalString());
                }
            }
        };

        /**
         * Executes the command, passing the arguments from the command line.
         *
         * @param args The arguments from the command line.
         */
        abstract void execute(String[] args);
    }
}

package net.filipvanlaenen.jcrk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the CommandLineInterface class.
 */
public class CommandLineInterfaceTest {
    /**
     * Verifies correct reporting when no collision can be found.
     */
    @Test
    public void shouldDetectACyclicResultSpace() {
        ByteArrayOutputStream outputStream = LaconicConfigurator.resetLaconicOutputStream();
        CommandLineInterface.Command.ANALYZE.execute(new String[] {"analyze", "SHA224", "3"});
        assertTrue(outputStream.toString().contains("No collision found."));
    }
}

package tests.units;

import main.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReplacingMessage
{
    @Test
    public void testEmptyMessage() throws Exception {
        assertTrue(Messages.replace(0,"") <= 0);
    }
}
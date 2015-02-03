package tests.units;

import junit.framework.TestCase;
import org.junit.Test;
import main.Messages;

public class AddingMessage extends TestCase
{
    @Test
    public void testEmptyMessage() throws Exception {
        assertTrue(Messages.add("","0767731855","0767731855") <= 0);
    }

    @Test
    public void testInvalidSenderId() throws Exception {
        assertTrue(Messages.add("A","42342","0767731855") <= 0);
        assertTrue(Messages.add("A","-123","0767731855") <= 0);
        assertTrue(Messages.add("A","kasd3","0767731855") <= 0);
        assertTrue(Messages.add("A","","0767731855") <= 0);
        assertTrue(Messages.add("A","ööö","0767731855") <= 0);
        assertTrue(Messages.add("A","+0767731855","0767731855") <= 0);
        assertTrue(Messages.add("A","07677318550","0767731855") <= 0);
    }

    @Test
    public void testInvalidRecieverId() throws Exception {
        assertTrue(Messages.add("A", "0767731855", "X") <= 0);
    }

    @Test
    public void testMessageIsAdded() throws Exception {
        int id = Messages.add("Test", "0767731855", "0767731855");
        assertTrue(Messages.exists(id));
    }

    @Test
    public void testGetsUniquePositiveId() throws Exception {
        int id = Messages.add("TestID", "0767731855", "0767731855");
        assertTrue(id > 0);
        int id2 = Messages.add("TestID", "0767731855", "0767731855");
        assertTrue(id2 > 0);
        assertTrue(id != id2);
    }
}
package tests.units;

import main.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeletingMessage {
    @Test
    public void testDeleteUnexistingMessage() throws Exception {
        assertFalse(Messages.exists(0));
        assertTrue(Messages.delete(0) <= 0);
    }

    @Test
    public void testMessageIsRemoved() throws Exception {
        int id = Messages.add("Hello","0767731855","0767731855");
        assertTrue(Messages.exists(id));
        Messages.delete(id);
        assertFalse(Messages.exists(id));
    }

    @Test
    public void testMessageIdReturned(){
        int id = Messages.add("Hello","0767731855","0767731855");
        assertTrue(Messages.exists(id));
        assertTrue(Messages.delete(id) == id);
    }
}
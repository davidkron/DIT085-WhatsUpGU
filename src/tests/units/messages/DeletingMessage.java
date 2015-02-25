package tests.units.messages;

import main.messagestore.IMessageCollection;
import main.messagestore.Messages;
import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class DeletingMessage {

    IMessageCollection messages;

    @Before
    public void init() {
        messages = new Messages();
    }

    //Zero or less is returned when the specified ID does not exist.
    @Test
    public void testDeleteUnexistingMessage() throws Exception {
        assertNull(messages.get(0));
        assertTrue(messages.delete(0) <= 0);
    }

    //Zero or less is returned when the specified ID is in fetched status.
    @Test
    public void testDeleteFetchingMessage() throws Exception {
        int id = messages.add("Hello", "0767731855", "0767731856");
        assertNotNull(messages.get(id));
        messages.fetch("0767731856");
        assertTrue(messages.get(id).isfetching);
        assertTrue(messages.delete(id) <= 0);
    }

    //The message does not exist after removal.
    @Test
    public void testMessageIsRemoved() throws Exception {
        int id = messages.add("Hello", "0767731855", "0767731855");
        assertNotNull(messages.get(id));
        assertFalse(messages.get(id).isfetching);
        messages.delete(id);
        assertNull(messages.get(id));
    }

    //The specified ID is returned on deletion.
    @Test
    public void testMessageIdReturned() {
        int id = messages.add("Hello", "0767731855", "0767731855");
        assertTrue(messages.delete(id) == id);
    }
}
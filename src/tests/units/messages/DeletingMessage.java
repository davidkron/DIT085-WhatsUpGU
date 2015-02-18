package tests.units.messages;

import main.messagestore.IMessageCollection;
import main.messagestore.Messages;
import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class DeletingMessage{

    IMessageCollection IMessageCollection;

    @Before
    public void init(){
        IMessageCollection = new Messages();
    }

    //Zero or less is returned when the specified ID does not exist.
    @Test
    public void testDeleteUnexistingMessage() throws Exception {
        assertNull(IMessageCollection.get(0));
        assertTrue(IMessageCollection.delete(0) <= 0);
    }

    //The message does not exist after removal.
    @Test
    public void testMessageIsRemoved() throws Exception {
        int id = IMessageCollection.add("Hello","0767731855","0767731855");
        assertNotNull(IMessageCollection.get(id));
        assertFalse(IMessageCollection.get(id).isfetching);
        IMessageCollection.delete(id);
        assertNull(IMessageCollection.get(id));
    }

    //The specified ID is returned on deletion.
    @Test
    public void testMessageIdReturned(){
        int id = IMessageCollection.add("Hello","0767731855","0767731855");
        assertTrue(IMessageCollection.delete(id) == id);
    }
}
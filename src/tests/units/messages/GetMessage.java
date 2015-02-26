package tests.units.messages;

import main.messagestore.IMessageCollection;
import main.messagestore.Message;
import main.messagestore.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;


@RunWith(org.junit.runners.JUnit4.class)
public class GetMessage {
    private IMessageCollection messages;

    @Before
    public void setUp() throws Exception {
        messages = new Messages();
    }

    //Null is returned when the specified message has not been added.
    @Test
    public void NotAdded() throws Exception {
        assertNull(messages.get(1));
    }

    //Null is returned when the specified message has been added and later removed.
    @Test
    public void Removed() throws Exception {
        int ID = messages.add("Hi", "0767731855", "0767731855");
        assertTrue(ID > 0);
        assertEquals(messages.delete(ID), ID);
        assertNull(messages.get(ID));
    }

    //A message associated with the specified ID is returned whenever the message has been added and not removed.
    @Test
    public void AddedNotRemoved() throws Exception {
        int ID = messages.add("Hi", "0767731855", "0767731855");
        assertTrue(ID > 0);
        Message m = messages.get(ID);
        assertEquals(ID, m.id);
    }


}
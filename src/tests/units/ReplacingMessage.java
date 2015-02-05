package tests.units;

import junit.framework.TestCase;
import main.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class ReplacingMessage {

    Messages messages;

    @Before
    public void init(){
        messages = new Messages();
    }


    //Zero or less is returned when message is empty
    @Test
    public void testEmptyMessage() throws Exception {
        int id = messages.add("Hej", "0767731855", "0767731855");
        assertTrue(id >= 0);
        assertTrue(messages.replace(id, "") <= 0);
    }

    //Zero or less is returned when message with specified id does not exist.
    @Test
    public void testNonExisting() throws Exception {
        assertNull(messages.get(1));
        assertTrue(messages.replace(1, "Hello") <= 0);
    }

    //The specified message ID is returned
    @Test
    public void testSpecifiedIdReturned() throws Exception {
        String reciever = "0767731855";
        int id = messages.add("Hej","0767731855",reciever);
        assertTrue(id >= 0);
        assertEquals(messages.replace(id, "Hejdå"),id);
    }

    //The message is replaced on success
    @Test
    public void testMessageReplaced() throws Exception {
        String reciever = "0767731855";
        int id = messages.add("Hej","0767731855",reciever);
        assertTrue(id >= 0);
        String inputString = "Hello";
        assertTrue(messages.replace(id, inputString) >= 0);
        assertEquals(messages.get(id).text, inputString);
    }
}
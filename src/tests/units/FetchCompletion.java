package tests.units;

import main.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(org.junit.runners.JUnit4.class)
public class FetchCompletion {

    Messages messages;

    @Before
    public void setUp(){
        messages = new Messages();
    }

    //A non positive number is returned when a fetch has not been made by the recipient.
    @Test
    public void testWithoutFetchFirst() throws Exception {
        assertTrue(messages.fetchComplete("0767731855") <= 0);
    }

    // A non positive number is returned when the last fetch by the recipient was unsuccessful and did not return any messages.
    @Test
    public void testWithUnsuccessfulFetchFirst() throws Exception {
        messages.fetch("0767731855");
        assertTrue(messages.fetchComplete("0767731855") <= 0);
    }

    // A positive number is returned when a successful fetch has been made by the recipient.
    @Test
    public void testWithSuccessfulFetchFirst() throws Exception {
        int id = messages.add("Test", "0767731855", "0767731855");
        assertTrue(messages.get(id) != null);
        messages.fetch("0767731855");
        assertTrue(messages.fetchComplete("0767731855") > 0);
    }

    //All messages with the specified recipient ID are removed from the server when a fetch has been made.
    @Test
    public void testFetchedRemoved() throws Exception {
        int id = messages.add("Test", "0767731855", "0767731855");
        assertTrue(messages.get(id) != null);
        messages.fetch("0767731855");
        assertTrue(messages.fetchComplete("0767731855") > 0);
        assertTrue(messages.get(id) == null);
    }

    //The sender of each message is signalled to mark the message as seen when a fetch has been made.
    @Test
    public void testSignaledSeen() throws Exception {
        String senderId = "0222222222";
        String recieverId = "0111111111";
        int id = messages.add("Test",senderId , recieverId);
        assertTrue(messages.get(id) != null);
        messages.fetch(recieverId);
        assertTrue(messages.fetchComplete(recieverId) > 0);
        assertTrue(messages.getSeen(senderId).contains(id));
    }
}
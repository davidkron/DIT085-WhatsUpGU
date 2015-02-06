package tests.units;

import junit.framework.TestCase;
import main.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class FetchCompletion {

    Messages messages;

    @Before
    public void setUp(){
        messages = new Messages();
    }

    @Test
    public void testWithoutFetchFirst() throws Exception {
        assertTrue(messages.fetchcomplete("0767731855") <= 0);
    }

    @Test
    public void testWithFetchFirst() throws Exception {
        messages.fetch("0767731855");
        assertTrue(messages.fetchcomplete("0767731855") > 0);
    }

    @Test
    public void testFetchedRemoved() throws Exception {
        int id = messages.add("Test", "0767731855", "0767731855");
        assertTrue(messages.get(id) != null);
        messages.fetch("0767731855");
        assertTrue(messages.fetchcomplete("0767731855") > 0);
        assertTrue(messages.get(id) == null);
    }

    @Test
    public void testSignaledSeen() throws Exception {
        String senderId = "0222222222";
        String recieverId = "0111111111";
        int id = messages.add("Test",senderId , recieverId);
        assertTrue(messages.get(id) != null);
        messages.fetch(recieverId);
        assertTrue(messages.fetchcomplete(recieverId) > 0);
        assertTrue(messages.getSeen(senderId).contains(id));
    }
}
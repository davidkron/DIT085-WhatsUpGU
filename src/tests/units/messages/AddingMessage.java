package tests.units.messages;

import main.messagestore.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class AddingMessage {
    private main.messagestore.IMessageCollection IMessageCollection;

    @Before
    public void init() {
        IMessageCollection = new Messages();
    }

    @Test
    public void testEmptyMessage() throws Exception {
        assertTrue(IMessageCollection.add("", "0767731855", "0767731855") <= 0);
    }

    @Test
    public void testInvalidSenderId() throws Exception {
        assertTrue(IMessageCollection.add("A", "42342", "0767731855") <= 0);
        assertTrue(IMessageCollection.add("A", "-123", "0767731855") <= 0);
        assertTrue(IMessageCollection.add("A", "kasd3", "0767731855") <= 0);
        assertTrue(IMessageCollection.add("A", "", "0767731855") <= 0);
        assertTrue(IMessageCollection.add("A", "ööö", "0767731855") <= 0);
        assertTrue(IMessageCollection.add("A", "+0767731855", "0767731855") <= 0);
        assertTrue(IMessageCollection.add("A", "07677318550", "0767731855") <= 0);
    }

    @Test
    public void testInvalidRecieverId() throws Exception {

        assertTrue(IMessageCollection.add("A", "0767731855", "X") <= 0);
    }

    @Test
    public void testMessageIsAdded() throws Exception {

        int id = IMessageCollection.add("Test", "0767731855", "0767731855");
        assertTrue(IMessageCollection.get(id) != null);
        assertEquals(IMessageCollection.get(id).text, "Test");
    }

    @Test
    public void testGetsUniquePositiveId() throws Exception {

        int id = IMessageCollection.add("TestID", "0767731855", "0767731855");
        assertTrue(id > 0);
        int id2 = IMessageCollection.add("TestID", "0767731855", "0767731855");
        assertTrue(id2 > 0);
        assertTrue(id != id2);
    }

    @Test
    public void testNotFetching() throws Exception {

        int id = IMessageCollection.add("TestID", "0767731855", "0767731855");
        assertFalse(IMessageCollection.get(id).isfetching);
    }


}
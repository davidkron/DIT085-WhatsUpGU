package tests.units;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import main.Messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class AddingMessage{
    Messages messages;

    @Before
    public void init(){
        messages = new Messages();
    }

    @Test
    public void testEmptyMessage() throws Exception {
        assertTrue(messages.add("","0767731855","0767731855") <= 0);
    }

    @Test
    public void testInvalidSenderId() throws Exception {
        assertTrue(messages.add("A", "42342", "0767731855") <= 0);
        assertTrue(messages.add("A","-123","0767731855") <= 0);
        assertTrue(messages.add("A","kasd3","0767731855") <= 0);
        assertTrue(messages.add("A","","0767731855") <= 0);
        assertTrue(messages.add("A","ööö","0767731855") <= 0);
        assertTrue(messages.add("A","+0767731855","0767731855") <= 0);
        assertTrue(messages.add("A","07677318550","0767731855") <= 0);
    }

    @Test
    public void testInvalidRecieverId() throws Exception {
        
        assertTrue(messages.add("A", "0767731855", "X") <= 0);
    }

    @Test
    public void testMessageIsAdded() throws Exception {
        
        int id = messages.add("Test", "0767731855", "0767731855");
        assertTrue(messages.get(id) != null);
        assertEquals(messages.get(id).text,"Test");
    }

    @Test
    public void testGetsUniquePositiveId() throws Exception {
        
        int id = messages.add("TestID", "0767731855", "0767731855");
        assertTrue(id > 0);
        int id2 = messages.add("TestID", "0767731855", "0767731855");
        assertTrue(id2 > 0);
        assertTrue(id != id2);
    }

    @Test
    public void testNotFetching() throws Exception {
        
        int id = messages.add("TestID", "0767731855", "0767731855");
        assertFalse(messages.get(id).isfetching);
    }



}
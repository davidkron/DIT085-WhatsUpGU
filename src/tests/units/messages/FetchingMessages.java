package tests.units.messages;

import main.messagestore.IMessageCollection;
import main.messagestore.Message;
import main.messagestore.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(org.junit.runners.JUnit4.class)
public class FetchingMessages {

    IMessageCollection IMessageCollection;

    @Before
    public void init() {
        IMessageCollection = new Messages();
    }


    NodeList parseXMLString(String string) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(string));
        return db.parse(is).getDocumentElement().getChildNodes();
    }

    //The xml string does not contain any message nodes when no message with the specified receipent ID exist.
    @Test
    public void testFetchMessageNotExists() throws Exception {
        List<Message> messages = IMessageCollection.fetch("0767731855");
        assertTrue(messages.size() == 0);// DOES NOT CONTAIN ANY messages
    }

    //The xml string does not contain any message nodes when all messages with specified recipient id are set to fetching status.
    @Test
    public void testFetchingMessageNotFetched() throws Exception {
        String senderId = "0767731855";

        for (int i = 0; i < 10; i++) {
            int id = IMessageCollection.add("TestID", senderId, "0767731855");
            assertFalse(IMessageCollection.get(id).isfetching);
        }

        // FETCH TWICE
        IMessageCollection.fetch("0767731855");
        List<Message> messages = IMessageCollection.fetch("0767731855");
        assertTrue(messages.size() == 0);// DOES NOT CONTAIN ANY messages
    }

    @Test
    public void testSetToFetchingStatus() throws Exception {

        String senderId = "0767731855";

        int messageIds[] = new int[10];

        for (int i = 0; i < 10; i++) {
            int id = IMessageCollection.add("TestID", senderId, "0767731855");
            assertFalse(IMessageCollection.get(id).isfetching);
            messageIds[i] = id;
        }

        IMessageCollection.fetch(senderId);

        for (int i = 0; i < 10; i++) {
            assertTrue(IMessageCollection.get(messageIds[i]).isfetching);
        }
    }

    //The xml string contains message nodes when at least one unfetched message with the specified ID exists.
    @Test
    public void testContainsMessageOnSuccess() throws Exception {
        String senderId = "0767731855";
        String message = "Hello";

        for (int i = 0; i < 10; i++) {
            int id = IMessageCollection.add(message, senderId, "0767731855");
            assertFalse(IMessageCollection.get(id).isfetching);
        }

        List<Message> messages = IMessageCollection.fetch("0767731855");
        assertEquals(messages.size(), 10);// CONTAINS messages


        for (Message m : messages) {
            assertEquals(m.text,message);
        }
    }

    //The xml string contains message nodes when at least one unfetched message with the specified ID exists.
    @Test
    public void testContainsRightSender() throws Exception {
        String senderId = "0767731855";
        String message = "Hello";

        int messageIds[] = new int[10];

        for (int i = 0; i < 10; i++) {
            int id = IMessageCollection.add(message, senderId, "0767731855");
            assertFalse(IMessageCollection.get(id).isfetching);
            messageIds[i] = id;
        }

        List<Message> messages = IMessageCollection.fetch("0767731855");

        for (Message m : messages) {
            assertEquals(m.senderId,senderId);
        }
    }

    @Test
    public void testContainsRightID() throws Exception {
        String senderId = "0767731855";
        String message = "Hello";

        List<Integer> messageIds = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            int id = IMessageCollection.add(message, senderId, "0767731855");
            assertFalse(IMessageCollection.get(id).isfetching);
            messageIds.add(id);
        }

        List<Message> messages = IMessageCollection.fetch("0767731855");

        for (Message m : messages) {
            assert (messageIds.contains(m.id));
        }
    }


}
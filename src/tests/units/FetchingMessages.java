package tests.units;

import junit.framework.TestCase;
import main.Messages;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

@RunWith(org.junit.runners.JUnit4.class)
public class FetchingMessages{

    Messages messages;

    @Before
    public void init(){
        messages = new Messages();
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
        String xmlreturn = messages.fetch("0767731855");
        NodeList nodeList = parseXMLString(xmlreturn);;
        assertTrue(nodeList.getLength() == 0);// THE XML STRING DOES NOT CONTAIN ANY messages
    }

    //The xml string does not contain any message nodes when all messages with specified recipient id are set to fetching status.
    @Test
    public void testFetchingMessageNotFetched() throws Exception {
        String senderId = "0767731855";

        int messageIds[] = new int[10];

        for(int i = 0; i <10; i++){
            int id = messages.add("TestID", senderId, "0767731855");
            assertFalse(messages.get(id).isfetching);
            messageIds[i] = id;
        }

        // FETCH TWICE
        messages.fetch("0767731855");
        String xmlreturn = messages.fetch("0767731855");

        NodeList nodeList = parseXMLString(xmlreturn);// THE XML STRING IS VALID
        assertTrue(nodeList.getLength() == 0);// THE XML STRING DOES NOT CONTAIN ANY messages
    }

    @Test
    public void testSetToFetchingStatus() throws Exception {

        String senderId = "0767731855";

        int messageIds[] = new int[10];

        for(int i = 0; i <10; i++){
            int id = messages.add("TestID", senderId, "0767731855");
            assertFalse(messages.get(id).isfetching);
            messageIds[i] = id;
        }

        messages.fetch(senderId);

        for(int i = 0; i <10; i++){
            assertTrue(messages.get(messageIds[i]).isfetching);
        }
    }

    //The xml string contains message nodes when at least one unfetched message with the specified ID exists.
    @Test
    public void testContainsMessageOnSuccess() throws Exception {
        String senderId = "0767731855";
        String message = "Hello";

        int messageIds[] = new int[10];

        for(int i = 0; i <10; i++){
            int id = messages.add(message, senderId, "0767731855");
            assertFalse(messages.get(id).isfetching);
            messageIds[i] = id;
        }

        String xmlreturn = messages.fetch("0767731855");
        NodeList nodeList = parseXMLString(xmlreturn);// THE XML STRING IS VALID
        assertTrue(nodeList.getLength() == 0);// THE XML STRING DOES NOT CONTAIN ANY messages
        for(int i = 0; i < nodeList.getLength(); i++){
            assertTrue(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE);
            if(nodeList.item(i).getNodeName().equals("Message")){
                assertEquals(nodeList.item(i).getFirstChild().getNodeValue(),message);
            }
        }
    }

    //The xml string contains message nodes when at least one unfetched message with the specified ID exists.
    @Test
    public void testContainsRightSender() throws Exception {
        String senderId = "0767731855";
        String message = "Hello";

        int messageIds[] = new int[10];

        for(int i = 0; i <10; i++){
            int id = messages.add(message, senderId, "0767731855");
            assertFalse(messages.get(id).isfetching);
            messageIds[i] = id;
        }

        String xmlreturn = messages.fetch("0767731855");
        NodeList nodeList = parseXMLString(xmlreturn);// THE XML STRING IS VALID
        assertTrue(nodeList.getLength() == messageIds.length);// THE XML STRING DOES NOT CONTAIN ANY messages
        for(int i = 0; i < nodeList.getLength(); i++){
            assertTrue(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE);
            if(nodeList.item(i).getNodeName().equals("Sender")){
                assertEquals(nodeList.item(i).getFirstChild().getNodeValue(),senderId);
            }
        }
    }

    @Test
    public void testContainsRightID() throws Exception {
        String senderId = "0767731855";
        String message = "Hello";

        List<Integer> messageIds = new ArrayList<Integer>();

        for(int i = 0; i <10; i++){
            int id = messages.add(message, senderId, "0767731855");
            assertFalse(messages.get(id).isfetching);
            messageIds.add(id);
        }

        String xmlreturn = messages.fetch("0767731855");
        NodeList nodeList = parseXMLString(xmlreturn);// THE XML STRING IS VALID
        assertTrue(nodeList.getLength() == messageIds.size());// THE XML STRING DOES NOT CONTAIN ANY messages
        for(int i = 0; i < nodeList.getLength(); i++){
            assertTrue(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE);
            if(nodeList.item(i).getNodeName().equals("Id")){
                int ID = Integer.parseInt(nodeList.item(i).getFirstChild().getNodeValue());
                assert(messageIds.contains(ID));
            }
        }
    }



}
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.Assert.*;

public class ReplacingMessage {

    //Zero or less is returned when message is empty
    @Test
    public void testEmptyMessage() throws Exception {
        int id = Messages.add("Hej","0767731855","0767731855");
        assertTrue(id >= 0);
        assertTrue(Messages.replace(id, "") <= 0);
    }

    //Zero or less is returned when message with specified id does not exist.
    @Test
    public void testNonExisting() throws Exception {
        assertNull(Messages.get(1));
        assertTrue(Messages.replace(1, "Hello") <= 0);
    }

    //The specified message ID is returned
    @Test
    public void testSpecifiedIdReturned() throws Exception {
        String reciever = "0767731855";
        int id = Messages.add("Hej","0767731855",reciever);
        assertTrue(id >= 0);
        assertEquals(Messages.replace(id, "Hejdå"),id);
    }


    //The message is replaced on success
    @Test
    public void testMessageReplaced() throws Exception {
        String reciever = "0767731855";
        int id = Messages.add("Hej","0767731855",reciever);
        assertTrue(id >= 0);
        String inputString = "Hello";
        assertTrue(Messages.replace(id, inputString) >= 0);
        assertEquals(Messages.get(id).text, inputString);

        /*String xmlreturn = Messages.fetch(reciever);

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlreturn));

        Document doc = db.parse(is);

        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if(currentNode.getNodeName().equals("Message")){
                assertEquals(currentNode.getFirstChild().getNodeValue(), inputString);
            }
        }*/

    }
}
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;

import static org.junit.Assert.*;

public class FetchingMessages {

    //The xml string does not contain any message nodes when no message with the specified receipent ID exist.
    @Test
    public void testFetchMessageNotExists() throws Exception {
        String xmlreturn = Messages.fetch("0767731855");

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlreturn));
        Document doc = db.parse(is);// THE XML STRING IS VALID

        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        assertTrue(nodeList.getLength() == 0);// THE XML STRING DOES NOT CONTAIN ANY MESSAGES
    }

    @Test
    public void testSetToFetchingStatus() throws Exception {

        String senderId = "0767731855";

        int messageIds[] = new int[10];

        for(int i = 0; i <10; i++){
            int id = Messages.add("TestID", senderId, "0767731855");
            assertFalse(Messages.get(id).isfetching);
            messageIds[i] = id;
        }

        Messages.fetch(senderId);

        for(int i = 0; i <10; i++){
            assertTrue(Messages.get(messageIds[i]).isfetching);
        }
    }


}
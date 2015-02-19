package tests.units.server;

import main.server.response.Response;
import main.server.response.XMLEncoder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class XMLEncoderTest {

    @Test
    public void testRefusedConnection() throws Exception {

    }

    @Test
    public void testAcceptedConnection() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(Response.Connected(ID));
        String acceptString = "<Accepted connection from  " + ID + " +/>";
        assertEquals(xml, acceptString);
    }

    @Test
    public void testAddMessage() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(Response.Connected(ID));
        String acceptRegex = "<Message added: \"d+\" />";
        assertTrue(xml.matches(acceptRegex));
    }



        /*

        DELETE:

        String requestString = "<DelMessage>\n" +
                                "<MsgID \"+ messageID +\" />\n" +
                                "</DelMessage>";*/
}
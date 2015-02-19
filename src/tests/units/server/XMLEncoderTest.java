package tests.units.server;

import main.server.response.Response;
import main.server.response.ResponseKind;
import main.server.response.XMLEncoder;
import org.junit.Test;

public class XMLEncoderTest {

    @Test
    public void testRefusedConnection() throws Exception {

    }

    @Test
    public void testAcceptedConnection() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(new Response(ResponseKind.ACCEPTEDCONNECTION,ID));
        String acceptString = "<Accepted connection from  " + ID + " +/>";
    }

    @Test
    public void testAddMessage() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(new Response(ResponseKind.ACCEPTEDCONNECTION,ID));
        String acceptRegex = "<Message added: \"d+\" />";
    }



        /*

        DELETE:

        String requestString = "<DelMessage>\n" +
                                "<MsgID \"+ messageID +\" />\n" +
                                "</DelMessage>";*/
}
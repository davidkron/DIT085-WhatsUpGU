package tests.units.server;

import main.server.response.Response;
import main.server.response.XMLEncoder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class XMLEncoderTest {

    @Test
    public void testRefusedConnection() throws Exception {

    }

    @Test
    public void testAcceptedConnection() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(Response.Connected(ID));
        String acceptString = "<connection><accepted>" + ID + "</accepted></connection>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testAddMessage() throws Exception {
        Integer ID = 5;
        String xml = XMLEncoder.Added(Response.Added(ID));
        String acceptString = "<messageActionResponse><added>" + ID + "</added></messageActionResponse>";
        assertEquals(acceptString,xml);
    }


    @Test
    public void testDeleteMessage() throws Exception {
        Integer ID = 5;
        String xml = XMLEncoder.Added(Response.Deleted(ID));
        String acceptString = "<deleted>" + ID+ "</deleted>";
        assertEquals(acceptString,xml);
    }
        /*

        DELETE:

        String requestString = "<DelMessage>\n" +
                                "<MsgID \"+ messageID +\" />\n" +
                                "</DelMessage>";*/
}
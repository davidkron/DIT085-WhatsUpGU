package tests.units.server;

import main.messagestore.Message;
import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLEncoder;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class XMLEncoderTest {

    @Test
    public void testError() throws Exception {
        RequestObject errorRequest = new RequestObject(ActionKind.ADD);
        String errorMessage = "Errormessage";
        errorRequest.Error = errorMessage;
        String xml = XMLEncoder.encode(errorRequest);
        assertEquals("<error>" + errorMessage + "</error>",xml);
    }

    @Test
    public void testAcceptedConnection() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.encode(RequestObject.ConnectRequest(ID));
        String acceptString = "<connection><accepted>" + ID + "</accepted></connection>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testAdd() throws Exception {
        RequestObject requestObject = new RequestObject(ActionKind.ADD);
        requestObject.messageID = 5;
        String xml = XMLEncoder.encode(requestObject);
        String acceptString = "<messageActionResponse><added>" + requestObject.messageID + "</added></messageActionResponse>";
        assertEquals(acceptString,xml);
    }


    @Test
    public void testDelete() throws Exception {
        Integer ID = 5;
        String xml = XMLEncoder.encode(RequestObject.DeleteRequest(ID));

        String acceptString = "<delete><deleted>" + ID+ "</deleted></delete>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testFetch() throws Exception {
        Integer messageId = 5;
        String receiverID = "0722353472";
        String senderID = "0722353472";
        String content = "Hello";
        RequestObject response = RequestObject.FetchRequest(receiverID);
        response.fetchedMessages = new ArrayList<Message>();
        response.fetchedMessages.add(new Message(content, messageId, senderID, receiverID));

        String xml = XMLEncoder.encode(response);
        String responseReceiverID = "<receiverID>" +  receiverID +  "</receiverID>";
        String responseSenderID = "<senderID>"  + senderID+ "</senderID>";
        String responseContent = "<content>"  + content+ "</content>";
        String RequestObject = "<fetched><message>" + responseReceiverID+ responseSenderID + responseContent+ "</message></fetched>";

        assertEquals(RequestObject, xml);
    }
    @Test
    public void testFetchComplete() throws Exception {
        assertTrue(false);
    }
}
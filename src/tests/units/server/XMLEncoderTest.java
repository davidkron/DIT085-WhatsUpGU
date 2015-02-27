package tests.units.server;

import main.messagestore.Message;
import main.server.request.ActionKind;
import main.server.request.RequestCreator;
import main.server.request.RequestObject;
import main.server.request.XMLEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class XMLEncoderTest {
    @Before
    public void setUp() throws Exception {
        // SILENCE COVERAGE FOR STATIC OBJECTS
        new XMLEncoder();
    }

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
        String xml = XMLEncoder.encode(RequestCreator.ConnectRequest(ID));
        String acceptString = "<connection><accepted>" + ID + "</accepted></connection>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testAdd() throws Exception {
        RequestObject requestObject = new RequestObject(ActionKind.ADD);
        requestObject.messageID = 5;
        String xml = XMLEncoder.encode(requestObject);
        String acceptString = "<added>" + requestObject.messageID + "</added>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testReplaced() throws Exception {
        RequestObject requestObject = new RequestObject(ActionKind.REPLACE);
        requestObject.messageID = 5;
        String xml = XMLEncoder.encode(requestObject);
        String acceptString = "<replaced>" + requestObject.messageID + "</replaced>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testDelete() throws Exception {
        Integer ID = 5;
        String xml = XMLEncoder.encode(RequestCreator.DeleteRequest(ID));

        String acceptString = "<deleted>" + ID+ "</deleted>";
        assertEquals(acceptString,xml);
    }

    @Test
    public void testFetch() throws Exception {
        Integer messageId = 5;
        String receiverID = "0722353472";
        String senderID = "0722353472";
        String content = "Hello";
        RequestObject response = RequestCreator.FetchRequest(receiverID);
        response.fetchedMessages = new ArrayList<>();
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
        String receiverID = "0722353472";
        RequestObject response = RequestCreator.FetchComplete(receiverID);
        response.content = "true";
        String xml = XMLEncoder.encode(response);
        String RequestObject = "<fetchCompleted>true</fetchCompleted>";

        assertEquals(RequestObject, xml);
    }

    @Test
    public void testFailEncode() throws Exception {
        RequestObject response = RequestCreator.InvalidRequest();

        String xml = XMLEncoder.encode(response);
        String RequestObject = "<error>Invalid request.</error>";

        assertEquals(RequestObject, xml);
    }
}
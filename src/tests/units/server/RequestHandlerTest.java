package tests.units.server;

import main.server.response.Response;
import main.ServerState;
import main.server.request.RequestMessage;
import main.server.response.ResponseKind;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class RequestHandlerTest {

    ServerState serverState;
    String senderID = "0767731855";
    String receiverID = "0765861635";
    RequestMessage connectRequest = RequestMessage.ConnectRequest(senderID);

    @Before
    public void setUp() throws Exception {
        serverState = new ServerState();
    }

    //
    @Test
    public void testHandleRequest() throws Exception {
        Response returnmessage = serverState.handlerequest(connectRequest);
        assertTrue(returnmessage.kind == ResponseKind.ACCEPTEDCONNECTION);
        assertTrue(returnmessage.ID.equals(senderID));
    }

    //Connecting multiple times should fail
    @Test
    public void testConnectTwice() {
        serverState.handlerequest(connectRequest);
        Response returnmessage = serverState.handlerequest(connectRequest );
        assertTrue(returnmessage.kind == ResponseKind.REFUSEDCONNECTION);
    }

    @Test
    public void testAddMessage() {
        Response returnmessage = serverState.handlerequest(RequestMessage.AddRequest("HEllo", senderID, receiverID));
        assertTrue(returnmessage.kind == ResponseKind.ADDEDMESSAGE);
    }

    @Test
    public void testAddInvalidMessage() {
        Response returnmessage = serverState.handlerequest(RequestMessage.AddRequest("", senderID, receiverID));
        assertTrue(returnmessage.kind == ResponseKind.ADDINGMESSAGE_FAILED);
    }

    @Test
    public void testDeletingMessage(){
        Response rM = serverState.handlerequest(RequestMessage.AddRequest("Hello", senderID, receiverID));
        int messId = rM.messageID;
        rM = serverState.handlerequest(RequestMessage.DeleteRequest(messId));
        assertEquals(rM.kind, ResponseKind.DELETEDMESSAGE);
        assertEquals(rM.messageID, messId);
    }

    @Test
    public void testReplaceMessage() {
        Response rM = serverState.handlerequest(RequestMessage.AddRequest("Hello", senderID, receiverID));
        int messId = rM.messageID;
        rM = serverState.handlerequest(RequestMessage.ReplaceRequest(messId, "Hejsan"));
        assertEquals(rM.kind, ResponseKind.MESSAGEREPLACED);
        assertEquals(rM.messageID,messId);
    }

    /*
    
    public void testEmptyReplaceMessage(){
    	serverState.handlerequest(connectString);
        String requestString = "<RplMessage>\n" +
                                "<MsgID \" \" />\n" +
                                "<Content \"NEW HELLO\" />\n" +
                                "</RplMessage>";
        Response returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.ERRORREPLACINGMESSAGE);
    	
    }
    
    @Test
    public void testFetchingMessage(){
    	 serverState.handlerequest(connectString);
         String requestString = "<AddMessage>\n" +
                                 "<Receiver \"0767731855\" />\n" +
                                 "<Content \" HELLO HELLO\" />\n" +
                                 "</AddMessage>";
         Response returnmessage = serverState.handlerequest(requestString);
         senderID = returnmessage.senderID;
         assertTrue(returnmessage.kind == returnmessage.kind.ADDEDMESSAGE);serverState.handlerequest(connectString);
    	
    	
        requestString = "<FetchMessages>\n" +
        						"<SenderID \"+ senderID +\" />\n" +
        						"<Content \"HELLO HELLO\" />\n" +
                                "<MsgID \"+ messageID +\" />\n" +
                                "</FetchedMessages>";
        returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.FETCHED);
    	
    }
    @Test
    public void testFailedFetchingMessage(){
        	serverState.handlerequest(connectString);
            String requestString = "<FetchMessages>\n" +
                                    "<MsgID \"+ messageID +\" />\n" +
                                    "</FetchedMessages>";
            Response returnmessage = serverState.handlerequest(requestString);
            assertTrue(returnmessage.kind == returnmessage.kind.FETCHED_FAILED);
        	
        }
    @Test
    public void testFetchComplete(){
    	serverState.handlerequest(connectString);
        String requestString = "<FetchComplete>\n" +
                                "</FetchedCompleteAck>";
        Response returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.FETCHCOMPLETE);
    }
    
    @Test
    public void testFailedFetchComplete(){
	serverState.handlerequest(connectString);
    String requestString = "<FetchComplete>\n" +
                            "</FetchedCompleteAck>";
    Response returnmessage = serverState.handlerequest(requestString);
    assertTrue(returnmessage.kind == returnmessage.kind.FAILEDFETCHCOMPLETE);
}
        assertTrue(returnmessage.kind == returnmessage.kind.ADDINGMESSAGE_FAILED);
        assertEquals(returnmessage.Error,"Message empty");
    }*/

}
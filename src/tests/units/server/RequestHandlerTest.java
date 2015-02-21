package tests.units.server;

import main.messagestore.Messages;
import main.server.ServerState;
import main.server.request.ActionKind;
import main.server.request.RequestObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class RequestHandlerTest {

    ServerState serverState;
    String senderID = "0767731855";
    String receiverID = "0765861635";
    RequestObject connectRequest = RequestObject.ConnectRequest(senderID);

    @Before
    public void setUp() throws Exception {
        serverState = new ServerState(new Messages());
    }
    //
    @Test
    public void testHandleRequest() throws Exception {
        RequestObject returnmessage = serverState.handlerequest(connectRequest);
        assertTrue(returnmessage.kind == ActionKind.CONNECT);
        assertTrue(returnmessage.ID.equals(senderID));
    }

    //Connecting multiple times should fail
    @Test
    public void testConnectTwice() {
        serverState.handlerequest(connectRequest);
        RequestObject returnmessage = serverState.handlerequest(connectRequest );
        assertTrue(returnmessage.kind == ActionKind.CONNECT);
        assertTrue(returnmessage.Error != null);
    }

    @Test
    public void testAddMessage() {
        RequestObject returnmessage = serverState.handlerequest(RequestObject.AddRequest("HEllo", senderID, receiverID));
        assertTrue(returnmessage.kind == ActionKind.ADD);
    }

    @Test
    public void testAddInvalidMessage() {
        RequestObject returnmessage = serverState.handlerequest(RequestObject.AddRequest("", senderID, receiverID));
        assertTrue(returnmessage.kind == ActionKind.ADD);
        assertTrue(returnmessage.Error != null);
    }

    @Test
    public void testDeletingMessage(){
        RequestObject rM = serverState.handlerequest(RequestObject.AddRequest("Hello", senderID, receiverID));
        int messId = rM.messageID;
        rM = serverState.handlerequest(RequestObject.DeleteRequest(messId));
        assertEquals(rM.kind, ActionKind.REMOVE);
        assertEquals(rM.messageID, messId);
    }

    @Test
    public void testReplaceMessage() {
        RequestObject rM = serverState.handlerequest(RequestObject.AddRequest("Hello", senderID, receiverID));
        int messId = rM.messageID;
        rM = serverState.handlerequest(RequestObject.ReplaceRequest(messId, "Hejsan"));
        assertEquals(rM.kind, ActionKind.REPLACE);
        assertEquals(rM.messageID,messId);
    }

    /*
    
    public void testEmptyReplaceMessage(){
    	serverState.handlerequest(connectString);
        String requestString = "<RplMessage>\n" +
                                "<MsgID \" \" />\n" +
                                "<Content \"NEW HELLO\" />\n" +
                                "</RplMessage>";
        RequestObject returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.ERRORREPLACINGMESSAGE);
    	
    }
    
    @Test
    public void testFetchingMessage(){
    	 serverState.handlerequest(connectString);
         String requestString = "<AddMessage>\n" +
                                 "<Receiver \"0767731855\" />\n" +
                                 "<Content \" HELLO HELLO\" />\n" +
                                 "</AddMessage>";
         RequestObject returnmessage = serverState.handlerequest(requestString);
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
            RequestObject returnmessage = serverState.handlerequest(requestString);
            assertTrue(returnmessage.kind == returnmessage.kind.FETCHED_FAILED);
        	
        }
    @Test
    public void testFetchComplete(){
    	serverState.handlerequest(connectString);
        String requestString = "<FetchComplete>\n" +
                                "</FetchedCompleteAck>";
        RequestObject returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.FETCHCOMPLETE);
    }
    
    @Test
    public void testFailedFetchComplete(){
	serverState.handlerequest(connectString);
    String requestString = "<FetchComplete>\n" +
                            "</FetchedCompleteAck>";
    RequestObject returnmessage = serverState.handlerequest(requestString);
    assertTrue(returnmessage.kind == returnmessage.kind.FAILEDFETCHCOMPLETE);
}
        assertTrue(returnmessage.kind == returnmessage.kind.ADDINGMESSAGE_FAILED);
        assertEquals(returnmessage.Error,"Message empty");
    }*/

}
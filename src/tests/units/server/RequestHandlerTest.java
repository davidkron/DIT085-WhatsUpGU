package tests.units.server;

import main.ReturnMessage;
import main.ServerState;
import main.server.request.RequestMessage;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class RequestHandlerTest {

    ServerState serverState;
    String ID = "0767731855";
    RequestMessage connectRequest = RequestMessage.ConnectRequest(ID);

    @Before
    public void setUp() throws Exception {
        serverState = new ServerState();
    }

    //
    @Test
    public void testHandleRequest() throws Exception {
        ReturnMessage returnmessage = serverState.handlerequest(connectRequest);
        assertTrue(returnmessage.kind == returnmessage.kind.ACCEPTEDCONNECTION);
        assertTrue(returnmessage.ID == ID);
    }

    //Connecting multiple times should fail
    @Test
    public void testConnectTwice() {
        serverState.handlerequest(connectRequest);
        ReturnMessage returnmessage = serverState.handlerequest(connectRequest );
        assertTrue(returnmessage.kind == returnmessage.kind.REFUSEDCONNECTION);
    }

    @Test
    public void testAddMessage() {
        ReturnMessage returnmessage = serverState.handlerequest(RequestMessage.AddRequest("HEllo", ID));
        assertTrue(returnmessage.kind == returnmessage.kind.ADDEDMESSAGE);
    }

    @Test
    public void testAddInvalidMessage() {
        ReturnMessage returnmessage = serverState.handlerequest(RequestMessage.AddRequest("",ID));
        assertTrue(returnmessage.kind == returnmessage.kind.FAILEDADDINGMESSAGE);
    }

    @Test
    public void testDeletingMessage(){
        ReturnMessage rM = serverState.handlerequest(RequestMessage.AddRequest("Hello", ID));
        int messId = rM.messageID;
        rM = serverState.handlerequest(RequestMessage.DeleteRequest(messId));
        /*String requestString = "<DelMessage>\n" +
                                "<MsgID \"+ messageID +\" />\n" +
                                "</DelMessage>";*/
        assertTrue(rM.kind == rM.kind.DELETEDMESSAGE);
        assertTrue(rM.messageID == messId);
    }

    /*
    public void testDeletingEmptyMessage(){
    	serverState.handlerequest(connectString);
        String requestString = "<DelMessage>\n" +
                                "<MsgID \" \" />\n" +
                                "</DelMessage>";
        ReturnMessage returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.FAILEDDELETEMESSAGE);
    	
    }
    
    public void testReplaceMessage(){
        serverState.handlerequest(connectString);
        String requestString = "<AddMessage>\n" +
                                "<Receiver \"0767731855\" />\n" +
                                "<Content \" HELLO\" />\n" +
                                "</AddMessage>";
        ReturnMessage returnmessage = serverState.handlerequest(requestString);
        messagID = returnmessage.ID;// saved for testing replacing purpose.
        assertTrue(returnmessage.kind == returnmessage.kind.ADDEDMESSAGE);
        
        requestString = "<RplMessage>\n" +
        					"<MsgID \"+ messageID +\" />\n" +
        					"<Content \"NEW HELLO\" />\n" +
        					"</RplMessage>";
        returnmessage = serverState.handlerequest(requestString);
        messagID = returnmessage.ID;// saved for testing deleleting purpose.
        assertTrue(returnmessage.kind == returnmessage.kind.MESSAGEREPLACED);
    }
    
    public void testEmptyReplaceMessage(){
    	serverState.handlerequest(connectString);
        String requestString = "<RplMessage>\n" +
                                "<MsgID \" \" />\n" +
                                "<Content \"NEW HELLO\" />\n" +
                                "</RplMessage>";
        ReturnMessage returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.ERRORREPLACINGMESSAGE);
    	
    }
    
    @Test
    public void testFetchingMessage(){
    	 serverState.handlerequest(connectString);
         String requestString = "<AddMessage>\n" +
                                 "<Receiver \"0767731855\" />\n" +
                                 "<Content \" HELLO HELLO\" />\n" +
                                 "</AddMessage>";
         ReturnMessage returnmessage = serverState.handlerequest(requestString);
         senderID = returnmessage.senderID;
         assertTrue(returnmessage.kind == returnmessage.kind.ADDEDMESSAGE);serverState.handlerequest(connectString);
    	
    	
        requestString = "<FetchMessages>\n" +
        						"<SenderID \"+ senderID +\" />\n" +
        						"<Content \"HELLO HELLO\" />\n" +
                                "<MsgID \"+ messageID +\" />\n" +
                                "</FetchedMessages>";
        returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.FETCHEDMESSAGE);
    	
    }
    @Test
    public void testFailedFetchingMessage(){
        	serverState.handlerequest(connectString);
            String requestString = "<FetchMessages>\n" +
                                    "<MsgID \"+ messageID +\" />\n" +
                                    "</FetchedMessages>";
            ReturnMessage returnmessage = serverState.handlerequest(requestString);
            assertTrue(returnmessage.kind == returnmessage.kind.FETCHEDMESSAGEFAILED);
        	
        }
    @Test
    public void testFetchComplete(){
    	serverState.handlerequest(connectString);
        String requestString = "<FetchComplete>\n" +
                                "</FetchedCompleteAck>";
        ReturnMessage returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.FETCHCOMPLETE);
    }
    
    @Test
    public void testFailedFetchComplete(){
	serverState.handlerequest(connectString);
    String requestString = "<FetchComplete>\n" +
                            "</FetchedCompleteAck>";
    ReturnMessage returnmessage = serverState.handlerequest(requestString);
    assertTrue(returnmessage.kind == returnmessage.kind.FAILEDFETCHCOMPLETE);
}
        assertTrue(returnmessage.kind == returnmessage.kind.FAILEDADDINGMESSAGE);
        assertEquals(returnmessage.Error,"Message empty");
    }*/

}
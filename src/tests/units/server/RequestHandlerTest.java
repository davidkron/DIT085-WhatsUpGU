package tests.units.server;

import main.messagestore.IMessageCollection;
import main.messagestore.Message;
import main.server.ServerState;
import main.server.request.RequestObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {

    String senderID = "0767731855";
    String receiverID = "0765861635";
    RequestObject connectRequest = RequestObject.ConnectRequest(senderID);

    IMessageCollection fakeMessages;
    ServerState serverState;

    @Before
    public void setUp() throws Exception {
        fakeMessages = Mockito.mock(IMessageCollection.class);
        serverState = new ServerState(fakeMessages);
    }
    //
    @Test
    public void testHandleRequest() throws Exception {
        RequestObject returnmessage = serverState.handlerequest(connectRequest);
        assertTrue(returnmessage.ID.equals(senderID));
        assertNull(returnmessage.Error);
    }

    //Connecting multiple times should fail
    @Test
    public void testConnectTwice() {
        serverState.handlerequest(connectRequest);
        RequestObject returnmessage = serverState.handlerequest(connectRequest );
        assertTrue(returnmessage.Error != null);
    }

    @Test
    public void testAddMessage() {
        String content = "Hello";
        int newMsgId = 5;
        when(fakeMessages.add(content,senderID,receiverID)).thenReturn(newMsgId);

        RequestObject returnmessage = serverState.handlerequest(RequestObject.AddRequest(content, senderID, receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).add(content, senderID, receiverID); // Make sure add is called
        assertEquals(returnmessage.messageID, newMsgId);
    }

    @Test
    public void testAddInvalidMessage() {
        when(fakeMessages.add("",senderID,receiverID)).thenReturn(-10);
        RequestObject returnmessage = serverState.handlerequest(RequestObject.AddRequest("", senderID, receiverID));

        /////////////////////////////////////////////////////
        assertTrue(returnmessage.Error != null);
    }

    @Test
    public void testDeletingMessage(){
        int messId = 10;
        when(fakeMessages.delete(messId)).thenReturn(messId);
        RequestObject rM = serverState.handlerequest(RequestObject.DeleteRequest(messId));

        /////////////////////////////////////////////////////
        verify(fakeMessages).delete(messId);
        assertEquals(rM.messageID, messId);
        assertNull(rM.Error);
    }

    @Test
    public void testFailDeletingMessage(){
        int messId = 10;
        when(fakeMessages.delete(messId)).thenReturn(-10);
        RequestObject rM = serverState.handlerequest(RequestObject.DeleteRequest(messId));

        /////////////////////////////////////////////////////
        verify(fakeMessages).delete(messId);
        assertNotNull(rM.Error);
    }

    @Test
    public void testReplaceMessage() {
        int messId = 10;
        String content = "Hej";
        when(fakeMessages.replace(messId,content)).thenReturn(messId);
        RequestObject rM = serverState.handlerequest(RequestObject.ReplaceRequest(messId, content));

        /////////////////////////////////////////////////////
        verify(fakeMessages).replace(messId,content);
        assertEquals(rM.messageID, messId);
        assertNull(rM.Error);
    }

    @Test
    public void testFailReplaceMessage() {
        int messId = 10;
        String content = "Hej";
        when(fakeMessages.replace(messId,content)).thenReturn(-10);
        RequestObject rM = serverState.handlerequest(RequestObject.ReplaceRequest(messId, content));

        /////////////////////////////////////////////////////
        verify(fakeMessages).replace(messId, content);
        assertNotNull(rM.Error);
    }

    @Test
    public void testFetch() {
        Message msg = new Message("Content",4,"A","B");
        ArrayList<Message> msges = new ArrayList<Message>();
        when(fakeMessages.fetch(receiverID)).thenReturn(msges);
        RequestObject rM = serverState.handlerequest(RequestObject.FetchRequest(receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).fetch(receiverID);
        assertEquals(rM.fetchedMessages,msges);
        assertNull(rM.Error);
    }

    @Test
    public void testFailFetch() {
        when(fakeMessages.fetch(receiverID)).thenReturn(new ArrayList<Message>());
        RequestObject rM = serverState.handlerequest(RequestObject.FetchRequest(receiverID));
        /////////////////////////////////////////////////////
        verify(fakeMessages).fetch(receiverID);
        assertNotNull(rM.Error);
    }

    @Test
    public void testFetchComplete() {
        when(fakeMessages.fetchComplete(receiverID)).thenReturn(1);
        RequestObject rM = serverState.handlerequest(RequestObject.FetchComplete(receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).fetchComplete(receiverID);
        assertNull(rM.Error);
    }

    /*

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
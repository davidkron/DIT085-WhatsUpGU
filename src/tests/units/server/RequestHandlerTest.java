package tests.units.server;

import main.messagestore.IMessageCollection;
import main.messagestore.Message;
import main.server.RequestHandler;
import main.server.request.RequestCreator;
import main.server.request.RequestObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {
    private String senderID = "0767731855";
    private String receiverID = "0765861635";
    private RequestObject connectRequest = RequestCreator.ConnectRequest(senderID);

    private IMessageCollection fakeMessages;
    private RequestHandler requestHandler;

    @Before
    public void setUp() throws Exception {
        fakeMessages = Mockito.mock(IMessageCollection.class);
        requestHandler = new RequestHandler(fakeMessages);
    }

    //
    @Test
    public void testHandleRequest() throws Exception {
        RequestObject returnmessage = requestHandler.handlerequest(connectRequest);
        assertTrue(returnmessage.ID.equals(senderID));
        assertNull(returnmessage.Error);
    }

    //Connecting multiple times should fail
    @Test
    public void testConnectTwice() {
        requestHandler.handlerequest(connectRequest);
        RequestObject returnmessage = requestHandler.handlerequest(connectRequest);
        assertTrue(returnmessage.Error != null);
    }

    @Test
    public void testAddMessage() {
        String content = "Hello";
        int newMsgId = 5;
        when(fakeMessages.add(content,senderID,receiverID)).thenReturn(newMsgId);

        RequestObject returnmessage = requestHandler.handlerequest(RequestCreator.AddRequest(content, senderID, receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).add(content, senderID, receiverID); // Make sure add is called
        assertEquals(returnmessage.messageID, newMsgId);
    }

    @Test
    public void testAddInvalidMessage() {
        when(fakeMessages.add("",senderID,receiverID)).thenReturn(-10);
        RequestObject returnmessage = requestHandler.handlerequest(RequestCreator.AddRequest("", senderID, receiverID));

        /////////////////////////////////////////////////////
        assertTrue(returnmessage.Error != null);
    }

    @Test
    public void testDeletingMessage(){
        int messId = 10;
        when(fakeMessages.delete(messId)).thenReturn(messId);
        RequestObject rM = requestHandler.handlerequest(RequestCreator.DeleteRequest(messId));

        /////////////////////////////////////////////////////
        verify(fakeMessages).delete(messId);
        assertEquals(rM.messageID, messId);
        assertNull(rM.Error);
    }

    @Test
    public void testFailDeletingMessage(){
        int messId = 10;
        when(fakeMessages.delete(messId)).thenReturn(-10);
        RequestObject rM = requestHandler.handlerequest(RequestCreator.DeleteRequest(messId));

        /////////////////////////////////////////////////////
        verify(fakeMessages).delete(messId);
        assertNotNull(rM.Error);
    }

    @Test
    public void testReplaceMessage() {
        int messId = 10;
        String content = "Hej";
        when(fakeMessages.replace(messId,content)).thenReturn(messId);
        RequestObject rM = requestHandler.handlerequest(RequestCreator.ReplaceRequest(messId, content));

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
        RequestObject rM = requestHandler.handlerequest(RequestCreator.ReplaceRequest(messId, content));

        /////////////////////////////////////////////////////
        verify(fakeMessages).replace(messId, content);
        assertNotNull(rM.Error);
    }

    @Test
    public void testFetch() {
        Message msg = new Message("Content",4,"A","B");
        ArrayList<Message> msges = new ArrayList<>();
        msges.add(msg);
        when(fakeMessages.fetch(receiverID)).thenReturn(msges);
        RequestObject rM = requestHandler.handlerequest(RequestCreator.FetchRequest(receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).fetch(receiverID);
        assertEquals(rM.fetchedMessages,msges);
        assertNull(rM.Error);
    }

    @Test
    public void testFailFetch() {
        when(fakeMessages.fetch(receiverID)).thenReturn(new ArrayList<Message>());
        RequestObject rM = requestHandler.handlerequest(RequestCreator.FetchRequest(receiverID));
        /////////////////////////////////////////////////////
        verify(fakeMessages).fetch(receiverID);
        assertNotNull(rM.Error);
    }

    @Test
    public void testFetchComplete() {
        when(fakeMessages.fetchComplete(receiverID)).thenReturn(1);
        RequestObject rM = requestHandler.handlerequest(RequestCreator.FetchComplete(receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).fetchComplete(receiverID);
        assertNull(rM.Error);
    }

    @Test
    public void testFailFetchComplete() {
        when(fakeMessages.fetchComplete(receiverID)).thenReturn(0);
        RequestObject rM = requestHandler.handlerequest(RequestCreator.FetchComplete(receiverID));

        /////////////////////////////////////////////////////
        verify(fakeMessages).fetchComplete(receiverID);
        assertNotNull(rM.Error);
    }
}
package tests.units.server;

import main.ReturnMessage;
import main.ServerState;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class RequestHandlerTest {

    ServerState serverState;

    String ID = "0767731855";
    String connectString = "<Request connection  " + ID + " +/>";

    @Before
    public void setUp() throws Exception {
        serverState = new ServerState();
    }

    @Test
    public void testHandleRequest() throws Exception {
        ReturnMessage returnmessage = serverState.handlerequest(connectString);
        assertTrue(returnmessage.kind == returnmessage.kind.ACCEPTEDCONNECTION);
        assertTrue(returnmessage.ID == ID);
    }

    @Test
    public void testConnectTwice(){
        serverState.handlerequest(connectString);
        ReturnMessage returnmessage = serverState.handlerequest(connectString);
        assertTrue(returnmessage.kind == returnmessage.kind.REFUSEDCONNECTION);
    }

    @Test
    public void testAddMessage(){
        serverState.handlerequest(connectString);
        String requestString = "<AddMessage>\n" +
                                "<Receiver \"0767731855\" />\n" +
                                "<Content \"HELLO\" />\n" +
                                "</AddMessage>";
        ReturnMessage returnmessage = serverState.handlerequest(requestString);
        assertTrue(returnmessage.kind == returnmessage.kind.ADDEDMESSAGE);
    }
}
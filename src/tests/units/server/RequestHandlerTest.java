package tests.units.server;

import main.ServerState;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

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
        String acceptString = "<Accepted connection from  " + ID + " +/>";
        String result = serverState.handlerequest(connectString);
        assertEquals(acceptString,result);
    }

    @Test
    public void testConnectTwice(){
        String acceptString = "<Accepted connection from  " + ID + " +/>";
        serverState.handlerequest(connectString);
        String result = serverState.handlerequest(connectString);
        assertFalse(acceptString.equals(result));
    }

    @Test
    public void testAddMessage(){
        serverState.handlerequest(connectString);
        String requestString = "<AddMessage>\n" +
                                "<Receiver \"0767731855\" />\n" +
                                "<Content \"HELLO\" />\n" +
                                "</AddMessage>";
        String acceptRegex = "<Message added: \"d+\" />";
        String result = serverState.handlerequest(requestString);
        assertTrue(result.matches("<Message added: \"d+\" />"));
    }
}
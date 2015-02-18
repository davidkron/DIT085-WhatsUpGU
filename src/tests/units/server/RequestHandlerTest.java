package tests.units.server;

import main.RequestHandler;
import main.messagestore.IMessageCollection;
import main.messagestore.Messages;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class RequestHandlerTest {

    IMessageCollection messages;
    RequestHandler requests;
    String ID = "0767731855";
    String connectString = "<Request connection  " + ID + " +/>";

    @Before
    public void setUp() throws Exception {
        messages = new Messages();
        requests = new RequestHandler();
    }

    @Test
    public void testHandleRequest() throws Exception {
        String acceptString = "<Accepted connection from  " + ID + " +/>";
        String result = requests.handle(connectString, messages);
        assertEquals(acceptString,result);
    }

    @Test
    public void testConnectTwice(){
        String acceptString = "<Accepted connection from  " + ID + " +/>";
        requests.handle(connectString, messages);
        String result = requests.handle(connectString, messages);
        assertFalse(acceptString.equals(result));
    }

    @Test
    public void testAddMessage(){
        requests.handle(connectString, messages);
        String requestString = "<AddMessage>\n" +
                                "<Receiver \"0767731855\" />\n" +
                                "<Content \"HELLO\" />\n" +
                                "</AddMessage>";
        String acceptRegex = "<Message added: \"d+\" />";
        String result = requests.handle(requestString, messages);
        assertTrue(result.matches("<Message added: \"d+\" />"));
    }






}
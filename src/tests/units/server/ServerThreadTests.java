package tests.units.server;

import main.messagestore.Messages;
import main.server.IRequestHandler;
import main.server.ObjectStream;
import main.server.RequestHandler;
import main.server.ServerThread;
import main.server.request.RequestCreator;
import main.server.request.RequestObject;
import main.server.request.XMLEncoder;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.IOException;
import java.net.SocketException;

import static org.mockito.Mockito.*;

public class ServerThreadTests {

    @Test
    public void testInvalidXML() throws Exception {
        ObjectStream stream = mock(ObjectStream.class);
        Messages fakeMessages = mock(Messages.class );
        RequestHandler requestHandler = new RequestHandler(fakeMessages);

        when(stream.readString()).thenReturn(">invalidXML<");
        doThrow(new SocketException("Connection Closed"))
            .when(stream).writeString("<error>Invalid request.</error>");

        ServerThread thread = new ServerThread(stream, requestHandler);
        thread.start();
        thread.join();

        ////////////////////////////////////////////////////////////////
        verify(stream).writeString(Matchers.matches("<error>.*</error>"));
    }

    @Test
    public void testClose() throws InterruptedException, IOException {
        ObjectStream stream = mock(ObjectStream.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);
        ServerThread serverThread = new ServerThread(stream,requestHandler);
        serverThread.start();
        serverThread.close();
        serverThread.join();
        verify(stream).close();
    }


    @Test
    public void testIdKept() throws Exception {
        ObjectStream stream = mock(ObjectStream.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);
        String id = "0767731855";

        when(stream.readString()).thenReturn(
                "<connection>" +
                        "<request>" + id + "</request>" +
                        "</connection>"
        ).thenReturn("<fetch>true</fetch>").thenThrow(new SocketException("Connection Closed"));

        RequestObject Connect = RequestCreator.ConnectRequest(id);

        when(requestHandler.handlerequest(Matchers.<RequestObject>anyObject())).thenReturn(Connect);

        ServerThread thread = new ServerThread(stream, requestHandler);
        thread.start();
        thread.join();

        //////////////////////////////////////////////////////////////////////////////////
        verify(stream,atLeastOnce()).writeString(XMLEncoder.encode(Connect));
        verify(requestHandler).handlerequest(Matchers.refEq(RequestCreator.FetchRequest(id)));
    }
}
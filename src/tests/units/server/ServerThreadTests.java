package tests.units.server;

import main.server.IRequestHandler;
import main.server.ObjectStream;
import main.server.ServerThread;
import main.server.request.RequestCreator;
import main.server.request.RequestObject;
import main.server.request.XMLEncoder;
import org.junit.Test;
import org.mockito.Matchers;

import java.net.SocketException;

import static org.mockito.Mockito.*;

public class ServerThreadTests {

    @Test
    public void testInvalidXML() throws Exception {
        ObjectStream stream = mock(ObjectStream.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(stream.readString()).thenReturn(">invalidXML<").thenThrow(new SocketException("Connection Closed"));

        ServerThread thread = new ServerThread(stream, requestHandler);
        thread.start();
        thread.join();

        ////////////////////////////////////////////////////////////////
        verify(stream).writeString(Matchers.matches("<error>.*</error>"));
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
package tests.units.server;

import main.ServerThread;
import org.junit.Test;

import java.io.OutputStream;
import java.net.Socket;

import static org.mockito.Mockito.mock;

public class ServerThreadTests {

    @Test
    public void testStart() throws Exception {
        // Mocking a socket
        Socket socket = mock(Socket.class);

        String ret = "Test";

        OutputStream out;

        ServerThread sThread = new ServerThread();
        //sThread.start(socket,state);
    }
}
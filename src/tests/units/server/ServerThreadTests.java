package tests.units.server;

import main.ServerThread;
import org.junit.Test;

import java.io.OutputStream;

public class ServerThreadTests {

    @Test
    public void testStart() throws Exception {
        // Mocking a socket

        String ret = "Test";

        OutputStream out;

        ServerThread sThread = new ServerThread();
        //sThread.start(socket,state);
    }
}
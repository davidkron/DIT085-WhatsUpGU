package tests.units.server;

import main.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static junit.framework.TestCase.assertEquals;

public class ServerTest {

    Server server;
    Socket socket;
    PrintWriter dOut;
    BufferedReader in;

    @Before
    public void setUp() throws InterruptedException {
        try {
            server = new Server();
            new Thread(server).start();
            Thread.sleep(1000);
            socket = new Socket("127.0.0.1",server.getPort());
            dOut = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneConnection() throws IOException {
        dOut.write("<Request connection  \"0767731855\" +/>\n");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/*

        byte[] emptyPayload = new byte[1001];

        // Using Mockito
        final Socket socket = mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
*/
        while(!in.ready()){};
        char string[] = new char[1000];
        in.read(string,0,1000);
        System.out.println("Received from Server: " + string);
        assertEquals("<Accepted connection from  \"0767731855\" +/>",string);
    }
}
package tests.units.server;

import main.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static junit.framework.TestCase.assertEquals;

public class ServerTest {

    Server server;
    Socket socket;

    @Before
    public void setUp() throws InterruptedException {
        try {
            server = new Server();
            new Thread(server).start();
            Thread.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneConnection() throws IOException, ClassNotFoundException {
        socket = new Socket("127.0.0.1", server.getPort());

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("<Request connection  \"0767731855\" +/>\n");
        out.flush();

        String message = (String)in.readObject();

        System.out.println("Received from Server: " + message);
        assertEquals("<Accepted connection from  \"0767731855\" +/>", message);
    }
}
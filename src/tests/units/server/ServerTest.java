package tests.units.server;

import main.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        out.writeObject(
            "<connection>" +
                "<request>0767731855</request>" +
            "</connection>"
        );
        out.flush();

        String message = (String)in.readObject();

        System.out.println("Received from Server: " + message);
        assertEquals(
            "<connection>" +
                "<accepted>0767731855</accepted>" +
            "</connection>",
            message
        );
    }
}
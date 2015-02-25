package tests.integration;

import main.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class other {

    Server server;
    Socket socket;

    @Before
    public void setUp() throws InterruptedException {
        try {
            server = new Server();
            new Thread(server).start();
            Thread.sleep(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneConnection() throws IOException, ClassNotFoundException {
        socket = new Socket("127.0.0.1", server.getPort());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        parts.asserted_connect(in, out, "0767731856");
    }
}
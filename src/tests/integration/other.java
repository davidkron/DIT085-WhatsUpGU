package tests.integration;

import main.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class other {

    private Server server;

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


    @After
    public void tearDown() throws IOException, InterruptedException {
        server.close();
    }

    @Test
    public void testOneConnection() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", server.getPort());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        parts.asserted_connect(in, out, "0767731856");
    }
}
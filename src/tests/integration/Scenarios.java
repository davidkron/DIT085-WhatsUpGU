package tests.integration;

import main.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;

    /*
    *
    *                      SCENARIOS
    *
    * 1: User connects, adds a message, then deletes it
    * 2: User tries to delete an unexisting message
    * 3: User adds two messages, deletes one of them, replaces the other and fetches
    * 5: User X and Y adds a message at the same time, user X deletes it, user Y replaces it
    *
    * */

public class Scenarios {

    Server server;
    Socket socket;
    Thread serverThread;

    @Before
    public void setUp() throws InterruptedException {
        try {
            server = new Server();
            serverThread = new Thread(server);
            serverThread.start();
            Thread.sleep(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws IOException, InterruptedException {
    }

    // User X adds a message, user Y fetches it, user X tries to delete it
    @Test
    public void testScenario4() throws IOException, ClassNotFoundException, InterruptedException {
        String xId = "0767731855";
        final String yId = "0767731856";

        Socket socketX = new Socket("127.0.0.1", server.getPort());

        ObjectOutputStream xOut = new ObjectOutputStream(socketX.getOutputStream());
        ObjectInputStream xIn = new ObjectInputStream(socketX.getInputStream());
        parts.asserted_connect(xIn, xOut, xId);

        int msg = parts.asserted_add(xIn, xOut, yId);

        Socket socketY = new Socket("127.0.0.1", server.getPort());
        ObjectOutputStream yOut = new ObjectOutputStream(socketY.getOutputStream());
        ObjectInputStream yIn = new ObjectInputStream(socketY.getInputStream());

        parts.asserted_connect(yIn, yOut, yId);
        parts.asserted_fetch(yIn, yOut, yId);
        /*  TRY DELETING MESSAGE BEING FETCHED    */
        xOut.writeObject("<messageAction>" +
                "<delete>" +
                "<messageID>"+ msg +"</messageID>" +
                "</delete>" +
                "</messageAction>");
        String message = (String)xIn.readObject();
        /*  SHOULD HAVE FAILED  */
        Pattern pattern = Pattern.compile("<error>(.+)</error>");
        Matcher m = pattern.matcher(message);
        assertTrue(m.matches());

        server.close();
        serverThread.join();
    }


}
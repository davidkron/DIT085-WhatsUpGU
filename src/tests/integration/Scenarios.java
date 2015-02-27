package tests.integration;

import main.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static junit.framework.Assert.assertTrue;

    /*
    *                      SCENARIOS
    *
    * 1: User connects, adds a message, then deletes it
    * 2: User tries to delete a message twice
    * 3: User adds two messages, deletes one of them, replaces the other and fetches
    * 4: User X adds a message, user Y fetches it, user X tries to delete it
    * 5: User X adds and deletes a message at the same time as user Y adds and replaces a message
    * 9: User X sends invalid xml to the server
    * 10: User X sends something that is not a string to the server
    * */

public class Scenarios {
    private Server server;
    private Thread serverThread;

    private String xId = "0767731855";
    private final String yId = "0767731856";

    private ObjectOutputStream xOut;
    private ObjectInputStream xIn;

    private ObjectOutputStream yOut;
    private ObjectInputStream yIn;

    @Before
    public void setUp() throws InterruptedException, IOException, ClassNotFoundException {
        new parts();

        try {
            server = new Server();
            serverThread = new Thread(server);
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket socketX = new Socket("127.0.0.1", server.getPort());
        xOut = new ObjectOutputStream(socketX.getOutputStream());
        xIn = new ObjectInputStream(socketX.getInputStream());
        parts.asserted_connect(xIn, xOut, xId);

        Socket socketY = new Socket("127.0.0.1", server.getPort());
        yOut = new ObjectOutputStream(socketY.getOutputStream());
        yIn = new ObjectInputStream(socketY.getInputStream());
        parts.asserted_connect(yIn, yOut, yId);
    }

    @After
    public void tearDown() throws IOException, InterruptedException {
        server.close();
        serverThread.join();
    }


    // 1: User X connects, adds a message, then deletes it
    @Test
    public void testScenario1() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn, xOut, xId);
        parts.asserted_delete(xIn, xOut, msg);
    }

    // 2: User X tries to delete a message twice
    @Test
    public void testScenario2() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn, xOut, xId);
        parts.asserted_delete(xIn, xOut, msg);
        parts.asserted_delete_failed(xIn, xOut, msg);
    }

    // 3: User X adds two messages, deletes one of them, replaces the other and fetches
    @Test
    public void testScenario3() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn, xOut, xId);
        int msg2 = parts.asserted_add(xIn, xOut, xId);
        parts.asserted_delete(xIn, xOut, msg);
        parts.asserted_replace(xIn, xOut, msg2);
        parts.asserted_fetch(xIn,xOut);
    }

    // 4: User X adds a message, user Y fetches it, user X tries to delete it
    @Test
    public void testScenario4() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn, xOut, yId);
        parts.asserted_fetch(yIn,yOut);
        /*  TRY DELETING MESSAGE BEING FETCHED    */
        parts.asserted_delete_failed(xIn,xOut,msg);
    }

    // 5: User X adds and deletes a message at the same time as user Y adds and replaces a message
    @Test
    public void testScenario5() throws IOException, ClassNotFoundException, InterruptedException {
        Thread yAdd = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socketZ = new Socket("127.0.0.1", server.getPort());
                    ObjectOutputStream zOut = new ObjectOutputStream(socketZ.getOutputStream());
                    ObjectInputStream zIn = new ObjectInputStream(socketZ.getInputStream());
                    parts.asserted_connect(zIn, zOut, "0767731888");
                    int msg = parts.asserted_add(zIn, zOut, xId);
                    parts.asserted_replace(zIn,zOut,msg);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        yAdd.start();
        int msg = parts.asserted_add(xIn, xOut, xId);
        parts.asserted_delete(xIn, xOut, msg);
        yAdd.join();
    }

    // 6: User X tries to connect twice(second connect fail), adds an empty message for user Y(fails), and user Y fetches (fails -  no messages).
    @Test
    public void testScenario6() throws IOException, ClassNotFoundException, InterruptedException {
        parts.asserted_connect_fail(xIn, xOut, xId);
        parts.asserted_add_fail(xIn, xOut, yId,"");
        parts.asserted_fetch_fail(yIn, yOut);
    }

    // 7: User X adds a message and tries to replace it with an empty message(fails) then fetches (returns the first message).

    @Test
    public void testScenario7() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn,xOut,yId);
        parts.asserted_replace_fail(xIn, xOut, msg, "");
        parts.asserted_fetch(yIn,yOut);
    }

    // 8: User X adds a message for user Y. User Y fetches the message. Completes the fetch. Then fetches again (fail - no messages). Completes fetch again (fail - no fetch).

    @Test
    public void testScenario8() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn,xOut,yId);
        parts.asserted_fetch(yIn,yOut);
        parts.asserted_fetchcomplete(yIn, yOut);
        parts.asserted_fetch_fail(yIn, yOut);
        parts.asserted_fetchcomplete_fail(yIn,yOut);
    }

    // 9: Sends garbage to the server
    @Test
    public void testScenario9() throws IOException, ClassNotFoundException, InterruptedException {
        xOut.writeObject("<><<>><<>!//");
        assertTrue(((String)xIn.readObject()).matches("<error>.+</error>"));
        xOut.writeObject("<root>shutdown</root>");
        assertTrue(((String)xIn.readObject()).matches("<error>.+</error>"));
    }

    // 10: Sends something that is not a string to the server
    @Test
    public void testScenario10() throws IOException, ClassNotFoundException, InterruptedException {
        xOut.writeObject(1.00f);
        xOut.flush();
        assertTrue(((String)xIn.readObject()).matches("<error>.+</error>"));
    }
}
package tests.integration;

import main.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static junit.framework.TestCase.assertTrue;

    /*
    *                      SCENARIOS
    *
    * 1: User connects, adds a message, then deletes it
    * 2: User tries to delete a message twice
    * 3: User adds two messages, deletes one of them, replaces the other and fetches
    * 4: User X adds a message, user Y fetches it, user X tries to delete it
    * 5: User X adds and deletes a message at the same time as user Y adds and replaces a message
    * */

public class Scenarios {

    Server server;
    Socket socket;
    Thread serverThread;

    String xId = "0767731855";
    final String yId = "0767731856";

    ObjectOutputStream xOut;
    ObjectInputStream xIn;

    ObjectOutputStream yOut;
    ObjectInputStream yIn;


    @Before
    public void setUp() throws InterruptedException, IOException, ClassNotFoundException {
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

    // 3: User adds two messages, deletes one of them, replaces the other and fetches
    @Test
    public void testScenario3() throws IOException, ClassNotFoundException, InterruptedException {
        assertTrue(false);
    }

    // 4: User X adds a message, user Y fetches it, user X tries to delete it
    @Test
    public void testScenario4() throws IOException, ClassNotFoundException, InterruptedException {
        int msg = parts.asserted_add(xIn, xOut, yId);
        parts.asserted_fetch(yIn,yOut,yId);
        /*  TRY DELETING MESSAGE BEING FETCHED    */
        parts.asserted_delete_failed(xIn,xOut,msg);
    }

    // 5: User X adds and deletes a message at the same time as user Y adds and replaces a message
    @Test
    public void testScenario5() throws IOException, ClassNotFoundException, InterruptedException {
        int msg2 = 0;
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

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        yAdd.start();
        int msg = parts.asserted_add(xIn, xOut, xId);
        parts.asserted_delete(xIn, xOut, msg);
        yAdd.join();
    }
}
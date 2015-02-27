package tests.units.server;

import main.server.IServerThread;
import main.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.SocketException;

import static org.mockito.Mockito.*;

public class ServerTest {
    @Mock private IServerThread thread;
    private Server spiedServer;
    Thread serverThread;

    int threadA = 0;
    int threadB = 0;

    @Before
    public void setUp() throws Exception {
        spiedServer = spy( new Server());
        serverThread = new Thread(spiedServer);
        thread = spy(new IServerThread() {
            @Override
            public void close() throws IOException {

            }

            @Override
            public void run(){
                threadA=1;
            }
        });
    }

    @Test
    public void testRunOneThread() throws Exception {
        doReturn(thread).doThrow(new SocketException()).when(spiedServer).makeThread();

        serverThread.start();
        while (threadA != 1){
            Thread.sleep(10);
        }
        spiedServer.close();
        serverThread.join();
        verify(thread).run();
    }

    @Test
    public void testRunTwoThreads() throws Exception {
        IServerThread thread2 = spy(new IServerThread() {
            @Override
            public void close() throws IOException {

            }

            @Override
            public void run(){
                threadB=1;
            }
        });

        doReturn(thread).doReturn(thread2).doThrow(new SocketException()).when(spiedServer).makeThread();

        Thread t = new Thread(spiedServer);
        t.start();
        while (threadA == 0 || threadB == 0){
            Thread.sleep(5);
        }
        spiedServer.close();
        t.join();
        verify(thread).run();
        verify(thread2).run();
    }
}
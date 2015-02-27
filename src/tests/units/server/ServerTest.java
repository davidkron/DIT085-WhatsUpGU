package tests.units.server;

import junit.framework.TestCase;
import main.server.IServerThread;
import main.server.Server;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.net.SocketException;

import static org.mockito.Mockito.*;

public class ServerTest extends TestCase {

    @Mock private IServerThread thread;
    private Server spiedServer;

    int threads = 0;

    @Before
    public void setUp() throws Exception {
        spiedServer = spy( new Server());
        thread = spy(new IServerThread() {
            @Override
            public void close() throws IOException {

            }

            @Override
            public void run(){
                threads++;
            }
        });
        doReturn(thread).doThrow(new SocketException()).when(spiedServer).makeThread();
    }

    public void testGetPort() throws Exception {

        spiedServer.close();
    }

    public void testClose() throws Exception {
        spiedServer.close();
    }

    public void testRun() throws Exception {
        Thread t = new Thread(spiedServer);
        t.start();
        while (threads <= 0){
            Thread.sleep(10);
        }
        spiedServer.close();
        t.join();
        verify(thread).run();
    }
}
package main.server;

import main.messagestore.Messages;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


public class Server implements Runnable {
    private final int PORT;
    private RequestHandler state = new RequestHandler(new Messages());
    private ServerSocket socket;
    private List<IServerThread> threads = new ArrayList<>();


    private boolean running = true;

    public int getPort() {
        return PORT;
    }

    public Server() throws IOException {
        this.PORT = 4444;
        socket = new ServerSocket(PORT);
    }

    public void close() throws IOException {
        running = false;
        socket.close();
    }

    public IServerThread makeThread() throws IOException {
        return new ServerThread(new ObjectStream(socket.accept()), state);
    }

    @Override
    public void run() {

        while (running) {
            try {
                IServerThread thread = makeThread();
                thread.start();
                threads.add(thread);
            } catch (IOException s) {
                running = false;
            }
        }

        for (IServerThread t: threads){
            try {
                t.close();
                t.join();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

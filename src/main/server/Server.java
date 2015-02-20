package main.server;

import main.messagestore.Messages;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
    private int PORT;
    ServerState state = new ServerState(new Messages());
    ServerSocket socket;

    boolean running = true;

    public int getPort() {
        return PORT;
    }

    public Server() throws IOException {
        this.PORT = 4444;
        socket = new ServerSocket(PORT);
    }

    public Server(int port) throws IOException {
        this.PORT = port;
        socket = new ServerSocket(PORT);
    }

    @Override
    public void run() {
        while (true) {
            try {
                new ServerThread().start(socket.accept(), state);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

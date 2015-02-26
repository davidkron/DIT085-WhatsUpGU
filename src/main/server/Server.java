package main.server;

import main.messagestore.Messages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private int PORT;
    RequestHandler state = new RequestHandler(new Messages());
    ServerSocket socket;
    List<ServerThread> threads = new ArrayList<>();


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

    public void close() throws IOException, InterruptedException {
        running = false;
        socket.close();
    }

    @Override
    public void run() {
        while (running) {
            try {
                ServerThread thread = new ServerThread();
                thread.start(new ObjectStream(socket.accept()), state);
                threads.add(thread);
            } catch (SocketException s) {
                running = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (ServerThread t: threads){
            try {
                t.close();
                t.join();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

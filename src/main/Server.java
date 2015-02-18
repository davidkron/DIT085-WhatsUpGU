package main;

import main.messagestore.IMessageCollection;
import main.messagestore.Messages;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by david on 2/16/15.
 */
public class Server implements Runnable{
    private int PORT;
    IMessageCollection messages;
    ServerSocket socket;
    ThreadSplitter splitter = new ThreadSplitter();
    boolean running = true;

    public int getPort(){
        return PORT;
    }

    public Server() throws IOException {
        this.PORT = 4444;
        socket = new ServerSocket(PORT);
        messages = new Messages();
    }

    public Server(int port) throws IOException {
        this.PORT = port;
        socket = new ServerSocket(PORT);
        messages = new Messages();
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("Lol");
                splitter.execute(socket.accept(),messages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

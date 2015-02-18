package main;

import main.messagestore.IMessageCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by david on 2/16/15.
 */
public class ThreadSplitter{
    public void execute(Socket s, IMessageCollection messages) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }

        ServerThread thread = new ServerThread();
        thread.start(s,messages);
    }
}

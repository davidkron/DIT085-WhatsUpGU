package main;

import main.messagestore.IMessageCollection;

import java.net.Socket;

/**
 * Created by david on 2/16/15.
 */
public class ServerThread extends Thread{
    RequestHandler requests;
    private Socket s;
    IMessageCollection messages;

    public void start(Socket s, IMessageCollection messages) {
        this.s = s;
        this.messages = messages;
        requests = new RequestHandler();
        start();
    }

    @Override
    public void run() {
            //try {
                //String result = RequestHandler.handleRequest(new BufferedReader(new InputStreamReader(s.getInputStream())), messages);
                String result = requests.handle("lol",messages);
                //new DataOutputStream(s.getOutputStream()).writeUTF(result);
                //} catch (IOException e) {
                //e.printStackTrace();
            //}
    }
}

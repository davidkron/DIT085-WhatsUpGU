package main;

import java.net.Socket;

/**
 * Created by david on 2/16/15.
 */
public class ServerThread extends Thread{
    private Socket s;
    IServerState state;

    public void start(Socket s, IServerState state) {
        this.s = s;
        this.state = state;
        start();
    }

    @Override
    public void run() {
            //try {
                //String result = state.handlerequest(new BufferedReader(new InputStreamReader(s.getInputStream())));
                String result = XMLEncoder.encode(state.handlerequest("lol"));
                //new DataOutputStream(s.getOutputStream()).writeUTF(result);
                //} catch (IOException e) {
                //e.printStackTrace();
            //}
    }
}

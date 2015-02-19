package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by david on 2/16/15.
 */
public class ServerThread extends Thread {
    private Socket s;
    IServerState state;

    public void start(Socket s, IServerState state) {
        this.s = s;
        this.state = state;
        start();
    }

    @Override
    public void run() {

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            String message = (String)in.readObject();
            String result = XMLEncoder.encode(state.handlerequest(message));
            out.writeObject(result);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

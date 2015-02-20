package main;

import main.server.request.RequestMessage;
import main.server.request.XMLDecoder;
import main.server.response.XMLEncoder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        try {
            ObjectOutputStream out= new ObjectOutputStream(s.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            String message = (String)in.readObject();
            RequestMessage request = XMLDecoder.decode(message);
            String result = XMLEncoder.encode(state.handlerequest(request));
            out.writeObject(result);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

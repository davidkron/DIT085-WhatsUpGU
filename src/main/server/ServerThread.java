package main.server;

import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLDecoder;
import main.server.request.XMLEncoder;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket s;
    IServerState state;
    String ID = null;

    public void start(Socket s, IServerState state) {
        this.s = s;
        this.state = state;
        start();
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            while (true) {
                String message = (String) in.readObject();
                RequestObject request = XMLDecoder.decode(message, ID);

                RequestObject response = state.handlerequest(request);

                if (response.kind == ActionKind.CONNECT) {
                    ID = response.ID;
                }

                String result = XMLEncoder.encode(response);
                out.writeObject(result);
                out.flush();
            }
        } catch (IOException | JDOMException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

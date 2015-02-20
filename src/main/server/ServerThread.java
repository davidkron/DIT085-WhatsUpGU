package main.server;

import main.server.request.RequestMessage;
import main.server.request.XMLDecoder;
import main.server.response.Response;
import main.server.response.ResponseKind;
import main.server.response.XMLEncoder;
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
            ObjectOutputStream out= new ObjectOutputStream(s.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            String message = (String)in.readObject();
            RequestMessage request = XMLDecoder.decode(message);

            if(ID != null){
                request.ID = ID;
            }

            Response response = state.handlerequest(request);

            if(response.kind == ResponseKind.ACCEPTEDCONNECTION){
                ID = response.ID;
            }

            String result = XMLEncoder.encode(response);
            out.writeObject(result);
            out.flush();
        } catch (IOException | JDOMException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

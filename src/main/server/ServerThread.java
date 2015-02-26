package main.server;

import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLDecoder;
import main.server.request.XMLEncoder;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.net.SocketException;

public class ServerThread extends Thread// extends Thread
{
    private ObjectStream stream;
    IRequestHandler state;
    String ID = null;
    boolean running = true;

    public void start(ObjectStream stream, IRequestHandler state) {
        this.stream = stream;
        this.state = state;
        start();
    }

    public void close() throws IOException {
        running = false;
        stream.close();
    }

    @Override
    public void run() {
        try {
            while (running) {
                String message = stream.readString();
                RequestObject request = null;
                String responseXML = "";
                try {
                    System.out.println(message);
                    request = XMLDecoder.decode(message, ID);
                    RequestObject response = state.handlerequest(request);

                    if (response.kind == ActionKind.CONNECT) {
                        ID = response.ID;
                    }

                    responseXML = XMLEncoder.encode(response);

                } catch (JDOMException e) {
                    responseXML = XMLEncoder.Error("Failed parsing request");
                }

                stream.writeString(responseXML);
            }

        } catch (SocketException s) {
            running = false;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

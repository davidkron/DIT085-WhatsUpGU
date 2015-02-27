package main.server;

import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLDecoder;
import main.server.request.XMLEncoder;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.net.SocketException;

public class ServerThread extends IServerThread
{
    private ObjectStream stream;
    private IRequestHandler state;
    private String ID = null;
    private boolean running = true;

    public ServerThread(ObjectStream stream, IRequestHandler state) {
        this.stream = stream;
        this.state = state;
    }

    @Override
    public void close() throws IOException {
        running = false;
        stream.close();
    }

    @Override
    public void run() {
        try {
            while (running) {
                String responseXML;
                try {
                    String message = stream.readString();
                    RequestObject request;
                    request = XMLDecoder.decode(message, ID);
                    RequestObject response = state.handlerequest(request);

                    if (response.kind == ActionKind.CONNECT) {
                        ID = response.ID;
                    }

                    responseXML = XMLEncoder.encode(response);

                } catch (JDOMException | ClassCastException | ClassNotFoundException e) {
                    responseXML = XMLEncoder.Error("Failed parsing request");
                }

                stream.writeString(responseXML);
            }

        } catch (SocketException s) {
            running = false;
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}

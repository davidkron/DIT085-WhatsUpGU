package main.server;

import main.server.request.*;

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
                RequestObject response;
                try {
                    String message = stream.readString();
                    RequestObject request;
                    request = XMLDecoder.decode(message, ID);
                    response = state.handlerequest(request);

                    if (response.kind == ActionKind.CONNECT) {
                        ID = response.ID;
                    }
                } catch (ClassCastException | ClassNotFoundException e) {
                    response = RequestCreator.InvalidRequest();
                }

                responseXML = XMLEncoder.encode(response);
                stream.writeString(responseXML);
            }

        } catch (SocketException s) {
            running = false;
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}

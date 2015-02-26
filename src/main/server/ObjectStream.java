package main.server;

import java.io.*;
import java.net.Socket;

/**
 * Created by david on 2/24/15.
 */
public class ObjectStream {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ObjectStream(Socket socket) throws IOException {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
        out.flush();
    }

    public String readString() throws IOException, ClassNotFoundException {
        return (String)in.readObject();
    }

    public void writeString(String result) throws IOException {
        out.writeObject(result);
        out.flush();
    }

    public void close() throws IOException {
        socket.close();
    }
}

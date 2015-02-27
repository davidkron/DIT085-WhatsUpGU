package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStream {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ObjectStream(Socket socket) throws IOException {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
        out.flush();
    }

    public String readString() throws IOException, ClassNotFoundException {
        return (String) in.readObject();
    }

    public void writeString(String result) throws IOException {
        out.writeObject(result);
        out.flush();
    }

    public void close() throws IOException {
        socket.close();
    }
}

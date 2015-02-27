package main.server;

import java.io.IOException;

/**
 * Created by david on 2/27/15.
 */
public abstract class IServerThread extends Thread{
    abstract public void close() throws IOException;
}

package main;

import main.messagestore.IMessageCollection;
import main.messagestore.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by david on 2/18/15.
 */
public class ServerState implements IServerState {
    IMessageCollection messages = new Messages();
    List<String> connections = new LinkedList<String>();

    @Override
    public String handlerequest(String request){
        String returnstring = "\"<Accepted connection from  \\\"ID\\\" +/>\"";
        return returnstring;
    }
}

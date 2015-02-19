package main;

import main.messagestore.IMessageCollection;
import main.messagestore.Messages;
import main.server.request.RequestMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by david on 2/18/15.
 */
public class ServerState implements IServerState {

    IMessageCollection messages = new Messages();
    List<String> connections = new LinkedList<String>();

    @Override
    public ReturnMessage handlerequest(RequestMessage request) {
        return new ReturnMessage(ReturnKind.ACCEPTEDCONNECTION, "0767731855");
    }
}

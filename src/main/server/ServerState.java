package main.server;

import main.messagestore.IMessageCollection;
import main.server.request.RequestMessage;
import main.server.response.Response;

import java.util.LinkedList;
import java.util.List;

public class ServerState implements IServerState {
    IMessageCollection messages;
    List<String> connections = new LinkedList<String>();

    public ServerState(IMessageCollection messages){
        this.messages = messages;
    }

    @Override
    public Response handlerequest(RequestMessage request) {
        int messId;
        Response response = null;

        switch (request.kind){
            case ADD:
                messId = messages.add(request.content, request.senderID, request.receiverID);
                response = Response.Added(messId);
                if (messId <= 0) {
                    response.Error = "Errar";
                }
                break;
            case CONNECT:
                response = Response.Connected(request.ID);
                if(connections.contains(request.ID)) {
                    response.Error = "Allready connected";
                }
                else {
                    connections.add(request.ID);
                }
                break;
            case REMOVE:
                messId = messages.delete(request.messageID);
                response = Response.Deleted(messId);
                if(messId <= 0){
                    response.Error = "Could not find message";
                }
                break;
            case REPLACE:
                messId = messages.replace(request.messageID, request.content);
                response = Response.Replaced(messId);
                if(messId <= 0){
                    response.Error = "Message invalid";
                }
                break;
        }

        return response;
    }
}

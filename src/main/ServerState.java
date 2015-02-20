package main;

import main.messagestore.IMessageCollection;
import main.messagestore.Messages;
import main.server.request.RequestMessage;
import main.server.response.Response;
import main.server.response.ResponseKind;

import java.util.LinkedList;
import java.util.List;

public class ServerState implements IServerState {
    IMessageCollection messages = new Messages();
    List<String> connections = new LinkedList<String>();

    @Override
    public Response handlerequest(RequestMessage request) {
        int messId;

        switch (request.kind){
            case ADD:
                messId = messages.add(request.content, request.senderID, request.receiverID);

                if (messId == 0) {
                    return Response.AddedFailed(messId);
                } else {
                    return Response.Added(messId);
                }

            case CONNECT:
                if(connections.contains(request.ID))
                    return  Response.AlreadyConnected(request.ID);
                connections.add(request.ID);
                    return Response.Connected(request.ID);
            case REMOVE:
                messId = messages.delete(request.messageID);
                return Response.Deleted(messId);
            case REPLACE:
                messId = messages.replace(request.messageID, request.content);
                return Response.Replaced(messId);
        }


        return new Response(ResponseKind.ACCEPTEDCONNECTION, "0767731855");
    }
}

package main.server;

import main.messagestore.IMessageCollection;
import main.server.request.RequestObject;

import java.util.LinkedList;
import java.util.List;

public class RequestHandler implements IRequestHandler {
    private IMessageCollection messages;
    private List<String> connections = new LinkedList<>();

    public RequestHandler(IMessageCollection messages){
        this.messages = messages;
    }

    @Override
    public RequestObject handlerequest(RequestObject request) {
        int messId;

        switch (request.kind){
            case ADD:
                request.messageID = messages.add(request.content, request.senderID, request.receiverID);
                if (request.messageID <= 0) {
                    request.Error = "Error adding message.";
                }
                break;
            case CONNECT:
                if(connections.contains(request.ID)) {
                    request.Error = "Already connected.";
                }
                else {
                    connections.add(request.ID);
                }
                break;
            case REMOVE:
                messId = messages.delete(request.messageID);
                if(messId <= 0){
                    request.messageID = messId;
                    request.Error = "Could not find message.";
                }
                break;
            case REPLACE:
                messId = messages.replace(request.messageID, request.content);
                if(messId <= 0){
                    request.Error = "Message invalid.";
                }
                break;
            case FETCH:
                request.fetchedMessages = messages.fetch(request.receiverID);

                if( request.fetchedMessages.isEmpty() ){
                    request.Error = "Error fetch.";
                }
                break;
            case FETCHCOMPLETE:
                int res = messages.fetchComplete(request.receiverID);
                request.content ="Succeeded";
                if (res <= 0){
                    request.Error = "Error fetch complete.";
                }
                break;
        }

        return request;
    }
}

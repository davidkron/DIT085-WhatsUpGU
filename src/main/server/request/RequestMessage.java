package main.server.request;

/**
 * Created by david on 2/19/15.
 */
public class RequestMessage {
    public RequestKind kind;
    public String ID;
    public String messageID;
    public String recipientID;
    public String senderID;
    public String content;

    public RequestMessage(RequestKind kind){
        this.kind = kind;
    }

    public static RequestMessage AddRequest(String content,String senderID){
        RequestMessage rM = new RequestMessage(RequestKind.ADD);
        rM.content = content;
        rM.senderID = senderID;
        return rM;
    }

    public static RequestMessage ConnectRequest(String id) {
        RequestMessage rM = new RequestMessage(RequestKind.CONNECT);
        rM.ID = id;
        return rM;
    }
}

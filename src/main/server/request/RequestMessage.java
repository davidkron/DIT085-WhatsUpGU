package main.server.request;

public class RequestMessage {
    public ActionKind kind;
    public String ID;
    public int messageID;
    public String receiverID;
    public String senderID;
    public String content;

    public RequestMessage(ActionKind kind){
        this.kind = kind;
    }

    public static RequestMessage AddRequest(String content, String senderID, String receiverID){
        RequestMessage rM = new RequestMessage(ActionKind.ADD);
        rM.content = content;
        rM.senderID = senderID;
        rM.receiverID = receiverID;
        return rM;
    }

    public static RequestMessage ConnectRequest(String id) {
        RequestMessage rM = new RequestMessage(ActionKind.CONNECT);
        rM.ID = id;
        return rM;
    }

    public static RequestMessage DeleteRequest(int messageID) {
        RequestMessage rM = new RequestMessage(ActionKind.REMOVE);
        rM.messageID = messageID;
        return rM;
    }

    public static RequestMessage ReplaceRequest(int messageID, String content) {
        RequestMessage rM = new RequestMessage(ActionKind.REPLACE);
        rM.messageID = messageID;
        rM.content = content;
        return rM;
    }
}

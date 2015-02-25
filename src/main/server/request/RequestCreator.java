package main.server.request;

/**
 * Created by David on 2015-02-25.
 */
public class RequestCreator {
    public static RequestObject AddRequest(String content, String senderID, String receiverID){
        RequestObject rM = new RequestObject(ActionKind.ADD);
        rM.content = content;
        rM.senderID = senderID;
        rM.receiverID = receiverID;
        return rM;
    }

    public static RequestObject ConnectRequest(String id) {
        RequestObject rM = new RequestObject(ActionKind.CONNECT);
        rM.ID = id;
        return rM;
    }

    public static RequestObject Added(String id) {
       RequestObject rM = new RequestObject(ActionKind.CONNECT);
        rM.ID = id;
        return rM;
    }

    public static RequestObject DeleteRequest(int messageID) {
        RequestObject rM = new RequestObject(ActionKind.REMOVE);
        rM.messageID = messageID;
        return rM;
    }

    public static RequestObject ReplaceRequest(int messageID, String content) {
        RequestObject rM = new RequestObject(ActionKind.REPLACE);
        rM.messageID = messageID;
        rM.content = content;
        return rM;
    }

    public static RequestObject FetchRequest(String receiverID) {
        RequestObject rM = new RequestObject(ActionKind.FETCH);
        rM.receiverID = receiverID;
        return rM;
    }

    public static RequestObject FetchComplete(String receiverID) {
        RequestObject rM = new RequestObject(ActionKind.FETCHCOMPLETE);
        rM.receiverID = receiverID;
        return rM;
    }
}

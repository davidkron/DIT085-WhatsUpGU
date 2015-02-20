package main.server.response;

import main.messagestore.Message;
import java.util.HashMap;

public class Response {
    public ResponseKind kind;
    public String ID;
    public String senderID;
    public String recieverID;
    public int messageID;
    public HashMap<Integer, Message> messages = new HashMap<>();
    public String Error = null;

    public Response(ResponseKind kind, String ID) {
        this.kind = kind;
        this.ID = ID;
    }

    public Response(ResponseKind kind){
        this.kind = kind;
    }

    public static Response Added(int messageID){
        Response rM = new Response(ResponseKind.ADDEDMESSAGE);
        rM.messageID = messageID;
        return rM;
    }

    public static Response AlreadyConnected(String ID){
        Response rM = new Response(ResponseKind.REFUSEDCONNECTION);
        rM.ID = ID;
        rM.Error = "Already connected";
        return rM;
    }

    public static Response Connected(String id) {
        Response rM = new Response(ResponseKind.ACCEPTEDCONNECTION);
        rM.ID = id;
        return rM;
    }

    public static Response Deleted(int messageID) {
        Response rM = new Response(ResponseKind.DELETEDMESSAGE);
        rM.messageID = messageID;
        return rM;
    }

    public static Response Replaced(int messageID) {
        Response rM = new Response(ResponseKind.MESSAGEREPLACED);
        rM.messageID = messageID;
        return rM;
    }

    public static Response ErrorDeleting(int messageID, String error) {
        Response rM = new Response(ResponseKind.DELETEMESSAGE_FAILED);
        rM.messageID = messageID;
        rM.Error = error;
        return rM;
    }

    public static Response AddedFailed(int messageID,String error) {
        Response rM = new Response(ResponseKind.ADDINGMESSAGE_FAILED);
        rM.messageID = messageID;
        rM.Error = error;
        return rM;
    }
    public static Response Fetched(int messageID,String recieverID, String senderID) {
        Response rM = new Response(ResponseKind.FETCHED);
        rM.messageID = messageID;
        rM.recieverID = recieverID;
        rM.senderID = senderID;
        return rM;
    }
}
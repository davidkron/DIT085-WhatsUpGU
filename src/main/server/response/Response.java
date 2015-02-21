package main.server.response;

import main.messagestore.Message;
import main.server.request.ActionKind;

import java.util.HashMap;

public class Response {
    public ActionKind kind;
    public String ID;
    public String senderID;
    public String recieverID;
    public int messageID;
    public HashMap<Integer, Message> messages = new HashMap<>();
    public String Error = null;

    public Response(ActionKind kind, String ID) {
        this.kind = kind;
        this.ID = ID;
    }

    public Response Error(String error){
        this.Error = error;
        return this;
    }

    public Response(ActionKind kind){
        this.kind = kind;
    }

    public static Response Added(int messageID){
        Response rM = new Response(ActionKind.ADD);
        rM.messageID = messageID;
        return rM;
    }

    public static Response Connected(String id) {
        Response rM = new Response(ActionKind.CONNECT);
        rM.ID = id;
        return rM;
    }

    public static Response Deleted(int messageID) {
        Response rM = new Response(ActionKind.REMOVE);
        rM.messageID = messageID;
        return rM;
    }

    public static Response Replaced(int messageID) {
        Response rM = new Response(ActionKind.REPLACE);
        rM.messageID = messageID;
        return rM;
    }
    public static Response Fetched(int messageID,String recieverID, String senderID) {
        Response rM = new Response(ActionKind.FETCH);
        rM.messageID = messageID;
        rM.recieverID = recieverID;
        rM.senderID = senderID;
        return rM;
    }
}
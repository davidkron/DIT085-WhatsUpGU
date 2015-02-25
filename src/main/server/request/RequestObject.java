package main.server.request;

import main.messagestore.Message;

import java.util.List;

public class RequestObject {
    public ActionKind kind;
    public String ID;
    public int messageID;
    public String receiverID;
    public String senderID;
    public String content;
    public String Error;
    public List<Message> fetchedMessages;

    public RequestObject(ActionKind kind){
        this.kind = kind;
    }

}

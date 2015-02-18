package main.messagestore;

public class Message {
    public boolean isfetching = false;
    public String id;
    public String senderId;
    public String receiverId;
    public String text;
    public Message(String message, String messageId, String fromId, String toId) {
        id = messageId;
        senderId = fromId;
        receiverId = toId;
        text = message;
    }
}

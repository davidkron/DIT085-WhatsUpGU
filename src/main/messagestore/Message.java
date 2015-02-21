package main.messagestore;

public class Message {
    public boolean isfetching = false;
    public int id;
    public String senderId;
    public String receiverId;
    public String text;

    public Message(String message, int messageId, String fromId, String toId) {
        id = messageId;
        senderId = fromId;
        receiverId = toId;
        text = message;
    }
}

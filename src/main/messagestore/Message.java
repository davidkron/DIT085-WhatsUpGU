package main.messagestore;

public class Message {
    public boolean isfetching = false;
    public final int id;
    public final String senderId;
    public final String receiverId;
    public String text;

    public Message(String message, int messageId, String fromId, String toId) {
        id = messageId;
        senderId = fromId;
        receiverId = toId;
        text = message;
    }
}

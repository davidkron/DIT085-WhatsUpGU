package main.messagestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages implements IMessageCollection {
    private HashMap<Integer, Message> messages = new HashMap<>();
    private Integer previousMessageId = 0;
    private HashMap<String, ArrayList<Integer>> seenMesssages = new HashMap<>();

    @Override
    public Message get(int index) {
        return messages.get(index);
    }

    /**
     * Description
     * <p/>
     * Pre-conditions:
     * Message is a valid non-empty string.
     * Sender ID is a valid phone number
     * Reciever ID is a valid phone number
     * Post-condition:
     * Zero or less is returned
     * <p/>
     * Test-cases:
     */

    @Override
    public int add(String message, String senderId, String receiverId) {
        if (isEmpty(message)) return 0;
        if (invalidPhoneNumber(senderId)) return 0;
        if (invalidPhoneNumber(receiverId)) return 0;

        int messageID = ++previousMessageId;
        messages.put(messageID, new Message(message, messageID, senderId, receiverId));

        return messageID;
    }

    @Override
    public int delete(int messageId) {
        if(messages.get(messageId) == null ||  messages.get(messageId).isfetching)
            return 0;
        else{
            messages.remove(messageId);
            return messageId;
        }
    }

    @Override
    public int replace(int messageId, String text) {
        if (isEmpty(text)) return 0;

        Message message = get(messageId);
        if (message == null) return 0;

        message.text = text;
        messages.put(messageId, message);

        return messageId;
    }

    @Override
    public List<Message> fetch(String recieverId) {
        List<Message> userMessages = new ArrayList<>();
        for (Message message : messages.values()) {
            if (message.receiverId.equals(recieverId) && !message.isfetching) {
                message.isfetching = true;
                userMessages.add(message);
            }
        }
          return userMessages;
    }

    @Override
    public int fetchComplete(String recieverId) {
        boolean messagesRemoved = false;

        for (Map.Entry<Integer, Message> entry : messages.entrySet()) {
            Integer key = entry.getKey();
            Message message = entry.getValue();

            if (message.receiverId.equals(recieverId) && message.isfetching) {
                messages.remove(key);

                ArrayList<Integer> senderList = seenMesssages.get(message.senderId);
                if (senderList == null) senderList = new ArrayList<>();
                senderList.add(key);
                seenMesssages.put(message.senderId, senderList);

                if (!messagesRemoved) messagesRemoved = true;
            }
        }

        if (messagesRemoved) return 1;
        return 0;
    }

    @Override
    public List<Integer> getSeen(String senderId) {
        return seenMesssages.get(senderId);
    }

    /**
     * Checks provided string whether it's empty.
     *
     * @param message string to be checked.
     * @return true if empty, otherwise false.
     */
    private boolean isEmpty(String message) {
        return message.length() < 1;
    }

    /**
     * Checks provided string whether it's a valid
     * mobile telephone number. Has to be numeric
     * and contain 10 digits.
     *
     * @param number string to be checked.
     * @return true if valid, otherwise false.
     */
    private boolean invalidPhoneNumber(String number) {
        return number.length() != 10 || !number.matches("(\\d+)");
    }
}
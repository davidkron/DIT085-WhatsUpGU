package main;

import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {
    private HashMap<Integer, Message> messages  = new HashMap<Integer, Message>();
    private Integer previousMessageId = 0;
    private HashMap<String, ArrayList<Integer>> seenMesssages = new HashMap<String, ArrayList<Integer>>();

    public Message get(int index) {
        return messages.get(index);
    }

    public int add(String message, String senderId, String recieverId){
        if (isEmpty(message)) return 0;
        if (! isValidPhoneNumber(senderId)) return 0;
        if (! isValidPhoneNumber(recieverId)) return 0;

        int messageID = ++previousMessageId;
        messages.put(messageID, new Message(message, String.valueOf(messageID), senderId, recieverId));

        return messageID;
    }

    public String fetch(String recieverId){
        Element root = new Element("items");
        for (Message message : messages.values()) {
            if (message.receiverId.equals(recieverId) && ! message.isfetching) {
                message.isfetching = true;

                Element item = new Element("item");

                Element idItem = new Element("Id");
                idItem.setText(message.id);
                item.addContent(idItem);

                Element messageItem = new Element("Message");
                messageItem.setText(message.text);
                item.addContent(messageItem);

                Element senderItem = new Element("Sender");
                senderItem.setText(message.senderId);
                item.addContent(senderItem);

                root.addContent(item);
            }
        }

        return new XMLOutputter().outputString(root);
    }

    public int replace(int messageId, String text){
        if (isEmpty(text)) return 0;

        Message message = get(messageId);
        if (message == null) return 0;

        message.text = text;
        messages.put(messageId, message);

        return messageId;
    }

    public int fetchComplete(String recieverId){
        boolean messagesRemoved = false;

        for (Map.Entry<Integer, Message> entry : messages.entrySet()) {
            Integer key = entry.getKey();
            Message message = entry.getValue();

            if (message.receiverId.equals(recieverId) && message.isfetching) {
                messages.remove(key);

                ArrayList<Integer> senderList = seenMesssages.get(message.senderId);
                if (senderList == null) {
                    senderList = new ArrayList<Integer>();
                    senderList.add(key);
                    seenMesssages.put(message.senderId, senderList);
                } else {
                    senderList.add(key);
                }

                if (! messagesRemoved) messagesRemoved = true;
            }
        }

        if (messagesRemoved) return 1; return 0;
    }

    public List<Integer> getSeen(String senderId){
        return seenMesssages.get(senderId);
    }

    public int delete(int messageId) {
        Message previousValue = messages.remove(messageId);

        if (previousValue == null) return 0;

        return messageId;
    }

    /**
     * Checks provided string whether it's empty.
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
     * @param number string to be checked.
     * @return true if valid, otherwise false.
     */
    private boolean isValidPhoneNumber(String number) {
        return number.length() == 10 && number.matches("(\\d+)");
    }
}
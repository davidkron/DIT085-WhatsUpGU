import java.util.HashMap;

public class Messages {
    private static HashMap<String, HashMap<Integer, Message>> messages  = new HashMap<String, HashMap<Integer, Message>>();
    private static Integer previousMessageId = 0;

    public static Message get(int index) {
        Message message = null;

        for (HashMap<Integer, Message> messagesMap : messages.values()) {
            message = messagesMap.get(index);
            if (message != null) break;
        }

        return message;
    }

    public static int add(String message, String senderId, String recieverId){
        if (isEmpty(message)) return 0;
        if (! isValidPhoneNumber(senderId)) return 0;
        if (! isValidPhoneNumber(recieverId)) return 0;

        if (messages.containsKey(recieverId)) {
            messages.get(recieverId).put(++previousMessageId, new Message(message));
        } else {
            HashMap<Integer, Message> messagesMap = new HashMap<Integer, Message>();
            messagesMap.put(++previousMessageId, new Message(message));
            messages.put(recieverId, messagesMap);
        }

        return previousMessageId;
    }

    public static String fetch(String recieverId){
        HashMap<Integer, Message> messagesMap = messages.get(recieverId);
        if (messagesMap == null) return "<null></null>";

        return "<Item>" +
                    "<Id>1</Id>" +
                    "<Message>Hello</Message>" +
                    "<Sender>0767731855</Sender>" +
                "</Item>";
    }

    public static int replace(int messageId, String message){
        if (isEmpty(message)) return 0;

        Message value = null;

        for (HashMap<Integer, Message> messagesMap : messages.values()) {
            value = messagesMap.put(messageId, new Message(message));
            if (value != null) break;
        }

        if (value == null) return 0;

        return messageId;
    }

    public static int delete(int messageId) {
        Message previousValue = null;

        for (HashMap<Integer, Message> messagesMap : messages.values()) {
            previousValue = messagesMap.remove(messageId);
            if (previousValue != null) break;
        }

        if (previousValue == null) return 0;

        return messageId;
    }

    /**
     * Checks provided string whether it's empty.
     * @param message string to be checked.
     * @return true if empty, otherwise false.
     */
    private static boolean isEmpty(String message) {
        return message.length() < 1;
    }

    /**
     * Checks provided string whether it's a valid
     * mobile telephone number. Has to be numeric
     * and contain 10 digits.
     * @param number string to be checked.
     * @return true if valid, otherwise false.
     */
    private static boolean isValidPhoneNumber(String number) {
        return number.length() == 10 && number.matches("(\\d+)");
    }
}
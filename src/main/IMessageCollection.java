package main;

import java.util.List;

/**
 * Created by david on 2/11/15.
 */
public interface IMessageCollection {
    Message get(int index);

    int add(String message, String senderId, String recieverId);

    String fetch(String recieverId);

    int replace(int messageId, String text);

    int fetchComplete(String recieverId);

    List<Integer> getSeen(String senderId);

    int delete(int messageId);
}

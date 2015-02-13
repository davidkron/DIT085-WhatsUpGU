package main;

import java.util.List;

public interface IMessageCollection {
    /**
     * Retrieves one message from list of messages given its ID.
     * @param index the ID of the message.
     * @return Message object if successful, otherwise null.
     * @test GetMessages.NotAdded()
     * @test GetMessages.Removed()
     * @test GetMessages.AddedNotRemoved()
     */
    Message get(int index);

    /**
     * Adds one message to list of messages.
     * @param message the non-empty text contents of the message.
     * @param senderId ID of the sender(from), a valid telephone number.
     * @param recieverId ID of the receiver(to), a valid telephone number.
     * @return the unique ID integer of the message if successful,
     * otherwise less than or 0 denoting error.
     * @test AddingMessage.testEmptyMessage()
     * @test AddingMessage.testInvalidSenderId()
     * @test AddingMessage.testInvalidRecieverId()
     * @test AddingMessage.testMessageIsAdded()
     * @test AddingMessage.testGetsUniquePositiveId()
     * @test AddingMessage.testNotFetching()
     */
    int add(String message, String senderId, String recieverId);

    /**
     * Removes a message from list of messages given its ID.
     * @param messageId the ID of the message
     * @return the unique ID integer of the message if successful,
     * otherwise less than or 0 denoting error.
     * @test DeletingMessage.testDeleteUnexistingMessage()
     * @test DeletingMessage.testMessageIsRemoved()
     * @test DeletingMessage.testMessageIdReturned()
     */
    int delete(int messageId);

    /**
     * Replaces text contents of a message given its ID.
     * @param messageId the ID of the message.
     * @param text the non-empty text of the message to be replaced with.
     * @return the unique ID integer of the message if successful,
     * otherwise less than or 0 denoting error.
     * @test ReplacingMessage.testEmptyMessage()
     * @test ReplacingMessage.testNonExisting()
     * @test ReplacingMessage.testSpecifiedIdReturned()
     * @test ReplacingMessage.testMessageReplaced()
     */
    int replace(int messageId, String text);

    /**
     * Fetches all messages that belong to the receiver and returns them
     * in XML format in a string.
     *
     * XML format for each item expected:
     * <item>
     *     <Id>The ID of the message.</Id>
     *     <Message>The text contents of the message.</Message>
     *     <Sender>The ID of the sender.</Sender>
     * </item>
     * @param recieverId ID of the receiver(to), a valid telephone number.
     * @return string of valid XML of items if successful,
     * otherwise string of valid XML of empty root.
     * @test FetchingMessages.testFetchMessageNotExists()
     * @test FetchingMessages.testFetchingMessageNotFetched()
     * @test FetchingMessages.testSetToFetchingStatus()
     * @test FetchingMessages.testContainsMessageOnSuccess()
     * @test FetchingMessages.testContainsRightSender()
     * @test FetchingMessages.testContainsRightID()
     */
    String fetch(String recieverId);

    /**
     * Removes fetched messages from the messages list and notifies senders(from)
     * that their messages have been seen by the receiver(to).
     * @param recieverId ID of the receiver(to), a valid telephone number.
     * @return a positive number if messages have been fetched successfully,
     * otherwise less than or 0 denoting error.
     * @test FetchCompletion.testWithoutFetchFirst()
     * @test FetchCompletion.testWithUnsuccessfulFetchFirst()
     * @test FetchCompletion.testWithSuccessfulFetchFirst()
     * @test FetchCompletion.testFetchedRemoved()
     */
    int fetchComplete(String recieverId);


    /**
     * Retrieves the list of message IDs for the sender(from)
     * that have been seen by the receiver(to).
     * @param senderId ID of the sender(from), a valid telephone number.
     * @return list of message ID integers if successful, otherwise an empty list.
     * @test FetchCompletion.testSignaledSeen()
     */
    List<Integer> getSeen(String senderId);
}

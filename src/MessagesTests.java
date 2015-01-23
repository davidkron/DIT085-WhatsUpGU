import junit.framework.TestCase;
import org.junit.Test;

public class MessagesTests extends TestCase {

    /*
    adding a non-empty string message from a given Id (the sender's telephone number)
    for a given ID (the recipients telephone number);
    the message is supposed to be stored at the server side until it is fetched by the recipient.
    For simplicity, we assume that the message is stored in the memory.
    Upon successful addition of the message a unique positive integer as the identifier of the added message is returned;
    upon failure a value indicating error (0 or a negative number is returned).
    */
    @Test
    public void testSendingEmptyMessage() throws Exception {
        assertFalse(Messages.Add(""));
    }

    @Test
    public void testSendingMessage() throws Exception {
        assertTrue(Messages.Add("Hello"));
    }

    @Test
    public void testSendExactMessage() throws Exception {
        assertTrue(Messages.Add("Hello"));
        assertTrue(Messages.Exists("Hello"));
    }


    @Test
    public void testEnsureLatestIsRemovedOnDelete() throws Exception {
        Messages.Add("A");
        Messages.Add("B");
        Messages.Add("C");
        assertFalse(Messages.IsEmpty());
        assertFalse(Messages.Exists("C"));
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(Messages.Delete());
    }

//    @Test
//    public void testDeleteWhenEmpty() throws Exception {
//        assertTrue(Messages.Delete());
//    }

    @Test
    public void testFetch() throws Exception {

    }

    @Test
    public void testEdit() throws Exception {

    }

    /*
               INTEGRATION TESTS
     */


    @Test
    public void testFetchingAddedMessage() throws Exception {
        assertTrue(Messages.Add("Hello"));
        assertTrue(Messages.Fetch().contains("Hello"));
        Messages.SignalCompletion();
        assertTrue(Messages.Fetch().isEmpty());
    }
}
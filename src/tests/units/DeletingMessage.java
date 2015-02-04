import org.junit.Test;

import static org.junit.Assert.*;

public class DeletingMessage {

    //Zero or less is returned when the specified ID does not exist.
    @Test
    public void testDeleteUnexistingMessage() throws Exception {
        assertNull(Messages.get(0));
        assertTrue(Messages.delete(0) <= 0);
    }

    //The message does not exist after removal.
    @Test
    public void testMessageIsRemoved() throws Exception {
        int id = Messages.add("Hello","0767731855","0767731855");
        assertNotNull(Messages.get(id));
        assertFalse(Messages.get(id).isfetching);
        Messages.delete(id);
        assertNull(Messages.get(id));
    }

    //The specified ID is returned on deletion.
    @Test
    public void testMessageIdReturned(){
        int id = Messages.add("Hello","0767731855","0767731855");
        assertTrue(Messages.delete(id) == id);
    }
}
package tests.units.server;

import org.junit.Test;

public class XMLEncoderTest {

    @Test
    public void testRefusedConnection() throws Exception {

    }

    @Test
    public void testAcceptedConnection() throws Exception {
        String ID = "";
        String acceptString = "<Accepted connection from  " + ID + " +/>";
    }


    @Test
    public void testAddMessage() throws Exception {
        String acceptRegex = "<Message added: \"d+\" />";
    }

}
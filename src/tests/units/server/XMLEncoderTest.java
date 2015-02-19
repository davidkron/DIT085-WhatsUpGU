package tests.units.server;

import main.ReturnKind;
import main.ReturnMessage;
import main.XMLEncoder;
import org.junit.Test;

public class XMLEncoderTest {

    @Test
    public void testRefusedConnection() throws Exception {

    }

    @Test
    public void testAcceptedConnection() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(new ReturnMessage(ReturnKind.ACCEPTEDCONNECTION,ID));
        String acceptString = "<Accepted connection from  " + ID + " +/>";
    }

    @Test
    public void testAddMessage() throws Exception {
        String ID = "0767731855";
        String xml = XMLEncoder.AcceptedConnection(new ReturnMessage(ReturnKind.ACCEPTEDCONNECTION,ID));
        String acceptRegex = "<Message added: \"d+\" />";
    }

}
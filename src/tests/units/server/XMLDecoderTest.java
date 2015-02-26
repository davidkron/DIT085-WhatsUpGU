package tests.units.server;

import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLDecoder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class XMLDecoderTest {

    private final String ID = "5";

    @Test
    public void testDecodeReplace() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                    "<replace>" +
                        "<messageID>5</messageID>" +
                        "<content>NEW HELLO</content>" +
                    "</replace>" +
                "</messageAction>", ID);

        assertEquals(msg.kind, ActionKind.REPLACE);
        assertEquals(msg.content, "NEW HELLO");
        assertEquals(msg.messageID, 5);
    }

    @Test
    public void testDecodeDelete() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                        "<delete>" +
                        "<messageID>5</messageID>" +
                        "</delete>" +
                        "</messageAction>", ID);

        assertEquals(msg.kind, ActionKind.REMOVE);
        assertEquals(msg.messageID, 5);
    }

    @Test
    public void testDecodeFetch() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                        "<fetch>" +
                         "true" +
                        "</fetch>" +
                        "</messageAction>", ID);

        assertEquals(ActionKind.FETCH,msg.kind);
    }


    @Test
    public void testDecodeFetchComplete() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                        "<fetchComplete>" +
                        "true" +
                        "</fetchComplete>" +
                        "</messageAction>", ID);

        assertEquals(ActionKind.FETCHCOMPLETE,msg.kind);
    }


    @Test
    public void testDecodeAdd() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                        "<add>" +
                        "<receiverID>5</receiverID>" +
                        "<content>HELLO</content>" +
                        "</add>" +
                        "</messageAction>", ID);

        assertEquals(ActionKind.ADD, msg.kind);
        assertEquals("5", msg.receiverID);
        assertEquals( "HELLO", msg.content);
    }


    @Test
    public void testDecodeConnect() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                        "<connection>" +
                        "<ID>5</ID>" +
                        "</connection>" +
                        "</messageAction>", ID);

        assertEquals(ActionKind.CONNECT, msg.kind);
        assertEquals("5", msg.ID);

    }




}
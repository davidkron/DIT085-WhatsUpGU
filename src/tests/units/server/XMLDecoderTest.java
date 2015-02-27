package tests.units.server;

import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLDecoder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class XMLDecoderTest {
    private final String ID = "5";

    @Before
    public void setUp() throws Exception {
        // SILENCE COVERAGE FOR STATIC OBJECTS
        new XMLDecoder();
    }

    @Test
    public void testDecodeReplace() throws Exception {
        RequestObject msg = XMLDecoder.decode(
            "<replace>" +
                "<messageID>5</messageID>" +
                "<content>NEW HELLO</content>" +
            "</replace>",
            ID
        );

        assertEquals(msg.kind, ActionKind.REPLACE);
        assertEquals(msg.content, "NEW HELLO");
        assertEquals(msg.messageID, 5);
    }

    @Test
    public void testDecodeDelete() throws Exception {
        RequestObject msg = XMLDecoder.decode(
            "<delete>" +
                "<messageID>5</messageID>" +
            "</delete>",
            ID
        );

        assertEquals(msg.kind, ActionKind.REMOVE);
        assertEquals(msg.messageID, 5);
    }

    @Test
    public void testDecodeFetch() throws Exception {
        RequestObject msg = XMLDecoder.decode("<fetch>true</fetch>", ID);

        assertEquals(ActionKind.FETCH, msg.kind);
    }

    @Test
    public void testDecodeFetchComplete() throws Exception {
        RequestObject msg = XMLDecoder.decode(
            "<fetchComplete>true</fetchComplete>",
            ID
        );

        assertEquals(ActionKind.FETCHCOMPLETE,msg.kind);
    }

    @Test
    public void testDecodeAdd() throws Exception {
        RequestObject msg = XMLDecoder.decode(
            "<add>" +
                "<receiverID>5</receiverID>" +
                "<content>HELLO</content>" +
            "</add>",
            ID
        );

        assertEquals(ActionKind.ADD, msg.kind);
        assertEquals("5", msg.receiverID);
        assertEquals("HELLO", msg.content);
    }

    @Test
    public void testDecodeConnect() throws Exception {
        RequestObject msg = XMLDecoder.decode(
            "<connection>" +
                "<ID>5</ID>" +
            "</connection>",
            ID
        );

        assertEquals(ActionKind.CONNECT, msg.kind);
        assertEquals("5", msg.ID);
    }

    @Test
    public void testFailDecode() throws Exception {
        RequestObject msg = XMLDecoder.decode("<bogusTag>fail</bogusTag>", ID);

        assertEquals(msg, null);
    }
}
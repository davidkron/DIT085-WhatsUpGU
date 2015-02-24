package tests.units.server;

import main.server.request.ActionKind;
import main.server.request.RequestObject;
import main.server.request.XMLDecoder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class XMLDecoderTest {

    @Test
    public void testDecodeReplace() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                    "<replace>" +
                        "<messageID>5</messageID>" +
                        "<content>NEW HELLO</content>" +
                    "</replace>" +
                "</messageAction>");

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
                        "</messageAction>");

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
                        "</messageAction>");

        assertEquals(ActionKind.FETCH,msg.kind);
    }


    @Test
    public void testDecodeFetchComplete() throws Exception {
        RequestObject msg = XMLDecoder.decode(
                "<messageAction>" +
                        "<fetch>" +
                        "true" +
                        "</fetch>" +
                        "</messageAction>");

        assertEquals(ActionKind.FETCHCOMPLETE,msg.kind);
    }


}
package tests.units.server;

import main.server.request.RequestKind;
import main.server.request.RequestMessage;
import main.server.request.XMLDecoder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class XMLDecoderTest {

    @Test
    public void testDecodeReplace() throws Exception {
        RequestMessage msg = XMLDecoder.decode(
                "<messageAction>" +
                    "<replace>" +
                        "<messageID>5</messageID>" +
                        "<content>NEW HELLO</content>" +
                    "</replace>" +
                "</messageAction>");

        assertEquals(msg.kind, RequestKind.REPLACE);
        assertEquals(msg.content, "NEW HELLO");
    }
}
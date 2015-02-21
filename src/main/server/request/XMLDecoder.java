package main.server.request;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;

public class XMLDecoder {

    public static int getMessageId(Element action){
        return Integer.parseInt(action.getChildren("messageID").get(0).getText());
    }

    public static String getContent(Element action){
        return action.getChildren("content").get(0).getText();
    }

    public static RequestMessage decode(String xml) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new StringReader(xml));

        Element action = doc.getRootElement().getChildren().get(0);
        String actionName = action.getName();

        switch (actionName) {
            case "request":
                return RequestMessage.ConnectRequest(action.getText());
            case "replace":
                return RequestMessage.ReplaceRequest(
                        getMessageId(action),
                        getContent(action)
                );
            case "delete":
                return RequestMessage.DeleteRequest(getMessageId(action));
        }

        return new RequestMessage(ActionKind.CONNECT);
    }
}

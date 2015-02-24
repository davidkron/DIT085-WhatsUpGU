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


    public static RequestObject decode(String xml) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new StringReader(xml));

        Element action = doc.getRootElement().getChildren().get(0);
        String actionName = action.getName();

        switch (actionName) {
            case "request":
                return RequestObject.ConnectRequest(action.getText());
            case "replace":
                return RequestObject.ReplaceRequest(
                        getMessageId(action),
                        getContent(action)
                );
            case "delete":
                return RequestObject.DeleteRequest(getMessageId(action));
            case "fetch":
                return RequestObject.FetchRequest(getContent(action));
        }

        return new RequestObject(ActionKind.CONNECT);
    }
}

package main.server.request;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;

public class XMLDecoder
{
    private static int getMessageId(Element action){
        return Integer.parseInt(action.getChildren("messageID").get(0).getText());
    }
    private static String getreceiverID(Element action){
        return (action.getChildren("receiverID").get(0).getText());
    }

    private static String getContent(Element action){

        return action.getChildren("content").get(0).getText();
    }

    public static RequestObject decode(String xml, String ID) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new StringReader(xml));
        Element action = doc.getRootElement();
        String actionName = action.getName();

        switch (actionName) {
            case "replace":
                return RequestCreator.ReplaceRequest(
                    getMessageId(action),
                    getContent(action)
                );
            case "delete":
                return RequestCreator.DeleteRequest(getMessageId(action));
            case "fetch":
                return RequestCreator.FetchRequest(ID);
            case "fetchComplete":
                return RequestCreator.FetchComplete(ID);
            case "add":
                return  RequestCreator.AddRequest(
                    getContent(action),
                    ID,
                    getreceiverID(action)
                );
            case "connection":
                return RequestCreator.ConnectRequest(action.getChildren("request").get(0).getText());
             default:
                throw new JDOMException();
        }
    }
}

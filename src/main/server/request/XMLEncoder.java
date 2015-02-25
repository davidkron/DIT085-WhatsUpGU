package main.server.request;

import main.messagestore.Message;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class XMLEncoder {

    public static String encode(RequestObject message) {

        if(message.Error != null) {
            Element root = new Element("error");
            root.addContent(message.Error);
            return new XMLOutputter().outputString(root);
        }
        switch (message.kind) {
            case CONNECT:
                return AcceptedConnection(message);
            case REPLACE:
                return Replaced(message);
            case REMOVE:
                return Deleted(message);
            case FETCH:
                return Fetched(message);
            case ADD:
                return Added(message);
            case FETCHCOMPLETE:
                return FetchCompleted(message);
        }

        return null;
    }

    public static String AcceptedConnection(RequestObject retmsg) {
        Element root = new Element("connection");
        Element accepted = new Element("accepted");
        accepted.addContent(retmsg.ID);
        root.addContent(accepted);

        return new XMLOutputter().outputString(root);
    }


    public static String Added(RequestObject retmsg){
        Element root = new Element("added");
        root.addContent(String.valueOf(retmsg.messageID));

        return new XMLOutputter().outputString(root);
    }
    public static String Deleted(RequestObject retmsg){

        Element root = new Element("deleted");
        root.addContent(String.valueOf(retmsg.messageID));
        return new XMLOutputter().outputString(root);

        //return "</>";
    }
    public static String Replaced(RequestObject retmsg){
        return "</>";
    }

    public static String Fetched(RequestObject retmsg){
        Element root = new Element("fetched");
        Element fetched = new Element("message");
        Element receiverid = new Element("receiverID");
        receiverid.addContent(String.valueOf(retmsg.receiverID));

        for(Message m : retmsg.fetchedMessages) {
            Element senderid = new Element("senderID");
            fetched.addContent(receiverid);
            senderid.addContent(String.valueOf(m.senderId));
            Element content = new Element("content");
            fetched.addContent(senderid);
            content.addContent(String.valueOf(m.text));
            fetched.addContent(content);
        }
        root.addContent(fetched);

        return new XMLOutputter().outputString(root);
    }

    public static String FetchCompleted(RequestObject retmsg){
        Element root = new Element("fetchCompleted");
        root.addContent(retmsg.content);
        return new XMLOutputter().outputString(root);
    }
}

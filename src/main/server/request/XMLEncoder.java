package main.server.request;

import main.messagestore.Message;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class XMLEncoder {

    public static String encode(RequestObject message) {
        switch (message.kind) {
            case CONNECT:
                if(message.Error != null){
                    return RefusedConnection(message);
                }
                return AcceptedConnection(message);
            case REPLACE:
                if(message.Error != null){
                    return FailedReplacing(message);
                }
                return Replaced(message);
            case REMOVE:
                if(message.Error != null){
                    return FailedDeleting(message);
                }
                return Deleted(message);
            case FETCH:
                if(message.Error != null){
                    return FailedFetching(message);
                }
                return Fetched(message);
            case ADD:
                if(message.Error != null){
                    return FailedAdding(message);
                }
                return Added(message);
            case FETCHCOMPLETE:
                if(message.Error != null){
                    return FailedFetchCompleting(message);
                }
                return FetchCompleted(message);
        }

        return null;
    }

    private static String FailedReplacing(RequestObject message) {
        return null;
    }

    public static String RefusedConnection(RequestObject retmsg) {
        Element root = new Element("connection");
        Element refused = new Element("refused");
        refused .addContent(retmsg.ID);
        root.addContent(refused);

        return new XMLOutputter().outputString(root);
    }

    public static String AcceptedConnection(RequestObject retmsg) {
        Element root = new Element("connection");
        Element accepted = new Element("accepted");
        accepted.addContent(retmsg.ID);
        root.addContent(accepted);

        return new XMLOutputter().outputString(root);
    }


    public static String Added(RequestObject retmsg){
        Element root = new Element("messageActionResponse");
        Element added = new Element("added");
        added.addContent(String.valueOf(retmsg.messageID));
        root.addContent(added);

        return new XMLOutputter().outputString(root);
    }
    public static String Deleted(RequestObject retmsg){

        Element root = new Element("delete");
        Element deleted = new Element("deleted");
        deleted.addContent(String.valueOf(retmsg.messageID));
        root.addContent(deleted);
        return new XMLOutputter().outputString(root);

        //return "</>";
    }
    public static String Replaced(RequestObject retmsg){
        return "</>";
    }

    public static String Fetched(RequestObject retmsg){

        ArrayList<Message> fetchedMessages = retmsg.fetchedMessages;
        Element root = new Element("fetched");
        Element fetched = new Element("message");
        Element receiverid = new Element("receiverID");
        receiverid.addContent(String.valueOf(retmsg.receiverID));

        for(Message m:fetchedMessages) {
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
        return "</>";
    }


    /*                  FAILS               */

    public static String FailedFetching(RequestObject retmsg){
        return "</>";
    }
    public static String FailedDeleting(RequestObject retmsg){
        return "</>";
    }
    public static String FailedFetchCompleting(RequestObject retmsg){
        return "</>";
    }
    public static String FailedAdding(RequestObject retmsg){
        return "</>";
    }
}

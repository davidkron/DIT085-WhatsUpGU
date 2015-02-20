package main.server.response;

import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class XMLEncoder {

    public static String encode(Response message) {
        switch (message.kind) {
            case ACCEPTEDCONNECTION:
                return AcceptedConnection(message);
            case REFUSEDCONNECTION:
                return RefusedConnection(message);
            case MESSAGEREPLACED:
                return Replaced(message);
            case DELETEDMESSAGE:
                return Deleted(message);
            case FETCHED:
                return Fetched(message);
            case FETCHCOMPLETE:
                return FetchCompleted(message);

            /// FAILS
            case FETCHCOMPLETE_FAILED:
                return FailedFetchCompleting(message);
            case FETCHED_FAILED:
                return FailedFetching(message);
            case DELETEMESSAGE_FAILED:
                return FailedDeleting(message);
            case ADDINGMESSAGE_FAILED:
                return FailedAdding(message);
        }

        return null;
    }

    public static String RefusedConnection(Response retmsg) {
        Element root = new Element("connection");
        Element refused = new Element("refused");
        refused .addContent(retmsg.ID);
        root.addContent(refused);

        return new XMLOutputter().outputString(root);
    }

    public static String AcceptedConnection(Response retmsg) {
        Element root = new Element("connection");
        Element accepted = new Element("accepted");
        accepted.addContent(retmsg.ID);
        root.addContent(accepted);

        return new XMLOutputter().outputString(root);
    }

    public static String Added(Response retmsg){
        Element root = new Element("messageActionResponse");
        Element added = new Element("added");
        added.addContent(String.valueOf(retmsg.messageID));
        root.addContent(added);

        return new XMLOutputter().outputString(root);
    }
    public static String Deleted(Response retmsg){
        return "</>";
    }
    public static String Replaced(Response retmsg){
        return "</>";
    }

    public static String Fetched(Response retmsg){
        return "</>";
    }

    public static String FetchCompleted(Response retmsg){
        return "</>";
    }


    /*                  FAILS               */

    public static String FailedFetching(Response retmsg){
        return "</>";
    }
    public static String FailedDeleting(Response retmsg){
        return "</>";
    }
    public static String FailedFetchCompleting(Response retmsg){
        return "</>";
    }
    public static String FailedAdding(Response retmsg){
        return "</>";
    }
}

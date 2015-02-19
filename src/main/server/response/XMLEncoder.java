package main.server.response;

/**
 * Created by david on 2/19/15.
 */

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
                return Deleted(message);
            case FETCHCOMPLETE:
                return Deleted(message);

            /// FAILS
            case FETCHCOMPLETE_FAILED:
                return Deleted(message);
            case FETCHED_FAILED:
                return Deleted(message);
            case DELETEMESSAGE_FAILED:
                return Deleted(message);
            case ADDINGMESSAGE_FAILED:
                return FailedAdding(message);
        }

        return null;
    }

    public static String RefusedConnection(Response retmsg) {
        return "<Declined connection from  \"" + retmsg.ID + "\" +/>";
    }

    public static String AcceptedConnection(Response retmsg) {
        return "<Accepted connection from  \"" + retmsg.ID + "\" +/>";
    }

    public static String Added(Response retmsg){
        return "</>";
    }
    public static String Deleted(Response retmsg){
        return "</>";
    }
    public static String Replaced(Response retmsg){
        return "</>";
    }


    public static String FailedAdding(Response retmsg){
        return "</>";
    }
}

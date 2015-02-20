package main.server.response;

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

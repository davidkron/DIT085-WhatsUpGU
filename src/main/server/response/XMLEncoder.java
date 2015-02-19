package main.server.response;

/**
 * Created by david on 2/19/15.
 */

public class XMLEncoder {

    public static String encode(Response message) {
        switch (message.kind) {
            case ACCEPTEDCONNECTION:
                return AcceptedConnection(message);
        }

        return null;
    }


    public static String RefusedConnection(Response retmsg) {
        return "<Declined connection from  \"" + retmsg.ID + "\" +/>";
    }

    public static String AcceptedConnection(Response retmsg) {
        return "<Accepted connection from  \"" + retmsg.ID + "\" +/>";
    }
}

package main;

/**
 * Created by david on 2/19/15.
 */

public class XMLEncoder {

    public static String encode(ReturnMessage message){
        switch (message.kind){
            case ACCEPTEDCONNECTION:
                return AcceptedConnection(message);
        }

        return null;
    }


    public static String RefusedConnection(ReturnMessage retmsg){
        return "<Declined connection from  " + retmsg.ID + " +/>";
    }

    public static String AcceptedConnection(ReturnMessage retmsg){
        return "<Accepted connection from  " + retmsg.ID + " +/>";
    }
}

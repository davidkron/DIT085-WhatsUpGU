package main;

import java.util.List;
import java.util.Stack;

/**
 * Created by david on 1/23/15.
 */
public class Messages {
    public static int add(String message,String SenderId,String RecieverId){
        return 0;
    }

    public static String fetch(String reciever){
        return "<Item>" +
                    "<Id>1</Id>" +
                    "<Message>Hello</Message>" +
                    "<Sender>0767731855</Sender>" +
                "</Item>";
    }

    public static int replace(int messageId,String message){
        return 0;
    }

    public static int delete(int messageId){
        return 0;
    }

    public static boolean exists(int id){
        return true;
    }
}

import java.util.List;
import java.util.Stack;

/**
 * Created by david on 1/23/15.
 */
public class Messages {

    static Stack<String> messages = new Stack<String>();

    public static boolean Add(String message){
        messages.push(message);

        return ! message.isEmpty();

    }

    public static boolean Exists(String message){
        return messages.contains(message);

    }

    public static boolean IsEmpty() {
        return messages.isEmpty();
    }

    public static void SignalCompletion(){
        messages.clear();
    }

    public static boolean Delete(){
        messages.pop();
        return true;
    }


    public static List<String> Fetch(){
        return messages;
    }
    public static void Edit(){

    }
}

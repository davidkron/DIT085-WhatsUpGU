package main;

/**
 * Created by david on 2/19/15.
 */
public class ReturnMessage {
    public ReturnKind kind;
    public String ID;
    public String senderID;
    public String recieverID;
    public String Error = null;

    public ReturnMessage(ReturnKind kind, String ID) {
        this.kind = kind;
        this.ID = ID;
    }
}

package main.server.response;

/**
 * Created by david on 2/19/15.
 */
public class Response {
    public ResponseKind kind;
    public String ID;
    public String senderID;
    public String recieverID;
    public int messageID;
    public String Error = null;

    public Response(ResponseKind kind, String ID) {
        this.kind = kind;
        this.ID = ID;
    }
}

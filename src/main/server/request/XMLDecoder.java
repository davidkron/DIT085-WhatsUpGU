package main.server.request;

/**
 * Created by david on 2/19/15.
 */
public class XMLDecoder {
    public static RequestMessage decode(String xml) {
        return new RequestMessage(RequestKind.CONNECT);
    }
}

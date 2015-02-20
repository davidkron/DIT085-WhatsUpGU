package main.server.request;

public class XMLDecoder {
    public static RequestMessage decode(String xml) {
        return new RequestMessage(RequestKind.CONNECT);
    }
}

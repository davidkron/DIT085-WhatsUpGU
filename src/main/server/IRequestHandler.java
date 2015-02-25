package main.server;

import main.server.request.RequestObject;

public interface IRequestHandler {
    RequestObject handlerequest(RequestObject request);
}

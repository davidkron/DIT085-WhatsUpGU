package main.server;

import main.server.request.RequestObject;

public interface IServerState {
    RequestObject handlerequest(RequestObject request);
}

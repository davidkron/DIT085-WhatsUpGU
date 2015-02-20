package main;

import main.server.request.RequestMessage;
import main.server.response.Response;

public interface IServerState {
    Response handlerequest(RequestMessage request);
}

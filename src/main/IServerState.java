package main;

import main.server.request.RequestMessage;
import main.server.response.Response;

/**
 * Created by david on 2/18/15.
 */
public interface IServerState {
    Response handlerequest(RequestMessage request);
}

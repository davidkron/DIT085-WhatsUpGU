package main;

import main.server.request.RequestMessage;

/**
 * Created by david on 2/18/15.
 */
public interface IServerState {
    ReturnMessage handlerequest(RequestMessage request);
}

package main;

/**
 * Created by david on 2/18/15.
 */
public interface IServerState {
    ReturnMessage handlerequest(String request);
}

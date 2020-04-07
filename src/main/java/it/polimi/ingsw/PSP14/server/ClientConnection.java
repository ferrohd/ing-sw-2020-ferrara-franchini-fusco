package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.core.model.actions.Action;

import java.util.concurrent.TimeoutException;

/**
 * Generic connection to a client.
 * Exposes the functions for bidirectional communication with a client.
 */
public interface ClientConnection {
    int requestGameOptions();
    void sendFatalError();

    /**
     * A request to the client to provide the name that the player has chosen.
     * @return the player username
     */
    String getPlayerUsername();

    /**
     * Send an Action that will be serialized to the client.
     * @param action the action you want to send
     */
    void sendAction(Action action);

    /**
     * Receive an Action that will be deserialized from the client.
     * @param action the action
     */
    Action receiveAction();
}

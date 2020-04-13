package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.actions.Action;
import it.polimi.ingsw.PSP14.server.model.Message;

import java.io.IOException;

/**
 * Generic connection to a client.
 * Exposes the functions for bidirectional communication with a client.
 */
public interface ClientConnection {
    int requestGameOptions();
    void sendFatalError();

    /**
     * Serialize and send a message to the client.
     */
    void sendMessage(Message message) throws IOException;

    /**
     * Receive a message from the client.
     */
    Message receiveMessage() throws IOException;

    /**
     * A request to the client to provide the name that the player has chosen.
     * @return the player username
     */
    String getPlayerUsername();

    /**
     * Send an Action that will be serialized to the client.
     * @param action the action you want to send
     */
    void sendAction(Action action) throws IOException;

    /**
     * Receive an Action that will be deserialized from the client.
     * @param action the action
     */
    Action receiveAction() throws IOException;
}

package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.actions.Action;

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

    int receiveChoice() throws IOException;
}

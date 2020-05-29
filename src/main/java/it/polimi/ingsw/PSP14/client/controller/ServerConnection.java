package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;

import java.io.IOException;

/**
 * ServerConnection implemented using TCP sockets.
 */
public interface ServerConnection {
    /**
     * Used when an error occurs and the match has to be terminated.
     */
    void sendFatalError();

    /**
     * Allows to send a message to the server.
     * @param message the message to send
     * @throws IOException if the sending of the message fails
     */
    void sendMessage(Message message) throws IOException;

    /**
     * Allows to receive a message from the server.
     * @return the received message
     * @throws IOException if the message reception fails
     */
    Message receiveMessage() throws IOException;

    void close() throws IOException;
}

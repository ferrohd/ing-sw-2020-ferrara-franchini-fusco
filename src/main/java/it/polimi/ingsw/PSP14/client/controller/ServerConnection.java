package it.polimi.ingsw.PSP14.client.controller;

import it.polimi.ingsw.PSP14.core.messages.Message;

import java.io.IOException;

/**
 * ServerConnection implemented using TCP sockets.
 */
public interface ServerConnection {

    /**
     * Allows to send a message to the server.
     * The call is synchronous.
     * @param message the message to send
     * @throws IOException if sending the message fails
     */
    void sendMessage(Message message) throws IOException;

    /**
     * Allows to receive a message from the server.
     * The call is synchronous.
     * @return the received message
     * @throws IOException if the message reception fails
     */
    Message receiveMessage() throws IOException;

    void close() throws IOException;
}

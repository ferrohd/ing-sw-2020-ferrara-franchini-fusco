package it.polimi.ingsw.PSP14.client;

import java.io.IOException;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.actions.*;

/**
 * ServerConnection implemented using TCP sockets.
 */
public interface ServerConnection {
    /**
     * Used when an error occurs and the match has to be terminated.
     */
    public void sendFatalError();

    /**
     * Allows to send a message to the server.
     * @param message the message to send
     * @throws IOException if the sending of the message fails
     */
    public void sendMessage(Message message) throws IOException;

    /**
     * Allows to receive a message from the server.
     * @return the received message
     * @throws IOException if the message reception fails
     */
    public Message receiveMessage() throws IOException;
}

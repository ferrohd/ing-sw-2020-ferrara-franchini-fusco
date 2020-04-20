package it.polimi.ingsw.PSP14.client;

import java.io.IOException;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.server.actions.*;

/**
 * ServerConnection implemented using TCP sockets.
 */
public interface ServerConnection {
    public void sendFatalError();

    public void sendMessage(Message message) throws IOException;

    public Message receiveMessage() throws IOException;
}

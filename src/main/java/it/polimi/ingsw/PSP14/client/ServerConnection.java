package it.polimi.ingsw.PSP14.client;

import java.io.IOException;

import it.polimi.ingsw.PSP14.core.Message;
import it.polimi.ingsw.PSP14.core.actions.*;

/**
 * ServerConnection implemented using TCP sockets.
 */
public interface ServerConnection {
    public void sendFatalError();

    public void sendMessage(Message message) throws IOException;

    public Message receiveMessage() throws IOException;

    public void sendAction(Action action) throws IOException;

    public Action receiveAction() throws IOException;
}

package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * A message send from the server that contains executable instructions.
 * This corresponds more-or-less to a client-side implementation of a
 * server action, except that the client is "dumb" and has to simply
 * display what the server says.
 */
public interface ClientExecutableMessage extends Message {
    /**
     * Execute the instruction contained in this method on the client.
     * @param ui the client UI
     * @param serverConnection a way to send/receive messages to/from the server
     * @return
     */
    public boolean execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException;
}

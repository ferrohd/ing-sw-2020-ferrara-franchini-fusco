package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * This message purpose is to retrieve a username (string) from a
 * client and send it back to the server.
 */
public class UsernameMessage implements ClientExecutableMessage {
    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException {
        String name = ui.askUsername();
        serverConnection.sendMessage(new StringMessage(name));
    }
}

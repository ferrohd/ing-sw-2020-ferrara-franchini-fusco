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
    public boolean execute(UI ui, ServerConnection serverConnection) {
        String name = ui.askUsername();
        try {
            serverConnection.sendMessage(new StringMessage(name));
        } catch(IOException e) {
            return false;
        }

        return true;
    }
}

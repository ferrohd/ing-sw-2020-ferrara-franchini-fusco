package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class UsernameMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        String name = ui.askUsername();
        Message stringMessage = new StringMessage(name);
        try {
            serverConnection.sendMessage(stringMessage);
        } catch(IOException e) {
            return false;
        }

        return true;
    }
}

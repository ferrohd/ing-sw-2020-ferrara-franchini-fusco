package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that tells the client to register a new player.
 */
public class PlayerRegisterMessage implements UIUpdateMessage {
    private String username;

    public PlayerRegisterMessage(String username) {
        this.username = username;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException {
        ui.registerPlayer(username);
        ui.update();
    }
}

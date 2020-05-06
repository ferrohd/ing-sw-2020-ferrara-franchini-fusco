package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

public class PlayerRegisterMessage implements UIUpdateMessage {
    private String username;

    public PlayerRegisterMessage(String username) {
        this.username = username;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        ui.registerPlayer(username);
        ui.update();

        return true;
    }
}

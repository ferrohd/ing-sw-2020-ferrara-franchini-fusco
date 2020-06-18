package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that tells the client that a player has chosen a certain god.
 */
public class GodUpdateMessage implements UIUpdateMessage {
    private String player;
    private String god;

    public GodUpdateMessage(String player, String god) {
        this.player = player;
        this.god = god;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.showNotification(player + " chose " + god + "!");
        ui.updateGod(player, god);
    }
}

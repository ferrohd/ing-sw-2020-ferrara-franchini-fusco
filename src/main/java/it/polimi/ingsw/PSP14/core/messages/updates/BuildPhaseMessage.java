package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that tells the client that the build phase of a certain player has started.
 */
public class BuildPhaseMessage extends PhaseMessage {
    public BuildPhaseMessage(String player) {
        super(player);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startBuild(getPlayer());
    }
}

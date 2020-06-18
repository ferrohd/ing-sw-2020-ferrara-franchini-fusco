package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that tells the client that a player move phase has started.
 */
public class MovePhaseMessage extends PhaseMessage {
    public MovePhaseMessage(String player) {
        super(player);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startMove(getPlayer());
    }
}

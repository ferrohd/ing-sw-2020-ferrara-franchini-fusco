package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that tells the client that a player is choosing where to place their workers.
 * Happens before the matchs starts.
 */
public class WorkerPlacementPhaseMessage extends PhaseMessage {
    public WorkerPlacementPhaseMessage(String player) {
        super(player);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startWorkerPlacement(getPlayer());
    }
}

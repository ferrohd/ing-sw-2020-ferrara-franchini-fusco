package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class WorkerPlacementPhaseMessage extends PhaseMessage {
    public WorkerPlacementPhaseMessage(String player) {
        super(player);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startWorkerPlacement(getPlayer());
        return false;
    }
}

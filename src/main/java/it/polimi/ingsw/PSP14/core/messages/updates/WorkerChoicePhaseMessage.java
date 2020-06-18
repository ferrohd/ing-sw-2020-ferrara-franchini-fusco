package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that tells the client that a player is choosing which worker to move.
 */
public class WorkerChoicePhaseMessage extends PhaseMessage {

    public WorkerChoicePhaseMessage(String player) {
        super(player);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startWorkerChoice(getPlayer());
    }
}

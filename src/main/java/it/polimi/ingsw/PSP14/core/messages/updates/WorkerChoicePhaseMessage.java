package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class WorkerChoicePhaseMessage extends PhaseMessage {

    public WorkerChoicePhaseMessage(String player) {
        super(player);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startWorkerChoice(getPlayer());
        return false;
    }
}

package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class MovePhaseMessage extends PhaseMessage {
    public MovePhaseMessage(String player) {
        super(player);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.startMove(getPlayer());
        return false;
    }
}

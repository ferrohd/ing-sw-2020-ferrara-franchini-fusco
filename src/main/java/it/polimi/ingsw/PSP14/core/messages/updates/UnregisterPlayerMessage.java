package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.messages.ClientExecutableMessage;

import java.io.IOException;

public class UnregisterPlayerMessage implements ClientExecutableMessage {
    private String toRemove;

    public UnregisterPlayerMessage(String toRemove) {
        this.toRemove = toRemove;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.unregisterPlayer(toRemove);
    }
}

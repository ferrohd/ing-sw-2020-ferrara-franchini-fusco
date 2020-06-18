package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class EndGameMessage implements ClientExecutableMessage {
    private String winner;

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException {
        ui.showNotification(winner + " won!");
        throw new IOException();
    }
}

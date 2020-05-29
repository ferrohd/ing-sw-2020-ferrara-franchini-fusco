package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class GameStartMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws InterruptedException, IOException {
        ui.gameStart();

        return true;
    }
}

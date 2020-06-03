package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class GameEndMessage implements ClientExecutableMessage {
    private String winner;

    public GameEndMessage(String winner) {
        this.winner = winner;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.showVictory(winner);
        System.exit(0);
        return false;
    }
}

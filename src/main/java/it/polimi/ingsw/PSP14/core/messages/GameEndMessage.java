package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Message that notifies the players that the game has ended, and announcing the winner.
 */
public class GameEndMessage implements ClientExecutableMessage {
    private final String winner;

    public GameEndMessage(String winner) {
        this.winner = winner;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.showVictory(winner);
        System.exit(0);
    }
}

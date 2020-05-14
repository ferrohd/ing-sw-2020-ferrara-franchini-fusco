package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * Send a client (the first player, in particular) a prompt to specify
 * the number of players that the lobby will accept, and then send the
 * answer back to the server.
 */
public class LobbySizeMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws InterruptedException {
        int lobbySize = ui.getLobbySize();
        try {
            serverConnection.sendMessage(new ChoiceMessage(lobbySize));
        } catch(IOException e) {
            return false;
        }
        return true;
    }
}

package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class WorkerInitialPositionMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        // Retrieve the index of the first player
        int[] coordinates = ui.chooseWorkerInitialPosition();
        // Send it back to the server
        try {
            serverConnection.sendMessage(new ChoiceMessage(coordinates[0]));
            serverConnection.sendMessage(new ChoiceMessage(coordinates[1]));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

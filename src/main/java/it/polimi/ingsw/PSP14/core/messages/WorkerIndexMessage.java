package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

/**
 * This class purpose is to retrieve a
 */
public class WorkerIndexMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        // Retrieve the index of the selected worker
        int index = ui.chooseWorker();
        // Send it back to the server
        try {
            serverConnection.sendMessage(new ChoiceMessage(index));
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}

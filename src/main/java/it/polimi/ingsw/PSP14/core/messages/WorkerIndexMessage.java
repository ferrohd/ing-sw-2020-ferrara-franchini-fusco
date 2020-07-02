package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;
import java.util.List;

/**
 * This class purpose is to retrieve the chosen worker index from a player and send
 * it back to the server.
 */
public class WorkerIndexMessage implements ClientExecutableMessage {
    private final List<Integer> choosable;

    public WorkerIndexMessage(List<Integer> choosable) {
        this.choosable = choosable;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        while (true) {
            try {
                int workerId = ui.chooseWorker(choosable);
                serverConnection.sendMessage(new ChoiceMessage(workerId));
                return;
            } catch (IndexOutOfBoundsException ignore) {
            }
        }
    }
}

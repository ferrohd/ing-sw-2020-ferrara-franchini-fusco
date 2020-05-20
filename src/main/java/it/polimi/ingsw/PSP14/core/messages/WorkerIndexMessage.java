package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;
import java.util.List;

/**
 * This class purpose is to retrieve a
 */
public class WorkerIndexMessage implements ClientExecutableMessage {
    private List<Integer> choosable;

    public WorkerIndexMessage(List<Integer> choosable) {
        this.choosable = choosable;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException {
        int index = ui.chooseWorker(choosable);
        serverConnection.sendMessage(new ChoiceMessage(index));
        return false;
    }
}

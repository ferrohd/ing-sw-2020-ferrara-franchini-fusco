package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

import java.io.IOException;

public class WorkerInitialPositionMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException {
        int[] coordinates = ui.chooseWorkerInitialPosition();
        serverConnection.sendMessage(new ChoiceMessage(coordinates[0]));
        serverConnection.sendMessage(new ChoiceMessage(coordinates[1]));
        return false;
    }
}

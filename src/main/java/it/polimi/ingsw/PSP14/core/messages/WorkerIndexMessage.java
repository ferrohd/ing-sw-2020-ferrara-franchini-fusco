package it.polimi.ingsw.PSP14.core.messages;


import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;

public class WorkerIndexMessage implements ClientExecutableMessage {
    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        ui.chooseWorker();


        return true;
    }
}

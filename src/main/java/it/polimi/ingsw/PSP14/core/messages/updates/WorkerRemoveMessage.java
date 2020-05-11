package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.model.UIPoint;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.Point;

public class WorkerRemoveMessage implements UIUpdateMessage {
    private Point pos;

    public WorkerRemoveMessage(Point pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        ui.unsetWorker(UIPoint.fromPoint(pos));
        ui.update();

        return true;
    }
}

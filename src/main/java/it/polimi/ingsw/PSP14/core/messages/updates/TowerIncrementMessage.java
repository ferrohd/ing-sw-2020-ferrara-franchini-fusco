package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

public class TowerIncrementMessage implements UIUpdateMessage {
    private Point pos;

    public TowerIncrementMessage(Point pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException {
        ui.incrementCell(pos);
        ui.update();
        return false;
    }
}

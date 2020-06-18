package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * Message that tells the client that a tower size has been increased.
 */
public class TowerIncrementMessage implements UIUpdateMessage {
    private Point pos;

    public TowerIncrementMessage(Point pos) {
        this.pos = pos;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.incrementCell(pos);
        ui.update();
    }
}

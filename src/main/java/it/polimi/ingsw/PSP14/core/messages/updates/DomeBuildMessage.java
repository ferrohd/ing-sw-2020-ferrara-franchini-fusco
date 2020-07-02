package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * Message that tells the client that a dome has been built.
 */
public class DomeBuildMessage implements UIUpdateMessage {
    private final Point pos;

    public DomeBuildMessage(Point pos) {
        this.pos = pos;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.setDome(pos);
        ui.update();
    }
}

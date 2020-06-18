package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * Message that tells the client that a new worker has been added to the match.
 */
public class WorkerAddMessage implements UIUpdateMessage {
    private Point pos;
    private String player;
    private int workerId;

    public WorkerAddMessage(Point pos, String player, int workerId) {
        this.pos = pos;
        this.player = player;
        this.workerId = workerId;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.setWorker(pos, workerId, player);
        ui.update();
    }
}

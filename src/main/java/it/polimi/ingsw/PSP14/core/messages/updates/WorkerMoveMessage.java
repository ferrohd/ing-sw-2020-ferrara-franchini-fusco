package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * Message that tells the client that a player is choosing where to move a worker.
 */
public class WorkerMoveMessage implements UIUpdateMessage {
    private final Point newPos;
    private final String player;
    private final int workerId;

    public WorkerMoveMessage(Point newPos, String player, int workerId) {
        this.newPos = newPos;
        this.player = player;
        this.workerId = workerId;
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        ui.moveWorker(newPos, workerId, player);
        ui.update();
    }
}

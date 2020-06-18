package it.polimi.ingsw.PSP14.core.messages.updates;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

public class WorkerMoveMessage implements UIUpdateMessage {
    private Point newPos;
    private String player;
    private int workerId;

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

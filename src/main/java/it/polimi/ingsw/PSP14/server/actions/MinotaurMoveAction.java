package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.core.messages.updates.UIUpdateMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerMoveMessage;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

import java.io.IOException;
import java.util.List;

public class MinotaurMoveAction extends MoveAction {
    private Point opponentNewPos;
    private int opponentWorkerIndex;
    private String opponent;

    public MinotaurMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public void execute(Match match) {
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(getTo()) &&
                        !p.getUsername().equals(getUser())) {
                    super.execute(match);
                    Point newPos = new Point(
                            2*getTo().getX() - getFrom().getX(),
                            2*getTo().getY() - getFrom().getY()
                    );
                    opponentNewPos = newPos;
                    opponentWorkerIndex = i;
                    opponent = p.getUsername();
                    p.getWorker(i).setPos(newPos);
                }
            }
        }
    }

    @Override
    public void updateClients(List<ClientConnection> clients) throws IOException {
        super.updateClients(clients);
        for (ClientConnection client : clients) {
            client.notifyWorkerMove(opponentNewPos, opponent, opponentWorkerIndex);
        }
    }
}

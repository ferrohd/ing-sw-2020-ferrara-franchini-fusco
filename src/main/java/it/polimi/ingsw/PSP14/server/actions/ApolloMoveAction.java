package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.core.messages.updates.UIUpdateMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerAddMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerMoveMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerRemoveMessage;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApolloMoveAction extends MoveAction {
    private Point opponentNewPos;
    private int opponentWorkerIndex;
    private String opponent;

    public ApolloMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public void execute(Match match) {
        // finds the worker at the "to" position
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(getTo())) {
                    // moves the worker at "from" to  "to"
                    super.execute(match);
                    opponentNewPos = getFrom();
                    opponentWorkerIndex = i;
                    opponent = p.getUsername();
                    p.getWorker(i).setPos(getFrom());
                }
            }
        }
    }

    @Override
    public void updateClients(List<ClientConnection> clients) throws IOException {
        super.updateClients(clients);
        UIUpdateMessage message = new WorkerMoveMessage(opponentNewPos, opponent, opponentWorkerIndex);
        for (ClientConnection client : clients) {
            client.sendMessage(message);
        }
    }
}

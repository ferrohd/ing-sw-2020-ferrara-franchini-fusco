package it.polimi.ingsw.PSP14.server.actions;

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
    public ApolloMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public boolean execute(Match match, List<ClientConnection> clients) {
        // finds the worker at the "to" position
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(getTo())) {
                    // moves the worker at "from" to  "to"
                    if(super.execute(match, clients)) {
                        // moves the worker at "to" to "from"
                        try {
                            p.getWorker(i).setPos(getFrom());
                            ClientConnection.sendAll(clients, new WorkerMoveMessage(p.getWorker(i).getPos(),  p.getUsername(), i));
                        } catch(IOException e) {
                            e.printStackTrace();
                            System.exit(-1);
                        }
                        return true;
                    } else { // if normal execution failed
                        return false;
                    }
                }
            }
        }
        return false;
    }
}

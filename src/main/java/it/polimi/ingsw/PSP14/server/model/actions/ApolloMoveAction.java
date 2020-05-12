package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.List;

public class ApolloMoveAction extends MoveAction {
    private Point opponentNewPos;
    private int opponentWorkerIndex;
    private String opponent;

    public ApolloMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public void execute(Match match) throws IOException {
        // finds the worker at the "to" position
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorkerPos(i).equals(getTo())) {
                    // moves the worker at "from" to  "to"
                    super.execute(match);
                    opponentNewPos = getFrom();
                    opponentWorkerIndex = i;
                    opponent = p.getUsername();
                    p.setWorker(i, getFrom());
                }
            }
        }
    }
}

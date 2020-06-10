package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

public class ApolloMoveAction extends MoveAction {
    private Point opponentNewPos;
    private int opponentWorkerIndex;
    private String opponent;

    public ApolloMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public void execute(MatchModel model) throws IOException {
        // finds the worker at the "to" position
        for(Player p: model.getPlayerMap()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorkerPos(i).equals(getTo())) {
                    // moves the worker at "from" to  "to"
                    super.execute(model);
                    opponentNewPos = getFrom();
                    opponentWorkerIndex = i;
                    opponent = p.getUsername();
                    p.setWorker(i, getFrom());
                    return;
                }
            }
        }
    }
}

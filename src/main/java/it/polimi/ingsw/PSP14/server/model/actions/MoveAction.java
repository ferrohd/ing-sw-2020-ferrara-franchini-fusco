package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * The implementation of a the move action.
 */
public class MoveAction extends Action implements Proposable {
    private final Point from;
    private final Point to;

    public MoveAction(String user, Point from, Point to) {
        super(user);
        this.from = from;
        this.to = to;
    }

    public MoveProposal getProposal() {
        return new MoveProposal(to);
    }

    @Override
    public void execute(MatchModel model) throws IOException {
        for (Player p : model.getPlayerMap()) {
            for (int i = 0; i < 2; ++i) {
                if (p.getWorkerPos(i).equals(from)) {
                    p.setWorker(i, to);
                    if (model.getBoard().getTowerSize(to) == 3 && model.getBoard().getTowerSize(from) < 3) {
                        model.end(getUser());
                    }
                }
            }
        }
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }
}

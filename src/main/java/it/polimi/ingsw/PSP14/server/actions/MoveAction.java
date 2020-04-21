package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

public class MoveAction extends Action implements Proposable {
    private Point from;
    private Point to;

    public MoveAction(String user, Point from, Point to) {
        super(user);
        this.from = from;
        this.to = to;
    }

    public MoveProposal getProposal() {
        return new MoveProposal(to);
    }

    public boolean execute(Match match) {
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(from)) {
                    p.getWorker(i).setPos(to);
                    return true;
                }
            }
        }
        return false;
    }

    protected Point getFrom() {
        return from;
    }

    protected Point getTo() {
        return to;
    }

}

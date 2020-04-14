package it.polimi.ingsw.PSP14.core.actions;

import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

public class MoveAction extends Action {
    private Point from;
    private Point to;

    public MoveAction(Point from, Point to) {
        this.from = from;
        this.to = to;
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

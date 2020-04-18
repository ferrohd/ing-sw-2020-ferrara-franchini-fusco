package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

public class MinotaurMoveAction extends MoveAction {
    public MinotaurMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public boolean execute(Match match) {
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(getTo()) &&
                        !p.getUsername().equals(getUser())) {
                    if(super.execute(match)) {
                        Point newPos = new Point(
                                2*getTo().getX() - getFrom().getX(),
                                2*getTo().getY() - getFrom().getY()
                        );
                        p.getWorker(i).setPos(newPos);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}

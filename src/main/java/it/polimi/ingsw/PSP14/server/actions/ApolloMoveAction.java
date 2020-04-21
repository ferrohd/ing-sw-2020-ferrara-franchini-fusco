package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

public class ApolloMoveAction extends MoveAction {
    public ApolloMoveAction(String user, Point from, Point to) {
        super(user, from, to);
    }

    @Override
    public boolean execute(Match match) {
        // finds the worker at the "to" position
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(getTo())) {
                    // moves the worker at "from" to  "to"
                    if(super.execute(match)) {
                        // moves the worker at "to" to "from"
                        p.getWorker(i).setPos(getFrom());
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

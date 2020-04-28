package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Point;

public class HephaestusBuildAction extends BuildAction {
    public HephaestusBuildAction(String user, Point point) {
        super(user, point, false, 2);
    }
}

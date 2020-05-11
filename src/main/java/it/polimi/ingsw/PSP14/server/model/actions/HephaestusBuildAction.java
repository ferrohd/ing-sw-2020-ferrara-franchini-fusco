package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.board.Point;

public class HephaestusBuildAction extends BuildAction {
    public HephaestusBuildAction(String user, Point point) {
        super(user, point, false, 2);
    }
}

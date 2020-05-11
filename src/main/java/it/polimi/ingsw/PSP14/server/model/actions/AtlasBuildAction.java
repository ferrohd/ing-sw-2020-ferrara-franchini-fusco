package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.board.Point;

public class AtlasBuildAction extends BuildAction {
    public AtlasBuildAction(String user, Point point) {
        super(user, point, true, 0);
    }
}

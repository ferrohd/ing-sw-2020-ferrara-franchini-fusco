package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.server.model.Point;

public class AtlasBuildAction extends BuildAction {
    public AtlasBuildAction(String user, Point point) {
        super(user, point, true);
    }
}

package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.board.Point;

/**
 * The implementation of a custom move of Atlas.
 */
public class AtlasBuildAction extends BuildAction {
    public AtlasBuildAction(String user, Point point) {
        super(user, point, true, 0);
    }
}

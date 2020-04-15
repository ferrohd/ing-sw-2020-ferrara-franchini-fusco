package it.polimi.ingsw.PSP14.core.proposals;

import it.polimi.ingsw.PSP14.server.model.Point;

public class BuildProposal implements Proposal {
    private Point point;
    private boolean dome;

    public BuildProposal(Point point, boolean dome) {
        this.point = point;
        this.dome = dome;
    }

    public Point getPoint() {
        return point;
    }

    public boolean isDome() {
        return dome;
    }
}

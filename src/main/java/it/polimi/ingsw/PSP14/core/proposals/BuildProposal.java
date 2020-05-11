package it.polimi.ingsw.PSP14.core.proposals;

import it.polimi.ingsw.PSP14.server.model.board.Point;

/**
 * Proposal containing data about a Build action,
 * containing a Point representing the position of
 * a tower and a boolean which specifies whether
 * the tower has to be marked as completed or not.
 */
public class BuildProposal implements Proposal {
    private Point point;
    private boolean dome;
    private int amount;

    public BuildProposal(Point point, boolean dome, int amount) {
        this.point = point;
        this.dome = dome;
        this.amount = amount;
    }

    public Point getPoint() {
        return point;
    }

    public boolean hasDome() {
        return dome;
    }

    public int getAmount() {
        return amount;
    }
}

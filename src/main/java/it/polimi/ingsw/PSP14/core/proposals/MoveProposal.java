package it.polimi.ingsw.PSP14.core.proposals;

import it.polimi.ingsw.PSP14.server.model.board.Point;

/**
 * Proposal representing a possible move.
 */
public class MoveProposal implements Proposal {
    private Point point;

    public MoveProposal(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}

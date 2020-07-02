package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;

import java.io.IOException;

/**
 * The implementation of the build action.
 */
public class BuildAction extends Action implements Proposable {
    private final Point point;
    private final boolean dome;
    private final int amount;

    public BuildAction(String user, Point point, boolean dome, int amount) {
        super(user);
        this.point = point;
        this.dome = dome;
        this.amount = amount;
    }

    public BuildProposal getProposal() {
        return new BuildProposal(point, dome, amount);
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public void execute(MatchModel model) throws IOException {
        if (dome) {
            model.getBoard().setAsCompleted(point);
        } else {
            try {
                for (int i = 0; i < amount; ++i)
                    model.getBoard().incrementTowerSize(point);
            } catch (TowerSizeException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}

package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.model.Cell;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.TowerSizeException;

public class BuildAction extends Action implements Proposable {
    private Point point;
    private boolean dome;

    public BuildAction(String user, Point point, boolean dome) {
        super(user);
        this.point = point;
        this.dome = dome;
    }

    public BuildProposal getProposal() {
        return new BuildProposal(point, dome);
    }

    public boolean execute(Match match) {
        Cell cell = match.getBoard().getCell(point);
        if(dome) {
            cell.setAsCompleted();
        } else {
            try {
                cell.incrementTowerSize();
            } catch(TowerSizeException e) {
                return false;
            }
        }

        return true;
    }
}

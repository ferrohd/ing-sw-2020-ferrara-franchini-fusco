package it.polimi.ingsw.PSP14.core.actions;

import it.polimi.ingsw.PSP14.server.model.Cell;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.TowerSizeException;

public class BuildAction extends Action {
    private Point point;
    private boolean dome;

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

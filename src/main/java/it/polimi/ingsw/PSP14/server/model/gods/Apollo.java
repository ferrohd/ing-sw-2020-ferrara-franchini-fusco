package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.ApolloMoveAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Apollo<br/>
 * God Of Music<br/>
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space by forcing their Worker to
 * the space yours just vacated.
 */
public class Apollo extends God {
    public Apollo(String owner) {
        super(owner);
    }

    @Override
    public void addMoves(List<MoveAction> moves, Player player, Worker worker, Match match) {
        if(!player.getUsername().equals(getOwner())) {
            return;
        }
        List<Point> workerPos = match.getWorkerPositions();
        workerPos.remove(player.getWorker(0).getPos());
        workerPos.remove(player.getWorker(1).getPos());

        Point currPos = worker.getPos();
        int currentLevel = match.getBoard().getCell(currPos).getTowerSize();

        for(Direction dir: Direction.values()) {
            Point newPos = currPos.move(dir);
            if (Board.isValidPos(newPos)) {
                int newLevel = match.getBoard().getCell(newPos).getTowerSize();
                if (!match.isCellFree(newPos) && newLevel <= currentLevel + 1 &&
                        !match.getBoard().getCell(newPos).getIsCompleted()) {
                    moves.add(new ApolloMoveAction(player.getUsername(), currPos, newPos));
                }
            }
        }
    }
}

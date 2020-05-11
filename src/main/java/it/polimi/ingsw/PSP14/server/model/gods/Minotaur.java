package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.MinotaurMoveAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.*;
import it.polimi.ingsw.PSP14.server.model.board.*;

import java.util.List;

/**
 * Minotaur</br>
 * Bull-headed Monster<br/>
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space, if their Worker can be
 * forced one space straight backwards to an
 * unoccupied space at any level.
 * 8
 */
public class Minotaur extends God {
    public Minotaur(String owner) {
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
            Point newPos = currPos.move(dir),
                    minotaurPos = newPos.move(dir);
            if(Board.isValidPos(minotaurPos)) {
                int newLevel = match.getBoard().getCell(newPos).getTowerSize();
                if (!match.isCellFree(newPos) && newLevel <= currentLevel + 1 &&
                        !match.getBoard().getCell(newPos).getIsCompleted())
                    moves.add(new MinotaurMoveAction(player.getUsername(), currPos, newPos));
            }
        }
    }
}

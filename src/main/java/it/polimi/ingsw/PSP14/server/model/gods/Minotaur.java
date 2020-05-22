package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.MinotaurMoveAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.*;
import it.polimi.ingsw.PSP14.server.model.board.*;

import java.io.IOException;
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
    public void addMoves(List<MoveAction> moves, String player, int workerIndex, Match match) throws IOException {
        if(!player.equals(getOwner())) {
            return;
        }
        List<Point> workerPos = match.getWorkerPositions();
        Player playing = match.getPlayerByUsername(player);
        workerPos.remove(playing.getWorkerPos(0));
        workerPos.remove(playing.getWorkerPos(1));

        Point currPos = playing.getWorkerPos(workerIndex);
        int currentLevel = match.getBoard().getTowerSize(currPos);

        for(Direction dir: Direction.values()) {
            Point newPos = currPos.move(dir),
                    minotaurPos = newPos.move(dir);
            if(Board.isValidPos(minotaurPos)) {
                int newLevel = match.getBoard().getTowerSize(newPos);
                if (!match.isCellFree(newPos) && newLevel <= currentLevel + 1 &&
                        !match.getBoard().getIsCompleted(newPos))
                    moves.add(new MinotaurMoveAction(player, currPos, newPos));
            }
        }
    }
}

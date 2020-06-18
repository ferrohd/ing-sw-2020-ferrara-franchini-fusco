package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.MinotaurMoveAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Direction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.List;

/**
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space, if their Worker can be
 * forced one space straight backwards to an
 * unoccupied space at any level.
 */
public class Minotaur extends God {
    public Minotaur(String owner) {
        super(owner);
    }

    @Override
    public void addMoves(List<MoveAction> moves, String player, int workerIndex, MatchModel model) throws IOException {
        if(!player.equals(getOwner())) {
            return;
        }
        Player playing = model.getPlayerByUsername(player);
        Point worker0 = playing.getWorkerPos(0),
                worker1 = playing.getWorkerPos(1);

        Point currPos = playing.getWorkerPos(workerIndex);
        int currentLevel = model.getBoard().getTowerSize(currPos);

        for(Direction dir: Direction.values()) {
            Point newPos = currPos.move(dir),
                    minotaurPos = newPos.move(dir);
            if(Board.isValidPos(minotaurPos) && !newPos.equals(worker0) && !newPos.equals(worker1)) {
                int newLevel = model.getBoard().getTowerSize(newPos);
                if (!model.isCellFree(newPos) && model.isCellFree(minotaurPos) && newLevel <= currentLevel + 1 &&
                        !model.getBoard().getIsCompleted(newPos) && !model.getBoard().getIsCompleted(minotaurPos))
                    moves.add(new MinotaurMoveAction(player, currPos, newPos));
            }
        }
    }
}

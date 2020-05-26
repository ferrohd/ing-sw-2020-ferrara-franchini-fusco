package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.ApolloMoveAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.*;
import it.polimi.ingsw.PSP14.server.model.board.*;

import java.io.IOException;
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
    public void addMoves(List<MoveAction> moves, String player, int workerIndex, Match match) throws IOException {
        if(!player.equals(getOwner())) {
            return;
        }
        Player playing = match.getPlayerByUsername(player);
        Point worker0 = playing.getWorkerPos(0);
        Point worker1 = playing.getWorkerPos(1);

        Point currPos = playing.getWorkerPos(workerIndex);
        int currentLevel = match.getBoard().getTowerSize(currPos);

        for(Direction dir: Direction.values()) {
            Point newPos = currPos.move(dir);
            if (Board.isValidPos(newPos) && !newPos.equals(worker0) && !newPos.equals(worker1)) {
                int newLevel = match.getBoard().getTowerSize(newPos);
                if (!match.isCellFree(newPos) && newLevel <= currentLevel + 1 &&
                        !match.getBoard().getIsCompleted(newPos)) {
                    moves.add(new ApolloMoveAction(player, currPos, newPos));
                }
            }
        }
    }
}

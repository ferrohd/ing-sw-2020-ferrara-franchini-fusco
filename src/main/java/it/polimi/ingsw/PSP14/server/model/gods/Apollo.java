package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.*;

import java.util.ArrayList;
import java.util.List;

public class Apollo extends God {

    private static Apollo ref = new Apollo();

    private Apollo() { }

    public static Apollo getInstance() {
        if (ref == null) {
            ref = new Apollo();
        }
        return ref;
    }

    @Override
    public void addMoves(List<Point> moves, Player player, Worker worker, Match match) {
        List<Point> workerPos = match.getWorkerPositions();
        workerPos.remove(player.getWorker(0).getPos());
        workerPos.remove(player.getWorker(1).getPos());

        Point currPos = worker.getPos();
        int currentLevel = match.getBoard().getCell(currPos).getTowerSize();

        for(Direction dir: Direction.values()) {
            Point newPos = currPos.move(dir);
                int newLevel = match.getBoard().getCell(newPos).getTowerSize();
                if (workerPos.contains(newPos) && newLevel <= currentLevel + 1 &&
                        !match.getBoard().getCell(newPos).getIsCompleted() &&
                        Board.isValidPos(newPos))
                    moves.add(newPos);
        }
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;

/**
 * Win condition:
 * You also win when there are at least five Complete Towers on the board.
 */
public class Chronus extends God {
    public Chronus(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        int nComplete = 0;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Point p = new Point(i, j);
                Board b = model.getBoard();
                if(b.getTowerSize(p) == 3 && b.getIsCompleted(p)) ++nComplete;
            }
        }

        if(nComplete >= 5)
            model.end(getOwner());
    }
}

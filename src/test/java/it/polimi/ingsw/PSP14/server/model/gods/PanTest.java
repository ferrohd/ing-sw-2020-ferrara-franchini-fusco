package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.EndGameException;
import it.polimi.ingsw.PSP14.server.model.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PanTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatchModel match = new FakeMatchModel() {
            @Override
            public Action getLastAction() {
                return new MoveAction("panOwner", new Point(0, 0), new Point(1, 1));
            }

            @Override
            public void end(String winningPlayer) throws EndGameException, IOException {
                assertTrue(flag);
            }
        };
        MatchController controller = new FakeMatchController();
        God pan = new Pan("panOwner");
        Board board = match.getBoard();
        board.incrementTowerSize(new Point(0, 0));

        pan.afterMove("panOwner", 0, controller, match);
        board.incrementTowerSize(new Point(0, 0));
        match.flag = true;
        pan.afterMove("panOwner", 0, controller, match);
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AthenaTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        MatchModel model = new FakeMatchModel() {
            @Override
            public Action getLastAction() {
                return new MoveAction("athenaOwner", new Point(0, 0), new Point(1, 1));
            }
        };
        MatchController controller = new FakeMatchController();
        God athena = new Athena("athenaOwner");
        Board board = model.getBoard();
        board.incrementTowerSize(new Point(1, 1));
        board.incrementTowerSize(new Point(3, 3));

        athena.beforeTurn("athenaOwner", controller, model);
        athena.afterMove("athenaOwner", 0, controller, model);

        List<MoveAction> moves = new ArrayList<>();
        moves.add(new MoveAction("opponent", new Point(2, 2), new Point(3, 3)));

        athena.removeMoves(moves, "opponent", 0, model);

        assertEquals(moves.size(), 0);
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrometheusTest {
    /**
     * checks if prometheus correctly builds and removes upward moves
     */
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatchModel match = new FakeMatchModel() {
            @Override
            public boolean build(String player, int workerIndex) throws IOException {
                num++;

                return true;
            }
        };
        MatchController controller = new FakeMatchController() {
            @Override
            public boolean askQuestion(String player, String s) throws IOException {
                return true;
            }
        };
        God god = new Prometheus("owner");
        Board board = match.getBoard();
        board.incrementTowerSize(new Point(1, 1));

        god.beforeMove("owner", 0, controller, match);
        assertEquals(1, match.num);
        List<MoveAction> moves = new ArrayList<>();
        moves.add(new MoveAction("owner", new Point(0, 0), new Point(1, 1)));
        moves.add(new MoveAction("owner", new Point(0, 0), new Point(0, 1)));
        god.removeMoves(moves, "owner", 0, match);
        assertEquals(1, moves.size());
        assertTrue(moves.get(0).getTo().equals(new Point(0, 1)));
    }
}

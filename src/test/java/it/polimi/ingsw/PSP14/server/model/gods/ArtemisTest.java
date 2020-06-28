package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtemisTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        MatchModel model = new FakeMatchModel() {
            @Override
            public Action getLastAction() {
                return new MoveAction("artemisOwner", new Point(0, 0), new Point(1, 1));
            }

            @Override
            public List<MoveAction> getMovements(String player, int workerIndex) {
                List<MoveAction> moves = new ArrayList<>();
                moves.add(new MoveAction("artemisOwner", new Point(1, 1), new Point(0, 0)));
                moves.add(new MoveAction("artemisOwner", new Point(1, 1), new Point(1, 0)));

                return moves;
            }

            @Override
            public void executeAction(Action action) throws IOException {
                assertTrue(((MoveAction)action).getTo().equals(new Point(1, 0)));
            }
        };
        MatchController controller = new FakeMatchController() {
            @Override
            public MoveAction askMove(String player, List<MoveAction> moves) {
                for(MoveAction move : moves) {
                    assertFalse(move.getTo().equals(new Point(0, 0)));
                }

                return moves.get(0);
            }
        };
        God artemis = new Artemis("artemisOwner");

        artemis.afterMove("artemisOwner", 0, controller, model);
    }
}

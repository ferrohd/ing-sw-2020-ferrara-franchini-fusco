package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TritonTest {
    /**
     * checks if triton moves as long as it is allowed to
     */
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        God god = new Triton("owner");
        MatchController controller = new FakeMatchController() {
            @Override
            public boolean askQuestion(String player, String s) throws IOException {
                return true;
            }
        };
        FakeMatchModel match = new FakeMatchModel() {
            @Override
            public boolean move(String player, int workerIndex) throws IOException {
                num++;
                god.afterMove(player, workerIndex, controller, this);

                return true;
            }

            @Override
            public Action getLastAction() {
                if (num < 5)
                    return new MoveAction("owner", new Point(0, 0), new Point(0, 1));
                else
                    return new MoveAction("owner", new Point(0, 0), new Point(1, 1));
            }
        };

        god.afterMove("owner", 0, controller, match);
        assertEquals(5, match.num);
    }
}

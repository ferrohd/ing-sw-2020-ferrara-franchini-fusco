package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.Match;
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
        Match match = new FakeMatch() {
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
        ClientConnection client = new FakeClientConnection() {
            @Override
            public int askMove(List<MoveProposal> moves) {
                for(MoveProposal move : moves) {
                    assertFalse(move.getPoint().equals(new Point(0, 0)));
                }

                return 0;
            }
        };
        God artemis = new Artemis("artemisOwner");

        artemis.afterMove("artemisOwner", 0, client, match);
    }
}

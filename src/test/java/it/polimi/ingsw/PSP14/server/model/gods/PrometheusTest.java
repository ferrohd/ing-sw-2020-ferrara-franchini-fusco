package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrometheusTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatch match = new FakeMatch() {
            @Override
            public void build(String player, ClientConnection client, int workerIndex) throws IOException {
                num++;
            }
        };
        ClientConnection client = new FakeClientConnection() {
            @Override
            public boolean askQuestion(String s) throws IOException {
                return true;
            }
        };
        God god = new Prometheus("owner");
        Board board = match.getBoard();
        board.incrementTowerSize(new Point(1, 1));

        god.beforeMove("owner", 0, client, match);
        assertEquals(1, match.num);
        List<MoveAction> moves = new ArrayList<>();
        moves.add(new MoveAction("owner", new Point(0, 0), new Point(1, 1)));
        moves.add(new MoveAction("owner", new Point(0, 0), new Point(0, 1)));
        god.removeMoves(moves, "owner", 0, match);
        assertEquals(1, moves.size());
        assertTrue(moves.get(0).getTo().equals(new Point(0, 1)));
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TritonTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        God god = new Triton("owner");
        FakeMatch match = new FakeMatch() {
            @Override
            public void move(String player, ClientConnection client, int workerIndex) throws IOException {
                num++;
                god.afterMove(player, workerIndex, client, this);
            }

            @Override
            public Action getLastAction() {
                if (num < 5)
                    return new MoveAction("owner", new Point(0, 0), new Point(0, 1));
                else
                    return new MoveAction("owner", new Point(0, 0), new Point(1, 1));
            }
        };
        ClientConnection client = new FakeClientConnection() {
            @Override
            public boolean askQuestion(String s) throws IOException {
                return true;
            }
        };

        god.afterMove("owner", 0, client, match);
        assertEquals(5, match.num);
    }
}

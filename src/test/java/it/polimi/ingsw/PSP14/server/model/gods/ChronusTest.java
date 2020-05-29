package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.EndGameException;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChronusTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatch match = new FakeMatch() {
            @Override
            public void end(String winningPlayer) throws EndGameException, IOException {
                assertTrue(flag);
            }
        };
        ClientConnection client = new FakeClientConnection();
        God chronus = new Chronus("chronusOwner");
        Board board = match.getBoard();
        for(int i = 0; i < 4; ++i) {
            Point p = new Point(i, 0);
            for(int j = 0; j < 3; ++j) {
                board.incrementTowerSize(p);
            }
            board.setAsCompleted(p);
        }
        board.setAsCompleted(new Point(4, 0));

        chronus.afterBuild("chronusOwner", 0, client, match);
        match.flag = true;
        for(int i = 0; i < 3; ++i) board.incrementTowerSize(new Point(4, 0));
        chronus.afterBuild("chronusOwner", 0, client, match);
    }
}

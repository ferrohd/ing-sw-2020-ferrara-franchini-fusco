package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.server.model.EndGameException;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveActionTest {
    @Test
    public void moveActionTest() throws IOException {
        FakeMatchModel model = new FakeMatchModel() {
            @Override
            public ArrayList<Player> getPlayerMap() {
                ArrayList<Player> players = new ArrayList<>();
                try {
                    Player p = new Player("resu");
                    p.setWorker(0, new Point(2, 2));
                    p.setWorker(1, new Point(5, 1));
                    players.add(p);
                    p = new Player("user");
                    p.setWorker(0, new Point(3, 4));
                    p.setWorker(1, new Point(0, 0));
                    players.add(p);
                } catch (IOException ignore) {}

                return players;
            }

            @Override
            public Board getBoard() {
                try {
                    Board b = new Board();
                    for (int i = 0; i < 3; ++i)
                        b.incrementTowerSize(new Point(1, 1));
                    return b;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void end(String winningPlayer) throws EndGameException, IOException {
                s = winningPlayer;
            }
        };
        MoveAction action = new MoveAction("user", new Point(0,0), new Point(1,1));

        model.executeAction(action);
        assertEquals("user", model.s);

    }
}

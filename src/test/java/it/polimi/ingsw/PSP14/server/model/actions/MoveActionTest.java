package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.EndGameException;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveActionTest {
    private static FakeMatchModel model;

    @BeforeEach
    public void reset() throws IOException {
        model = new FakeMatchModel() {
            @Override
            public void resetPlayers() {
                this.players = new ArrayList<>();
                try {
                    Player p = new Player("resu");
                    p.setWorker(0, new Point(0, 1));
                    p.setWorker(1, new Point(5, 1));
                    players.add(p);
                    p = new Player("user");
                    p.setWorker(0, new Point(3, 4));
                    p.setWorker(1, new Point(0, 0));
                    players.add(p);
                } catch (IOException ignore) {
                }
            }

            @Override
            public void resetBoard() {
                try {
                    board = new Board();
                    for (int i = 0; i < 3; ++i)
                        board.incrementTowerSize(new Point(1, 1));
                } catch (Exception ignored) {
                }
            }

            @Override
            public void end(String winningPlayer) throws IOException {
                s = winningPlayer;
            }
        };
    }

    @Test
    public void moveActionTest() throws IOException {
        MoveAction action = new MoveAction("user", new Point(0, 0), new Point(1, 1));

        model.executeAction(action);
        assertEquals("user", model.s);
        assertTrue(model.getPlayerByUsername("user").getWorkerPos(1).equals(new Point(1, 1)));
    }

    @Test
    public void apolloMoveActionTest() throws IOException {
        MoveAction action = new ApolloMoveAction("user", new Point(0, 0), new Point(0, 1));

        model.executeAction(action);
        assertTrue(model.getPlayerByUsername("user").getWorkerPos(1).equals(new Point(0, 1)));
        assertTrue(model.getPlayerByUsername("resu").getWorkerPos(0).equals(new Point(0, 0)));
    }

    @Test
    public void minotaurMoveActionTest() throws IOException {
        MoveAction action = new MinotaurMoveAction("user", new Point(0, 0), new Point(0, 1));

        model.executeAction(action);
        assertTrue(model.getPlayerByUsername("user").getWorkerPos(1).equals(new Point(0, 1)));
        assertTrue(model.getPlayerByUsername("resu").getWorkerPos(0).equals(new Point(0, 2)));
    }

    @Test
    public void proposalTest() {
        MoveAction action = new MoveAction("foo", new Point(0, 0), new Point(1, 1));
        MoveProposal proposal = action.getProposal();
        assertTrue(action.getTo().equals(proposal.getPoint()));
    }
}

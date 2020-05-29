package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinotaurTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        Match match = new FakeMatch() {
            @Override
            public Player getPlayerByUsername(String name) {
                Player player = null;
                try {
                    player = new Player("minotaurOwner");
                    player.setWorker(0, new Point(0, 0));
                    player.setWorker(1, new Point(1, 1));
                } catch(IOException e) {}

                return player;
            }

            @Override
            public boolean isCellFree(Point pos) {
                return !(pos.getX() == 1 || pos.getY() == 1);
            }
        };
        God minotaur = new Minotaur("minotaurOwner");
        match.getBoard().setAsCompleted(new Point(2, 0));

        List<MoveAction> moves = new ArrayList<>();
        minotaur.addMoves(moves, "minotaurOwner", 0, match);

        assertEquals(moves.size(), 1);
    }
}

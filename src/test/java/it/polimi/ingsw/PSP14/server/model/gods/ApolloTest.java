package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApolloTest {
    /**
     * checks if apollo correctly adds moves to busy cells, discarding cells occupied by the other allied worker
     */
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        MatchModel model = new FakeMatchModel() {
            @Override
            public Player getPlayerByUsername(String name) {
                Player player = null;
                try {
                    player = new Player("apolloOwner");
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
        God apollo = new Apollo("apolloOwner");

        List<MoveAction> moves = new ArrayList<>();
        apollo.addMoves(moves, "apolloOwner", 0, model);

        assertEquals(moves.size(), 2);
    }
}

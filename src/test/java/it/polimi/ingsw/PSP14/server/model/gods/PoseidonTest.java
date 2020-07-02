package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoseidonTest {
    /**
     * checks if poseidon checks other worker's height and can build no more than three times
     */
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatchModel match = new FakeMatchModel() {
            @Override
            public boolean build(String player, int workerIndex) throws IOException {
                num++;

                return true;
            }

            @Override
            public Player getPlayerByUsername(String username) {
                Player player = null;
                try {
                    player = new Player("poseidonOwner");
                    if (flag)
                        player.setWorker(0, new Point(1, 0));
                    else
                        player.setWorker(0, new Point(0, 0));
                    player.setWorker(1, new Point(1, 1));
                } catch (IOException e) {
                }

                return player;
            }
        };
        MatchController controller = new FakeMatchController() {
            @Override
            public boolean askQuestion(String player, String s) throws IOException {
                return true;
            }
        };
        God poseidon = new Poseidon("poseidonOwner");
        Board board = match.getBoard();
        board.incrementTowerSize(new Point(0, 0));

        poseidon.afterTurn("poseidonOwner", 1, controller, match);
        assertEquals(0, match.num);
        match.flag = true;
        poseidon.afterTurn("poseidonOwner", 0, controller, match);
        assertEquals(3, match.num);
    }
}

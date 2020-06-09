package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HephaestusTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        Match match = new FakeMatch() {
            @Override
            public Action getLastAction() {
                return new BuildAction("hephaestusOwner", new Point(0, 0), false, 1);
            }
        };
        MatchController controller = new FakeMatchController();
        God hephaestus = new Hephaestus("hephaestusOwner");
        Board board = match.getBoard();
        board.incrementTowerSize(new Point(0, 0));

        hephaestus.afterBuild("hephaestusOwner", 0, controller, match);

        assertEquals(board.getTowerSize(new Point(0, 0)), 2);
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
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
        MatchModel model = new FakeMatchModel() {
            @Override
            public Action getLastAction() {
                return new BuildAction("hephaestusOwner", new Point(0, 0), false, 1);
            }
        };
        MatchController controller = new FakeMatchController();
        God hephaestus = new Hephaestus("hephaestusOwner");
        Board board = model.getBoard();
        board.incrementTowerSize(new Point(0, 0));

        hephaestus.afterBuild("hephaestusOwner", 0, controller, model);

        assertEquals(board.getTowerSize(new Point(0, 0)), 2);
    }
}

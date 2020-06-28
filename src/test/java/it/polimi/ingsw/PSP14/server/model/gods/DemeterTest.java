package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DemeterTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatchModel match = new FakeMatchModel() {
            @Override
            public List<BuildAction> getBuildable(String player, int worker) {
                List<BuildAction> builds = new ArrayList<>();
                builds.add(new BuildAction("demeterOwner", new Point(0, 0), false, 1));
                builds.add(new BuildAction("demeterOwner", new Point(1, 0), false, 1));
                builds.add(new BuildAction("demeterOwner", new Point(0, 1), false, 1));

                return builds;
            }

            @Override
            public Action getLastAction() {
                return new BuildAction("demeterOwner", new Point(0, 0), false, 1);
            }

            @Override
            public void executeAction(Action action) throws IOException {
            }
        };
        MatchController controller = new FakeMatchController() {
            @Override
            public BuildAction askBuild(String player, List<BuildAction> builds) throws IOException {
                builds.forEach(p -> assertFalse(p.getPoint().equals(new Point(0, 0))));
                assertEquals(2, builds.size());
                return builds.get(0);
            }
        };
        God demeter = new Demeter("demeterOwner");

        demeter.afterBuild("demeterOwner", 0, controller, match);


    }
}

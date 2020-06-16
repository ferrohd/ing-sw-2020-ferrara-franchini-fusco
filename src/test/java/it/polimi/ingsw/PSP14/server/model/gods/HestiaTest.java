package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HestiaTest {
    @Test
    public void functionalityTest() throws IOException {
        God hestia = new Hestia("hestiaOwner");
        MatchModel model = new FakeMatchModel() {
            @Override
            public void build(String player, int workerIndex) throws IOException {
                List<BuildAction> builds = new ArrayList<>();
                builds.add(new BuildAction("hestiaOwner", new Point(0,0), false, 1));
                builds.add(new BuildAction("hestiaOwner", new Point(1,1), false, 1));

                hestia.removeBuilds(builds, "hestiaOwner", workerIndex, this);

                assertEquals(builds.size(), 1);
            }

            @Override
            public List<BuildAction> getBuildable(String player, int worker) {
                List<BuildAction> builds = new ArrayList<>();
                builds.add(new BuildAction("hestiaOwner", new Point(0,0), false, 1));
                builds.add(new BuildAction("hestiaOwner", new Point(1,1), false, 1));

                return builds;
            }
        };

        hestia.afterBuild("hestiaOwner", 0, new FakeMatchController(), model);
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchController;
import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AtlasTest {
    /**
     * checks if atlas adds dome building BuildActions at the same position of already existing BuildActions
     */
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        MatchModel model = new FakeMatchModel();
        God atlas = new Atlas("atlasOwner");
        MatchController controller = new FakeMatchController();

        List<BuildAction> builds = new ArrayList<>();
        builds.add(new BuildAction("atlasOwner", new Point(0, 0), false, 1));
        builds.add(new BuildAction("atlasOwner", new Point(1, 1), true, 1));

        atlas.beforeBuild("atlasOwner", 0, controller, model);
        atlas.addBuilds(builds, "atlasOwner", 0, model);

        assertEquals(3, builds.size());

        atlas.removeBuilds(builds, "atlasOwner", 0, model);

        assertEquals(2, builds.size());
        builds.forEach(b -> assertTrue(b.getProposal().hasDome()));
    }
}

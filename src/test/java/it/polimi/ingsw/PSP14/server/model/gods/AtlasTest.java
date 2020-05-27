package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.Match;
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
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        Match match = new FakeMatch();
        God atlas = new Atlas("atlasOwner");
        ClientConnection client = new FakeClientConnection();

        List<BuildAction> builds = new ArrayList<>();
        builds.add(new BuildAction("atlasOwner", new Point(0, 0), false, 1));
        builds.add(new BuildAction("atlasOwner", new Point(1, 1), true, 1));

        atlas.beforeBuild("atlasOwner", 0, client, match);
        atlas.addBuilds(builds, "atlasOwner", 0, match);

        assertEquals(3, builds.size());

        atlas.removeBuilds(builds, "atlasOwner", 0, match);

        assertEquals(2, builds.size());
        builds.forEach(b -> assertTrue(b.getProposal().hasDome()));
    }
}

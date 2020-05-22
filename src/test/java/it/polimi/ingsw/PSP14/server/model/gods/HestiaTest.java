package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HestiaTest {
    @Test
    public void functionalityTest() throws IOException {
        God hestia = new Hestia("hestiaOwner");
        Match match = new FakeMatch() {
            @Override
            public void build(String player, ClientConnection client, int workerIndex) throws IOException {
                List<BuildAction> builds = new ArrayList<>();
                builds.add(new BuildAction("hestiaOwner", new Point(0,0), false, 1));
                builds.add(new BuildAction("hestiaOwner", new Point(1,1), false, 1));

                hestia.removeBuilds(builds, "hestiaOwner", workerIndex, this);

                assertEquals(builds.size(), 1);
            }
        };

        hestia.afterBuild("hestiaOwner", 0, new FakeClientConnection(), match);
    }
}

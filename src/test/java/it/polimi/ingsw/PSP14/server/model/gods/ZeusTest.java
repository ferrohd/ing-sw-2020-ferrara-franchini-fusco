package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.fake.FakeMatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZeusTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        God god = new Zeus("owner");
        FakeMatchModel match = new FakeMatchModel() {
            @Override
            public Player getPlayerByUsername(String username) {
                Player p = null;
                try {
                    p = new Player("owner");
                    p.setWorker(0, new Point(0, 0));
                } catch(IOException e) {};

                return p;
            }
        };
        List<BuildAction> builds = new ArrayList<>();
        god.addBuilds(builds, "owner", 0, match);
        assertEquals(1, builds.size());
        assertTrue(builds.get(0).getPoint().equals(new Point(0, 0)));
    }
}

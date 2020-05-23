package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.EndGameException;
import it.polimi.ingsw.PSP14.server.model.FakeClientConnection;
import it.polimi.ingsw.PSP14.server.model.FakeMatch;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.TowerSizeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DemeterTest {
    @Test
    public void functionalityTest() throws IOException, TowerSizeException {
        FakeMatch match = new FakeMatch() {
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
        ClientConnection client = new FakeClientConnection() {
            @Override
            public int askBuild(List<BuildProposal> proposals) throws IOException {
                proposals.forEach(p -> assertFalse(p.getPoint().equals(new Point(0, 0))));
                assertEquals(2, proposals.size());
                return 0;
            }
        };
        God demeter = new Demeter("demeterOwner");

        demeter.afterBuild("demeterOwner", 0, client, match);


    }
}

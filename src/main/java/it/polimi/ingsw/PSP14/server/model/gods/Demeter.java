package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Demeter extends God {
    public Demeter(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, MatchController controller, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        List<BuildAction> builds = model.getBuildable(player, workerIndex);
        BuildAction lastBuild = (BuildAction) model.getLastAction();
        builds = builds.stream().filter(m -> !m.getPoint().equals(lastBuild.getPoint())).collect(Collectors.toList());

        if(builds.size() > 0) {
            boolean choice = controller.askQuestion(player, "DEMETER: By my blessing, will you build again?");
            if (choice) {
                BuildAction action = controller.askBuild(player, builds);
                model.executeAction(action);
            }
        }
    }
}

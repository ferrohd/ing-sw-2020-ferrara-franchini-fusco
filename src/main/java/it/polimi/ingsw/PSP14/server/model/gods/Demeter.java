package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.BuildProposalMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Demeter extends God {
    public Demeter(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        boolean choice = client.askQuestion("DEMETER: Do you want to build again?");

        if(choice) {
            List<BuildAction> builds = match.getBuildable(player, workerIndex);
            BuildAction lastBuild = (BuildAction) match.getHistory().get(match.getHistory().size() - 1);
            builds = builds.stream().filter(m -> !m.getPoint().equals(lastBuild.getPoint())).collect(Collectors.toList());
            List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());

            int buildChoice = client.askBuild(buildProposals);
            // TODO handle this better
            Action action = builds.get(buildChoice);
            match.executeAction(action);

        }
    }
}

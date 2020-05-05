package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.BuildProposalMessage;
import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.MoveProposalMessage;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Demeter extends God {
    public Demeter(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, ClientConnection client, Match match, MatchController matchController) {
        if(!player.equals(getOwner())) return;

        Message message = new YesNoMessage("DEMETER: Do you want to build again?");
        int choice;
        try {
            client.sendMessage(message);
            choice = client.receiveChoice();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
            return;
        }

        if(choice == 1) {
            List<BuildAction> builds = match.getBuildable(player, workerIndex);
            BuildAction lastBuild = (BuildAction) match.getHistory().get(match.getHistory().size() - 1);
            builds = builds.stream().filter(m -> !m.getPoint().equals(lastBuild.getPoint())).collect(Collectors.toList());
            List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());
            message = new BuildProposalMessage(buildProposals);
            try {
                client.sendMessage(message);
                choice = client.receiveChoice();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            builds.get(choice).execute(match, matchController.getClientConnections());
        }
    }
}

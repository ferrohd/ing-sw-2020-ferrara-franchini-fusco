package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.MoveProposalMessage;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Artemis extends God {
    public Artemis(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match) {
        Message message = new YesNoMessage("Do you want to move again?");
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
            List<MoveAction> movements = match.getMovements(player, workerIndex);
            MoveAction lastMove = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);
            movements = movements.stream().filter(m -> !m.getTo().equals(lastMove.getFrom())).collect(Collectors.toList());
            List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());
            message = new MoveProposalMessage(moveProposals);
            try {
                client.sendMessage(message);
                choice = client.receiveChoice();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            movements.get(choice).execute(match);
        }

    }
}

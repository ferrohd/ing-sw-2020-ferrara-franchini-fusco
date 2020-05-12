package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.MoveProposalMessage;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Artemis extends God {
    public Artemis(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        boolean choice = client.askQuestion("ARTEMIS: Do you want to move again?");

        if(choice) {
            List<MoveAction> movements = match.getMovements(player, workerIndex);
            MoveAction lastMove = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);
            movements = movements.stream().filter(m -> !m.getTo().equals(lastMove.getFrom())).collect(Collectors.toList());
            List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());

            int moveChoice = client.askMove(moveProposals);
            // TODO handle this better
            Action action = movements.get(moveChoice);
            match.executeAction(action);
        }

    }
}

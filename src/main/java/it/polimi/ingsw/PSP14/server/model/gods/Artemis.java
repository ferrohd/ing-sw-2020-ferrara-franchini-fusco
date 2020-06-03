package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

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


        List<MoveAction> movements = match.getMovements(player, workerIndex);
        MoveAction lastMove = (MoveAction) match.getLastAction();
        movements = movements.stream().filter(m -> !m.getTo().equals(lastMove.getFrom())).collect(Collectors.toList());
        List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());

        if(moveProposals.size() > 0) {
            boolean choice = client.askQuestion("ARTEMIS: Do you want get closer to the prey?");
            if (choice) {
                int moveChoice = client.askMove(moveProposals);
                Action action = movements.get(moveChoice);
                match.executeAction(action);
            }
        }

    }
}

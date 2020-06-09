package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
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
    public void afterMove(String player, int workerIndex, MatchController controller, Match model) throws IOException {
        if(!player.equals(getOwner())) return;


        List<MoveAction> movements = model.getMovements(player, workerIndex);
        MoveAction lastMove = (MoveAction) model.getLastAction();
        movements = movements.stream().filter(m -> !m.getTo().equals(lastMove.getFrom())).collect(Collectors.toList());

        if(movements.size() > 0) {
            boolean choice = controller.askQuestion(player, "ARTEMIS: Do you want get closer to the prey?");
            if (choice) {
                MoveAction action = controller.askMove(player, movements);
                model.executeAction(action);
            }
        }

    }
}

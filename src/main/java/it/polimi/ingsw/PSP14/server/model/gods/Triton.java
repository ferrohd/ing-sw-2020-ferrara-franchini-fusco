package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;

public class Triton extends God {
    public Triton(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, MatchController controller, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        MoveAction lastMove = (MoveAction) model.getLastAction();

        if(lastMove.getTo().getX() == 0 || lastMove.getTo().getX() == 4 ||
                lastMove.getTo().getY() == 0 || lastMove.getTo().getY() == 4) {

            boolean choice = controller.askQuestion(player, "TRITON: Hey! How about riding another wave?");

            if(choice) {
                model.move(player, workerIndex);
            }
        }
    }
}

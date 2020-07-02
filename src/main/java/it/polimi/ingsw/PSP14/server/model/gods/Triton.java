package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;

/**
 * Your move:
 * Each time your Worker moves into a perimeter space, it may immediately move again.
 */
public class Triton extends God {

    public static final String MESSAGE = "TRITON: Hey! How about riding another wave?";

    public Triton(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        if (!player.equals(getOwner())) return;

        MoveAction lastMove = (MoveAction) model.getLastAction();

        if (lastMove.getTo().getX() == 0 || lastMove.getTo().getX() == 4 ||
                lastMove.getTo().getY() == 0 || lastMove.getTo().getY() == 4) {

            boolean choice = controller.askQuestion(player, MESSAGE);

            if (choice) {
                model.move(player, workerIndex);
            }
        }
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Your turn:
 * If your Worker does not move up, it may build both before and after moving.
 */
public class Prometheus extends God {
    public static final String MESSAGE = "PROMETHEUS: Do not move up and build twice. Art thou willing to accept my deal?";
    private boolean activated = false;

    public Prometheus(String owner) {
        super(owner);
    }

    @Override
    public void beforeMove(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        if (!player.equals(getOwner())) return;

        boolean choice = controller.askQuestion(player, MESSAGE);

        if (choice) {
            activated = true;
            model.build(player, workerIndex);
        }
    }

    @Override
    public void removeMoves(List<MoveAction> moves, String player, int workerIndex, MatchModel model) throws IOException {
        if (!player.equals(getOwner())) return;

        if (activated) {
            List<MoveAction> illegalMoves = moves.stream()
                    .filter(m -> model.getBoard().getTowerSize(m.getFrom()) < model.getBoard().getTowerSize(m.getTo()))
                    .collect(Collectors.toList());

            moves.removeAll(illegalMoves);

            activated = false;
        }
    }
}

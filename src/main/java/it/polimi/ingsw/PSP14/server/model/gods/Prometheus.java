package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Prometheus extends God {
    private boolean activated = false;

    public Prometheus(String owner) {
        super(owner);
    }

    @Override
    public void beforeMove(String player, int workerIndex, MatchController controller, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        boolean choice = controller.askQuestion(player, "PROMETHEUS: Do not move up and build twice. Art thou willing to accept my deal?");

        if (choice) {
            activated = true;
            model.build(player, workerIndex);
        }
    }

    @Override
    public void removeMoves(List<MoveAction> moves, String player, int workerIndex, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        if(activated) {
            List<MoveAction> illegalMoves = moves.stream()
                    .filter(m -> model.getBoard().getTowerSize(m.getFrom()) < model.getBoard().getTowerSize(m.getTo()))
                    .collect(Collectors.toList());

            moves.removeAll(illegalMoves);

            activated = false;
        }
    }
}

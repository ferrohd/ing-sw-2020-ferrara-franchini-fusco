package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.board.Player;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Prometheus extends God {
    private boolean activated = false;

    public Prometheus(String owner) {
        super(owner);
    }

    @Override
    public void beforeMove(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        boolean choice = client.askQuestion("PROMETHEUS: Do not move up and build twice. Art thou willing to accept my deal?");

        if (choice) {
            activated = true;
            match.build(player, client, workerIndex);
        }
    }

    @Override
    public void removeMoves(List<MoveAction> moves, Player player, int workerIndex, Match match) throws IOException {
        if(!player.getUsername().equals(getOwner())) return;

        if(activated) {
            List<MoveAction> illegalMoves = moves.stream()
                    .filter(m -> match.getBoard().getTowerSize(m.getFrom()) < match.getBoard().getTowerSize(m.getTo()))
                    .collect(Collectors.toList());

            moves.removeAll(illegalMoves);

            activated = false;
        }
    }
}

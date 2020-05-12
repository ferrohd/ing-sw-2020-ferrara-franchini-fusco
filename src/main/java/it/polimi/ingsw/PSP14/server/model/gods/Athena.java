package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.board.Player;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Athena extends God {
    private boolean activated = false;

    public Athena(String owner) {
        super(owner);
    }

    @Override
    public void beforeTurn(String player, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        activated = false;
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        MoveAction lastAction = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);
        if(match.getBoard().getTowerSize(lastAction.getFrom()) < match.getBoard().getTowerSize(lastAction.getFrom())) {
            activated = true;
        }
    }

    @Override
    public void removeMoves(List<MoveAction> moves, Player player, int workerIndex, Match match) throws IOException {
        if(player.getUsername().equals(getOwner())) return;

        if(activated) {
            List<MoveAction> illegalMoves = moves.stream().filter(m ->
                match.getBoard().getTowerSize(m.getFrom()) < match.getBoard().getTowerSize(m.getFrom())
            ).collect(Collectors.toList());

            moves.removeAll(illegalMoves);
        }
    }
}

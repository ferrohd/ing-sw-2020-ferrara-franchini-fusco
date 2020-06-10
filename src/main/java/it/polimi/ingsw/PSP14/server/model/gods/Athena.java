package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Athena extends God {
    private boolean activated = false;

    public Athena(String owner) {
        super(owner);
    }

    @Override
    public void beforeTurn(String player, MatchController controller, MatchModel model) throws IOException {
        if(!player.equals(getOwner())) return;

        activated = false;
    }

    @Override
    public void afterMove(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        if(!player.equals(getOwner())) return;

        MoveAction lastAction = (MoveAction) model.getLastAction();
        if(model.getBoard().getTowerSize(lastAction.getFrom()) < model.getBoard().getTowerSize(lastAction.getTo())) {
            activated = true;
        }
    }

    @Override
    public void removeMoves(List<MoveAction> moves, String player, int workerIndex, MatchModel model) throws IOException {
        if(player.equals(getOwner())) return;

        if(activated) {
            List<MoveAction> illegalMoves = moves.stream().filter(m ->
                    model.getBoard().getTowerSize(m.getFrom()) < model.getBoard().getTowerSize(m.getTo())
            ).collect(Collectors.toList());

            moves.removeAll(illegalMoves);
        }
    }
}

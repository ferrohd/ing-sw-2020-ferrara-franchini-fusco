package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;

public class Pan extends God {
    public Pan(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        if(!player.equals(getOwner())) return;

        MoveAction lastMove = (MoveAction) model.getLastAction();
        int levelFrom = model.getBoard().getTowerSize(lastMove.getFrom());
        int levelTo = model.getBoard().getTowerSize(lastMove.getTo());

        if(levelFrom - levelTo > 1) {
            model.end(player);
        }
    }
}

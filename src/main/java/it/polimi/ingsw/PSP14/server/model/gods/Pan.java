package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;

public class Pan extends God {
    public Pan(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match, MatchController matchController) {
        MoveAction lastMove = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);
        int levelFrom = match.getBoard().getTowerSize(lastMove.getFrom());
        int levelTo = match.getBoard().getTowerSize(lastMove.getTo());

        if(levelFrom - levelTo > 1) {
            matchController.end(player);
        }
    }
}

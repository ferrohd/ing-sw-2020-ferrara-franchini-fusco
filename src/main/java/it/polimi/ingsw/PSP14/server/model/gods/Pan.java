package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;

public class Pan extends God {
    public Pan(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        MoveAction lastMove = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);
        int levelFrom = match.getBoard().getTowerSize(lastMove.getFrom());
        int levelTo = match.getBoard().getTowerSize(lastMove.getTo());

        if(levelFrom - levelTo > 1) {
            match.end(player);
        }
    }
}

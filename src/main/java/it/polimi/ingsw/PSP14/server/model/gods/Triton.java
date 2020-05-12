package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;

public class Triton extends God {
    public Triton(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        MoveAction lastMove = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);

        if(lastMove.getTo().getX() == 0 || lastMove.getTo().getX() == 4 ||
                lastMove.getTo().getY() == 0 || lastMove.getTo().getY() == 4) {

            boolean choice = client.askQuestion("TRITON: Do you want to move again?");

            if(choice) {
                match.move(player, client, workerIndex);
            }
        }
    }
}

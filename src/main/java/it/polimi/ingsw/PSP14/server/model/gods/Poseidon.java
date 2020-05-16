package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;

public class Poseidon extends God {
    public Poseidon(String owner) {
        super(owner);
    }

    @Override
    public void afterTurn(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        int otherWorker = workerIndex == 1 ? 0 : 1;

        if(match.getBoard().getTowerSize(match.getPlayerByUsername(player).getWorkerPos(otherWorker)) == 0) {
            for(int i = 0; i < 3; ++i) {
                boolean choice = client.askQuestion("POSEIDON: Why don't you let the other guy build as well?");

                if(!choice) break;

                match.build(player, client, otherWorker);
            }
        }
    }
}

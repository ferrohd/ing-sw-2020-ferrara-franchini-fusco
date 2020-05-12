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
                Message message = new YesNoMessage("POSEIDON: Do you want to build with your unmoved worker?");
                int choice;
                try {
                    client.sendMessage(message);
                    choice = client.receiveChoice();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                    return;
                }

                if(choice == 0) break;

                try {
                    match.build(player, client, otherWorker);
                } catch(IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }
}

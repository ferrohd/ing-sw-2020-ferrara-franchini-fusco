package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;

public class Triton extends God {
    public Triton(String owner) {
        super(owner);
    }

    @Override
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match) {
        if(!player.equals(getOwner())) return;

        MoveAction lastMove = (MoveAction) match.getHistory().get(match.getHistory().size() - 1);

        if(lastMove.getTo().getX() == 0 || lastMove.getTo().getX() == 4 ||
                lastMove.getTo().getY() == 0 || lastMove.getTo().getY() == 4) {

            Message message = new YesNoMessage("TRITON: Do you want to move again?");
            int choice;
            try {
                client.sendMessage(message);
                choice = client.receiveChoice();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
                return;
            }

            if(choice == 1) {
                try {
                    match.move(player, client, workerIndex);
                } catch(IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }
}

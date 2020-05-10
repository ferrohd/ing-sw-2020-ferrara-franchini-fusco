package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Worker;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Prometheus extends God {
    private boolean activated = false;

    public Prometheus(String owner) {
        super(owner);
    }

    @Override
    public void beforeMove(String player, int workerIndex, ClientConnection client, Match match) {
        if(!player.equals(getOwner())) return;

        Message message = new YesNoMessage("PROMETHEUS: do you want to build now?");
        int choice;
        try {
            client.sendMessage(message);
            choice = client.receiveChoice();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
            return;
        }

        if (choice == 1) {
            activated = true;
            try {
                match.build(player, client, workerIndex);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
                return;
            }
        }
    }

    @Override
    public void removeMoves(List<MoveAction> moves, Player player, Worker worker, Match match) {
        if(!player.getUsername().equals(getOwner())) return;

        if(activated) {
            List<MoveAction> illegalMoves = moves.stream().filter(m -> {
                return match.getBoard().getTowerSize(m.getFrom()) < match.getBoard().getTowerSize(m.getTo());
            }).collect(Collectors.toList());

            moves.removeAll(illegalMoves);

            activated = false;
        }
    }
}

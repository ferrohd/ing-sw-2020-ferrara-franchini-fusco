package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Hestia extends God {
    private boolean activated = false;

    public Hestia(String owner) {
        super(owner);
    }

    @Override
    public void beforeTurn(String player, ClientConnection client, Match match) throws IOException {
        activated = false;
    }

    @Override
    public void afterBuild(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        if(!activated) {
            boolean choice = client.askQuestion("HESTIA: Do you want to build again, my dear?");

            if (choice) {
                activated = true;
                match.build(player, client, workerIndex);
            }
        }
    }
}

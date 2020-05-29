package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;

public class Hephaestus extends God {
    public Hephaestus(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        BuildAction lastBuild = (BuildAction) match.getLastAction();

        if(!match.getBoard().getIsCompleted(lastBuild.getPoint()) && match.getBoard().getTowerSize(lastBuild.getPoint()) < 3) {
            boolean choice = client.askQuestion("HEPHAESTUS: Build on build will ya?");

            if(choice) {
                match.executeAction(new BuildAction(player, lastBuild.getPoint(), false, 1));
            }
        }
    }
}

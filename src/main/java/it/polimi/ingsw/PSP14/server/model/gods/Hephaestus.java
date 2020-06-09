package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;

public class Hephaestus extends God {
    public Hephaestus(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, MatchController controller, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        BuildAction lastBuild = (BuildAction) model.getLastAction();

        if(!model.getBoard().getIsCompleted(lastBuild.getPoint()) && model.getBoard().getTowerSize(lastBuild.getPoint()) < 3) {
            boolean choice = controller.askQuestion(player, "HEPHAESTUS: Build on build will ya?");

            if(choice) {
                model.executeAction(new BuildAction(player, lastBuild.getPoint(), false, 1));
            }
        }
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.Worker;

import java.util.List;

public class Zeus extends God {
    public Zeus(String owner) {
        super(owner);
    }

    @Override
    public void addBuilds(List<BuildAction> builds, Player player, Worker worker, Match match) {
        if(!player.getUsername().equals(getOwner())) return;

        Point curr = worker.getPos();
        if(match.getBoard().getTowerSize(curr) < 3 && !match.getBoard().getIsCompleted(curr))
            builds.add(new BuildAction(player.getUsername(), curr, false, 1));
    }
}

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.board.Worker;

import java.util.List;

public class Zeus extends God {
    public Zeus(String owner) {
        super(owner);
    }

    @Override
    public void addBuilds(List<BuildAction> builds, Player player, int workerIndex, Match match) {
        if(!player.getUsername().equals(getOwner())) return;

        Point curr = player.getWorkerPos(workerIndex);
        if(match.getBoard().getTowerSize(curr) < 3 && !match.getBoard().getIsCompleted(curr))
            builds.add(new BuildAction(player.getUsername(), curr, false, 1));
    }
}

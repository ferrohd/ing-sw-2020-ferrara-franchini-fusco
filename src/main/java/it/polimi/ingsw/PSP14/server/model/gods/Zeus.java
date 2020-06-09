package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.List;

public class Zeus extends God {
    public Zeus(String owner) {
        super(owner);
    }

    @Override
    public void addBuilds(List<BuildAction> builds, String player, int workerIndex, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        Player playing = model.getPlayerByUsername(player);
        Point curr = playing.getWorkerPos(workerIndex);
        if(model.getBoard().getTowerSize(curr) < 3 && !model.getBoard().getIsCompleted(curr))
            builds.add(new BuildAction(player, curr, false, 1));
    }
}

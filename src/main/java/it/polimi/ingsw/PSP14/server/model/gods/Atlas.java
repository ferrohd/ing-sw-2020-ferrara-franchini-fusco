package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.model.actions.AtlasBuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.*;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Worker;

import java.util.ArrayList;
import java.util.List;

public class Atlas extends God {
    public Atlas(String owner) {
        super(owner);
    }

    @Override
    public void addBuilds(List<BuildAction> builds, Player player, int workerIndex, Match match) {
        if(!player.getUsername().equals(getOwner())) {
            return;
        }

        ArrayList<BuildAction> newBuilds = new ArrayList<>();

        for(BuildAction b: builds) {
            BuildProposal proposal = b.getProposal();
            if(!proposal.hasDome()) {
                BuildAction newBuild = new AtlasBuildAction(player.getUsername(), proposal.getPoint());
                newBuilds.add(newBuild);
            }
        }

        builds.addAll(newBuilds);
    }
}

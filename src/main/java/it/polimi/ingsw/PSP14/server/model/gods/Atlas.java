package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.actions.AtlasBuildAction;
import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.*;

import java.util.ArrayList;
import java.util.List;

public class Atlas extends God {
    public Atlas(String owner) {
        super(owner);
    }

    @Override
    public void addBuilds(List<BuildAction> builds, Player player, Worker worker, Match match) {
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

package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.actions.AtlasBuildAction;
import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.HephaestusBuildAction;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Hephaestus extends God {
    public Hephaestus(String owner) {
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
            Point p = proposal.getPoint();
            if(!proposal.hasDome() && match.getBoard().getTowerSize(p) < 3) {
                BuildAction newBuild = new HephaestusBuildAction(player.getUsername(), proposal.getPoint());
                newBuilds.add(newBuild);
            }
        }

        builds.addAll(newBuilds);
    }
}

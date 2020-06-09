package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.actions.AtlasBuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Atlas extends God {
    private boolean activated = false;

    public Atlas(String owner) {
        super(owner);
    }

    @Override
    public void beforeBuild(String player, int workerIndex, MatchController controller, Match model) throws IOException {
        if(!player.equals(getOwner())) return;

        activated = controller.askQuestion(player, "ATLAS: DOMEEEEEES???!!!?!?!??");
    }

    @Override
    public void addBuilds(List<BuildAction> builds, String player, int workerIndex, Match model) throws IOException {
        if(!player.equals(getOwner()) || !activated) return;

        ArrayList<BuildAction> newBuilds = new ArrayList<>();

        for(BuildAction b: builds) {
            BuildProposal proposal = b.getProposal();
            if(!proposal.hasDome()) {
                BuildAction newBuild = new AtlasBuildAction(player, proposal.getPoint());
                newBuilds.add(newBuild);
            }
        }

        builds.addAll(newBuilds);
    }

    @Override
    public void removeBuilds(List<BuildAction> builds, String player, int workerIndex, Match model) throws IOException {
        if(!player.equals(getOwner()) || !activated) return;

        activated = false;

        List<BuildAction> invalidBuilds = builds.stream().filter(b -> !b.getProposal().hasDome()).collect(Collectors.toList());

        builds.removeAll(invalidBuilds);
    }
}

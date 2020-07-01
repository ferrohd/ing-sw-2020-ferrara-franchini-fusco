package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.AtlasBuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Your build:
 * Your Worker may build a dome at any level.
 */
public class Atlas extends God {
    public static final String MESSAGE = "ATLAS: DOMEEEEEES???!!!?!?!??";
    private boolean activated = false;

    public Atlas(String owner) {
        super(owner);
    }

    @Override
    public void beforeBuild(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        if(!player.equals(getOwner())) return;

        activated = controller.askQuestion(player, MESSAGE);
    }

    @Override
    public void addBuilds(List<BuildAction> builds, String player, int workerIndex, MatchModel model) throws IOException {
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
    public void removeBuilds(List<BuildAction> builds, String player, int workerIndex, MatchModel model) throws IOException {
        if(!player.equals(getOwner()) || !activated) return;

        activated = false;

        List<BuildAction> invalidBuilds = builds.stream().filter(b -> !b.getProposal().hasDome()).collect(Collectors.toList());

        builds.removeAll(invalidBuilds);
    }
}

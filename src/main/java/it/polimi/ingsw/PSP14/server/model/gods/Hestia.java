package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Your Build:
 * Your Worker may build one additional time, but this cannot be on a perimeter space.
 */
public class Hestia extends God {
    private boolean activated = false;

    public Hestia(String owner) {
        super(owner);
    }

    @Override
    public void beforeTurn(String player, MatchController controller, MatchModel model) throws IOException {
        activated = false;
    }

    @Override
    public void afterBuild(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
        if(!player.equals(getOwner())) return;


        if(!activated) {
            activated = true;
            List<BuildAction> buildable = model.getBuildable(player, workerIndex);
            if(buildable.size() > 0) {
                boolean choice = controller.askQuestion(player, "HESTIA: Do you want to build again, my dear?");
                if (choice) {
                    model.build(player, workerIndex);
                }
            }
        }
    }

    @Override
    public void removeBuilds(List<BuildAction> builds, String player, int workerIndex, MatchModel model) throws IOException {
        if(!activated || !player.equals(getOwner())) return;

        List<BuildAction> toRemove = builds.stream().filter(b ->
                b.getPoint().getX() == 0 || b.getPoint().getX() == 4 ||
                        b.getPoint().getY() == 0 || b.getPoint().getY() == 4
        ).collect(Collectors.toList());

        builds.removeAll(toRemove);
    }
}

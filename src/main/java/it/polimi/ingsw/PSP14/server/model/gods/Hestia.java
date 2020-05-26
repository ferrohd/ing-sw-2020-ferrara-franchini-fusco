package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.core.messages.Message;
import it.polimi.ingsw.PSP14.core.messages.YesNoMessage;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.board.Player;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Hestia extends God {
    private boolean activated = false;

    public Hestia(String owner) {
        super(owner);
    }

    @Override
    public void afterBuild(String player, int workerIndex, ClientConnection client, Match match) throws IOException {
        if(!player.equals(getOwner())) return;

        boolean choice = client.askQuestion("HESTIA: Do you want to build again, my dear?");

        if(choice) {
            activated = true;
            match.build(player, client, workerIndex);
        }
    }

    @Override
    public void removeBuilds(List<BuildAction> builds, String player, int workerIndex, Match match) throws IOException {
        if(!activated || !player.equals(getOwner())) return;

        activated = false;

        List<BuildAction> toRemove = builds.stream().filter(b ->
                b.getPoint().getX() == 0 || b.getPoint().getX() == 4 ||
                        b.getPoint().getY() == 0 || b.getPoint().getY() == 4
        ).collect(Collectors.toList());

        builds.removeAll(toRemove);
    }
}

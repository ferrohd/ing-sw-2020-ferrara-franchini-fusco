package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class God {
    private String owner;

    public God(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void addMoves(List<MoveAction> moves, Player player, Worker worker, Match match) {
    }

    public void removeMoves(List<MoveAction> moves, Player player, Worker worker, Match match) {
    }

    public void addBuilds(List<BuildAction> builds, Player player, Worker worker, Match match) {
    }
}

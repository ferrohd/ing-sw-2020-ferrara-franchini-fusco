package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic god, has nothing implemented
 */
public class God {
    private String owner;

    public God(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    /**
     * Implements god effects that add additional move possibilities, by adding MoveActions to the current list of
     * possible MoveActions
     * @param moves moves already calculated
     * @param player moving player
     * @param worker moving worker
     * @param match current match
     */
    public void addMoves(List<MoveAction> moves, Player player, Worker worker, Match match) {
    }

    /**
     * Implements god effects that limit the possibility of movement, by removing MoveActions from the current list of
     * possible MoveActions
     * @param moves moves already calculated
     * @param player moving player
     * @param worker moving worker
     * @param match current match
     */
    public void removeMoves(List<MoveAction> moves, Player player, Worker worker, Match match) {
    }

    /**
     * Implements god effects that add additional build possibilities, by adding BuildActions to the current list of
     * possible BuildActions
     * @param builds builds already calculated
     * @param player building player
     * @param worker building worker
     * @param match current match
     */
    public void addBuilds(List<BuildAction> builds, Player player, Worker worker, Match match) {
    }

    /**
     * Implements god effects that activate right after a player has moved
     * @param player current turn's player
     * @param workerIndex worker that has just moved
     * @param client ClientConnection relative to the current turn's player
     * @param match current match
     * @param matchController current matchController
     */
    public void afterMove(String player, int workerIndex, ClientConnection client, Match match, MatchController matchController){
    }

    public void beforeMove(String player, int workerIndex, ClientConnection client, Match match, MatchController matchController){
    }

    /**
     * Implements god effects that activate right after a player has built
     * @param player current turn's player
     * @param workerIndex worker that has just built
     * @param client ClientConnection relative to the current turn's player
     * @param match current match
     * @param matchController current matchController
     */
    public void afterBuild(String player, int workerIndex, ClientConnection client, Match match, MatchController matchController){
    }

    public void beforeBuild(String player, int workerIndex, ClientConnection client, Match match, MatchController matchController){
    }
}

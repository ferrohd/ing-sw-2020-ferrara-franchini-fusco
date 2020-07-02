package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;

import java.io.IOException;
import java.util.List;

/**
 * Generic god, has nothing implemented.
 * Gods can add and remove possible moves from the player's default ones.
 * A specific god can implement one or all of the following methods, according
 * to its ability description.
 */
public class God {
    private final String owner;

    /**
     * Constructor.
     *
     * @param owner the player (username) that owns this god
     */
    public God(String owner) {
        this.owner = owner;
    }

    /**
     * Get the string id of the player that owns this god
     *
     * @return the id of the player
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Implements god effects that add additional move possibilities, by adding MoveActions to the current list of
     * possible MoveActions.
     *
     * @param moves       moves already calculated
     * @param player      moving player
     * @param workerIndex the index of the worker that's being moved
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void addMoves(List<MoveAction> moves, String player, int workerIndex, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that limit the possibility of movement, by removing MoveActions from the current list of
     * possible MoveActions.
     *
     * @param moves       moves already calculated
     * @param player      moving player
     * @param workerIndex the index of the worker that's being moved
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void removeMoves(List<MoveAction> moves, String player, int workerIndex, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that add additional build possibilities, by adding BuildActions to the current list of
     * possible BuildActions.
     *
     * @param builds      builds already calculated
     * @param player      building player
     * @param workerIndex the index of the worker that's being moved
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void addBuilds(List<BuildAction> builds, String player, int workerIndex, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that remove build possibilities, by removing BuildActions from the current list of
     * possible BuildActions.
     *
     * @param builds      builds already calculated
     * @param player      building player
     * @param workerIndex the index of the worker that's being moved
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void removeBuilds(List<BuildAction> builds, String player, int workerIndex, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that activate right after a player has moved.
     *
     * @param player      current turn player
     * @param workerIndex worker that has just moved
     * @param controller  current match controller reference
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void afterMove(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that activate right before a player moves, but after the start of the turn.
     *
     * @param player      current turn player
     * @param workerIndex worker that has just moved
     * @param controller  current match controller reference
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void beforeMove(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that activate right after a player has built.
     *
     * @param player      current turn player
     * @param workerIndex worker that has just built
     * @param controller  current match controller reference
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void afterBuild(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
    }

    /**
     * Implements god effects that activate right before a player builds something, but after they move.
     *
     * @param player      current turn player
     * @param workerIndex worker that has just built
     * @param controller  current match controller reference
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void beforeBuild(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
    }

    /**
     * Do something before the current player's turn starts, but after the previous player's turn ends.
     *
     * @param player     current turn player
     * @param controller current match controller reference
     * @param model      current match model reference
     * @throws IOException if there's a connection error
     */
    public void beforeTurn(String player, MatchController controller, MatchModel model) throws IOException {
    }

    /**
     * Do something after the current player's turn ends, but before the next player's turn starts.
     *
     * @param player      current turn player
     * @param workerIndex the index of the worker
     * @param controller  current match controller reference
     * @param model       current match model reference
     * @throws IOException if there's a connection error
     */
    public void afterTurn(String player, int workerIndex, MatchController controller, MatchModel model) throws IOException {
    }
}

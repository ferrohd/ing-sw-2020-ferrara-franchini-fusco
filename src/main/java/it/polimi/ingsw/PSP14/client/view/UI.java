package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.model.*;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This abstract class has to be implemented by
 * a CLI or GUI (for example).
 * It contains a set of abstract methods that have
 * to be specifically implemented by the CLI or GUI.
 * It contains a set of utility functions that
 */
public abstract class UI {
    /**
     * Cache works like a "local database" for storing
     * relevant data client-side in order to reduce
     * server traffic. It's protected so it will be accessible
     * to whom implements this class.
     */
    protected final UICache cache;
    private final Set<UIColor> assignedColors;
    private int playerNumber = 1;

    /**
     * Constructor of the UI:
     * It initialize the cache.
     */
    public UI() {
        cache = new UICache();
        assignedColors = new HashSet<>();
    }

    /**
     * Add a new player to the match by providing their username.
     * @param newPlayerUsername the username of the player you want to
     *                          register (you get this from the server)
     */
    public void registerPlayer(String newPlayerUsername) {
        UIColor _newPlayerColor = null;
        // Prevent duplicate colors
        while (_newPlayerColor == null ||
                assignedColors.contains(_newPlayerColor)) {
            _newPlayerColor = getColor(playerNumber);
        }
        assignedColors.add(_newPlayerColor);

        cache.addPlayer(newPlayerUsername, playerNumber++, _newPlayerColor);
    }

    /**
     * Remove a player from the match. This will remove the player and
     * all of its workers from this match.
     * @param username the username of the player you want
     *                 to remove
     */
    public void unregisterPlayer(String username) {
        cache.removePlayer(username);
    }

    /**
     * Set a player's worker in a target position.
     * <br/>
     * If the player does not own a worker with that <code>workerId</code>,
     * a new worker will be instantiated.
     * <br/>
     * You can use this method to perform moves: in that case, call first
     * {@link #unsetWorker(UIPoint)}.
     * @param position the target position
     * @param workerId the ID of the player's worker
     * @param playerUsername the owner of the worker
     */
    public void setWorker(UIPoint position, int workerId, String playerUsername) {
        UIPlayer _player = cache.getPlayer(playerUsername);
        UIWorker _worker = _player.getWorker(workerId);
        // Create a new worker if there isn't one
        if (_worker == null) {
            _worker = new UIWorker(workerId, _player);
        }
        cache.setWorker(_worker, playerUsername, cache.getCell(position));
    }

    /**
     * Remove a worker from a specific position on the board,
     * if there's any.<br/>
     * You want to call this before performing a move.
     * @param position the position of the worker
     */
    public void unsetWorker(UIPoint position) {
        UIWorker _w = cache.getCell(position).getWorker();
        cache.unsetWorker(_w);
    }

    /**
     * Remove a worker from a specific position.
     * @see #unsetWorker(UIPoint)
     */
    public void unsetWorker(int workerId, String playerUsername) {
        UIPlayer _player = cache.getPlayer(playerUsername);
        cache.unsetWorker(_player.getWorker(workerId));
    }

    /**
     * Increment the tower height at the specified cell position by 1.
     * @param position position
     */
    public void incrementCell(UIPoint position) {
        cache.getCell(position).incrementTowerHeight();
    }

    /**
     * Decrement the tower height at the specified cell position by 1.
     * @param position the position
     */
    public void decrementCell(UIPoint position) {
        cache.getCell(position).decrementTowerHeight();
    }

    /**
     * Add a dome (marking a tower as complete) to the specified cell position.
     * @param position the position
     */
    public void setDome(UIPoint position) {
        cache.getCell(position).setDome(true);
    }

    /**
     * Remove a dome (un-completing a tower) from the specified cell position.
     * @param position the position
     */
    public void unsetDome(UIPoint position) {
        cache.getCell(position).setDome(false);
    }

    /*
     * Commands used by the Command Pattern
     * (these methods will get called by the ProposalMessages
     * that are executable and are sent by the server)
     */

    /**
     * Calls an update of the UI implementation.
     * Use this to display changes in data.
     */
    public abstract void update();

    /**
     * Get a color depending on the player unique id.
     * @param playerNumber an int between <code>1</code> and <code>3</code>
     * @return a color
     */
    public abstract UIColor getColor(int playerNumber);

    /**
     * Display a greeting to the player.
     */
    public abstract void welcome() throws InterruptedException;

    /**
     * Ask the player for how many players should participate in a match.
     *
     * @return the size of the lobby
     */
    public abstract int getLobbySize() throws InterruptedException;

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    public abstract void showNotification(String s);

    /**
     * Prompt the player to provide a username
     *
     * @return the chosen username
     */
    public abstract String askUsername() throws InterruptedException;

    /**
     * Prompt the player to select a god from the list of available gods.
     *
     * @return the index of the chosen god.
     */
    public abstract int chooseGod(List<GodProposal> proposals);

    /**
     * Ask the player to choose another player (even themselves) from a list.
     *
     * @param proposals the list of players to choose from
     * @return the index of the chosen player
     */
    public abstract int chooseFirstPlayer(List<PlayerProposal> proposals);

    /**
     * Ask the player to choose a worker from a list
     *
     * @return the index of the chosen worker
     */
    public abstract int chooseWorker(List<Integer> choosable);

    /**
     * Ask the (hosting) player to choose a god that will be added to a pool
     * from which the players will be able to choose from before a game start.
     *
     * @param gods a list of the available gods
     * @return the index of the chosen god
     */
    public abstract int chooseAvailableGods(List<GodProposal> gods);

    /**
     * Ask a player for the starting position of one of their workers.
     *
     * @return A tuple [x,y] of the worker coordinates
     */
    public abstract int[] chooseWorkerInitialPosition();


    /**
     * Ask a player for the destination of their next move.
     * @param moves a list of possible moves to choose from
     * @return the Index of the chosen move
     */
    public abstract int chooseMove(List<MoveProposal> moves);

    /**
     * Ask a player for the destination of their next build action,
     * where the next tower block will be built if possible.
     * @param moves a list of options to choose from
     * @return the Index of the chosen option
     */
    public abstract int chooseBuild(List<BuildProposal> moves);

    /**
     * Ask the player if they want to perform a certain action.
     * This handles only the confirm (positive or negative).
     * @param message the text to display
     * @return 0 = no, 1 = yes
     */
    public abstract int chooseYesNo(String message);
}

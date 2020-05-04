package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.model.*;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.util.List;

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

    /**
     * Constructor of the UI:
     * It initialize the cache.
     */
    public UI() {
        cache = new UICache();
    }

    /**
     * Add a new player to the match.
     * @param newPlayerUsername the username of the player you want to
     *                          register (you get this from the server)
     */
    public void registerPlayer(String newPlayerUsername) {
        UIColor _newPlayerColor = getColor();
        // TODO: Prevent duplicate colors

        cache.addPlayer(newPlayerUsername, _newPlayerColor);
    }

    /**
     * Remove a player from the match.
     * @param username the username of the player you want
     *                 to remove
     */
    public void unregisterPlayer(String username) {
        cache.removePlayer(username);
    }

    /**
     * Set a player's worker in a specific position.
     * @param position the new position of the worker
     * @param workerId the ID of the player's worker
     * @param playerUsername the owner of the worker
     */
    public void setWorker(UIPoint position, int workerId, String playerUsername) {
        UIPlayer _player = cache.getPlayer(playerUsername);
        UIWorker _worker = _player.getWorker(workerId);
        cache.getCell(position).setWorker(_worker);
    }

    /**
     * Remove any worker from a specific position.
     * @param position the position of the worker
     */
    public void unsetWorker(UIPoint position) {
        cache.getCell(position).unsetWorker();
    }

    /**
     * Remove any worker from a specific position.
     */
    public void unsetWorker(int workerId, String playerUsername) {
        UIPlayer _player = cache.getPlayer(playerUsername);
        _player.unsetWorker(workerId);
    }

    /**
     * Move a worker from a position to another
     * @param oldPos the starting position
     * @param newPos the final position
     */
    public void moveWorker(Point startPosition, Point endPosition) {
        UIPlayer _player = cache.getCell(startPosition).getWorker();
        unsetWorker(startPosition);
        uns(endPosition, player);
    }

    /**
     * Add a block to the specified cell position.
     * @param pos position
     */
    public void drawBlockAdd(Point pos) {
        cache.getBlock(pos).incrementSize();
    }
    /**
     * Remove a block from the specified cell position.
     * @param pos the position
     */
    public void drawBlockRemove(Point pos) {
        cache.getBlock(pos).decrementSize();
    }
    /**
     * Add a dome (completing a tower) to the specified cell position.
     * @param pos the position
     */
    public void drawDomeAdd(Point pos) {
        cache.getBlock(pos).setDome();
    }
    /**
     * Remove a dome (completing a tower) from the specified cell position.
     * @param pos the position
     */
    public void drawDomeRemove(Point pos) {
        cache.getBlock(pos).unsetDome();
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
     * Generate a random UIColor
     * @return a color
     */
    public abstract UIColor getColor();

    /**
     * Display a greeting to the player.
     */
    public abstract void welcome();

    /**
     * Display on the UI that a player is trying to connect to a server
     *
     * @param hostname the address of the server
     * @param port     the port of the server
     */
    public abstract void notifyConnection(String hostname, int port);

    /**
     * Ask the player for how many players should participate in a match.
     *
     * @return the size of the lobby
     */
    public abstract int getLobbySize();

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    public abstract void notify(String s);

    /**
     * Prompt the player to provide a username
     *
     * @return the chosen username
     */
    public abstract String askUsername();

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
    public abstract int chooseWorker();

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
}

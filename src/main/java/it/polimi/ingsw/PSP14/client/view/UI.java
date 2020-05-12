package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UI {
    private UICache cache;
    private Map<String, Color> colorMap;

    public UI() {
        cache = new UICache();
        colorMap = new HashMap<>();
    }

    protected UICache getCache() { return cache; }
    protected Map<String, Color> getColorMap() { return colorMap; }

    public abstract void update();
    abstract Color getColor();

    /**
     * @param newPlayer new player to be registered in the view
     */
    public void registerPlayer(String newPlayer) {
        colorMap.put(newPlayer, getColor());
    }

    /**
     * @param removedPlayer existing player to be removed from the view
     */
    public void removePlayer(String removedPlayer) {
        colorMap.remove(removedPlayer);
    }

    /**
     * Set a player's worker in a specific position.
     * @param pos the position of the worker
     * @param player the owner of the worker
     */
    public void drawWorkerSet(Point pos, String player) {
        cache.getBlock(pos).setWorker(player);
    }

    /**
     * Remove any worker from a specific position.
     * @param pos the position of the worker
     */
    public void drawWorkerUnset(Point pos) {
        cache.getBlock(pos).unsetWorker();
    }

    /**
     * Move a worker from a position to another
     * @param oldPos the starting position
     * @param newPos the final position
     */
    public void drawWorkerMove(Point oldPos, Point newPos) {
        String player = cache.getBlock(oldPos).getWorker();
        drawWorkerUnset(oldPos);
        drawWorkerSet(newPos, player);
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

    /**
     * Display a greeting to the player.
     */
    public abstract void welcome() throws InterruptedException;

    /**
     * Display on the UI that a player is trying to connect to a server
     * @param hostname the address of the server
     * @param port the port of the server
     */
    public abstract void notifyConnection(String hostname, int port);

    /**
     * Ask the player for how many players should participate in a match.
     * @return the size of the lobby
     */
    public abstract int getLobbySize() throws InterruptedException;

    /**
     * Display a notification to the player.
     * @param s the content of the notification
     */
    public abstract void notify(String s);

    /**
     * Prompt the player to provide a username
     * @return the chosen username
     */
    public abstract String askUsername();

    /**
     * Prompt the player to select a god from the list of available gods.
     * @return the index of the chosen god.
     */
    public abstract int chooseGod(List<GodProposal> proposals);

    /**
     * Ask the player to choose another player (even themselves) from a list.
     * @param proposals the list of players to choose from
     * @return the index of the chosen player
     */
    public abstract int chooseFirstPlayer(List<PlayerProposal> proposals);

    /**
     * Ask the player to choose a worker from a list
     * @return the index of the chosen worker
     */
    public abstract int chooseWorker();

    public abstract int chooseAvailableGods(List<GodProposal> gods);

    public abstract int[] chooseWorkerInitialPosition();
}

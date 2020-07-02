package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.client.view.cli.UIColor;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.List;

/**
 * This abstract class has to be implemented by
 * a CLI or GUI (for example).
 * It contains a set of abstract methods that have
 * to be specifically implemented by the CLI or GUI.
 * It contains a set of utility functions that
 */
public interface UI {

    void gameStart() throws InterruptedException;

    /**
     * Add a new player to the match by providing their username.
     *
     * @param newPlayerUsername the username of the player you want to
     *                          register (you get this from the server)
     */
    void registerPlayer(String newPlayerUsername);

    /**
     * Remove a player from the match. This will remove the player and
     * all of its workers from this match.
     *
     * @param username the username of the player you want
     *                 to remove
     * @throws InterruptedException if interrupted
     */
    void unregisterPlayer(String username) throws InterruptedException;

    /**
     * Ask the player to choose a worker to move
     *
     * @param player the player that has to choose the worker
     * @throws InterruptedException if interrupted
     */
    void startWorkerChoice(String player) throws InterruptedException;

    /**
     * Ask the player where they want to move the worker
     *
     * @param player the player that has to choose where to move
     * @throws InterruptedException if interrupted
     */
    void startMove(String player) throws InterruptedException;

    /**
     * Ask the player where they want to build
     *
     * @param player the player that has to choose where to build
     * @throws InterruptedException if interrupted
     */
    void startBuild(String player) throws InterruptedException;

    /**
     * Ask the player where to position the workers at the start of the match
     *
     * @param player the player that has to place the workers
     * @throws InterruptedException if interrupted
     */
    void startWorkerPlacement(String player) throws InterruptedException;

    /**
     * Notify the players that there's a winner
     *
     * @param winner the winner's name
     * @throws InterruptedException if interrupted
     */
    void showVictory(String winner) throws InterruptedException;

    /**
     * Set a player's worker in a target position.
     * If the player does not own a worker with that <code>workerId</code>,
     * a new worker will be instantiated.
     *
     * @param position       the target position
     * @param workerId       the ID of the player's worker
     * @param playerUsername the owner of the worker
     * @throws InterruptedException if interrupted
     */
    void setWorker(Point position, int workerId, String playerUsername) throws InterruptedException;

    /**
     * Move a worker of a player to a new position
     *
     * @param newPosition    the target destination of the worker
     * @param workerId       the worker to move
     * @param playerUsername the worker's owner
     * @throws InterruptedException if interrupted
     */
    void moveWorker(Point newPosition, int workerId, String playerUsername) throws InterruptedException;

    /**
     * Increment the tower height at the specified cell position by 1.
     *
     * @param position position
     * @throws InterruptedException if interrupted
     */
    void incrementCell(Point position) throws InterruptedException;

    /**
     * Add a dome (marking a tower as complete) to the specified cell position.
     *
     * @param position the position
     * @throws InterruptedException if interrupted
     */
    void setDome(Point position) throws InterruptedException;

    /*
     * Commands used by the Command Pattern
     * (these methods will get called by the ProposalMessages
     * that are executable and are sent by the server)
     */

    /**
     * Calls an update of the UI implementation.
     * Use this to display changes in data.
     */
    void update();

    /**
     * Get a color depending on the player unique id.
     *
     * @param playerNumber an int between <code>1</code> and <code>3</code>
     * @return a color
     */
    UIColor getColor(int playerNumber);

    /**
     * Display a greeting to the player.
     *
     * @throws InterruptedException if interrupted
     */
    void welcome() throws InterruptedException;

    /**
     * Ask the host how many players should participate in the match.
     *
     * @return the size of the lobby
     * @throws InterruptedException if interrupted
     */
    int getLobbySize() throws InterruptedException;

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    void showNotification(String s);

    /**
     * Prompt the player to provide a username.
     *
     * @return the chosen username
     * @throws InterruptedException if interrupted
     */
    String askUsername() throws InterruptedException;

    /**
     * Prompt the player to select a god from the list of available gods.
     *
     * @param proposals the list of available gods
     * @return the index of the chosen god
     * @throws InterruptedException if interrupted
     */
    int chooseGod(List<GodProposal> proposals) throws InterruptedException;

    /**
     * Ask the player to choose another player (even themselves) from a list.
     *
     * @param proposals the list of players to choose from
     * @return the index of the chosen player
     * @throws InterruptedException if interrupted
     */
    int chooseFirstPlayer(List<PlayerProposal> proposals) throws InterruptedException;

    /**
     * Ask the player to choose a worker from a list
     *
     * @param choices the list of workers
     * @return the index of the chosen worker
     * @throws IOException          if it fails to communicate with the server
     * @throws InterruptedException if interrupted
     */
    int chooseWorker(List<Integer> choices) throws InterruptedException, IOException;

    /**
     * Ask the (hosting) player to choose a god that will be added to a pool
     * from which the players will be able to choose from before a game start.
     *
     * @param gods a list of the available gods
     * @return the index of the chosen god
     * @throws InterruptedException if interrupted
     */
    int chooseAvailableGods(List<GodProposal> gods) throws InterruptedException;

    /**
     * Ask a player for the starting position of one of their workers.
     *
     * @return A tuple [x,y] of the worker coordinates
     * @throws InterruptedException if interrupted
     */
    int[] chooseWorkerInitialPosition() throws InterruptedException;


    /**
     * Ask a player for the destination of their next move.
     *
     * @param moves a list of possible moves to choose from
     * @return the Index of the chosen move
     * @throws IOException          if it fails to communicate with the server
     * @throws InterruptedException if interrupted
     */
    int chooseMove(List<MoveProposal> moves) throws InterruptedException, IOException;

    /**
     * Ask a player for the destination of their next build action,
     * where the next tower block will be built if possible.
     *
     * @param moves a list of options to choose from
     * @return the Index of the chosen option
     * @throws IOException          if it fails to communicate with the server
     * @throws InterruptedException if interrupted
     */
    int chooseBuild(List<BuildProposal> moves) throws InterruptedException, IOException;

    /**
     * Ask the player if they want to perform a certain action.
     * This handles only the confirm (positive or negative).
     *
     * @param message the text to display
     * @return 0 = no, 1 = yes
     * @throws InterruptedException if interrupted
     */
    int chooseYesNo(String message) throws InterruptedException;

    /**
     * This method is called when a player has been assigned a god.
     *
     * @param player the player's username
     * @param god    the name/id of the god
     */
    void updateGod(String player, String god);
}

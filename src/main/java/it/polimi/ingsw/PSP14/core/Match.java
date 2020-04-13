package it.polimi.ingsw.PSP14.core;

import it.polimi.ingsw.PSP14.core.*;
import it.polimi.ingsw.PSP14.core.actions.Action;

import java.util.*;

/**
 * Model for a single match, server side.
 * The MatchModel contains references to the clients' connections.
 */
public class Match {
    private HashMap<String, Player> players = new HashMap<>();
    private Board board;
    private List<Action> history;

    /**
     * Constructor of MatchModel.
     * Since players don't require any previous data except for username,
     * the order of setup doesn't matter.
     * @param usernames a Set of each player's username
     */
    public Match(Set<String> usernames) {
        // Init players
        for (String username : usernames) {
            players.put(username, new Player(username));
        }
        // Init board
        this.board = new Board();
        // Init history
        history = new ArrayList<>();
    }

    public void addActionToHistory(Action action) {
        history.add(action);
    }

    public List<Action> getHistory() {
        return history;
    }

    /**
     * Retrieve a Player object by username.
     * @param username the username of the Player
     * @return the associated Player
     * @throws PlayerNotFoundException when the player is not found
     */
    private Player getPlayerByUsername(String username) throws PlayerNotFoundException {
        Player player = players.get(username);
        if (player == null)
            throw new PlayerNotFoundException();
        else return player;
    }

    public Board getBoard() {
        return board;
    }

    private ArrayList<Point> getWorkerPositions() {
        ArrayList<Point> workerPositions = new ArrayList<>();
        for(Player p : players.values())
            for(int i = 0; i < 2; ++i)
                workerPositions.add(p.getWorker(i).getPos());

        return workerPositions;
    }

    /**
     * @param player player to move
     * @param worker index of worker to move
     * @return an array of Points to move to
     * @throws PlayerNotFoundException if the player given is not playing
     */
    public ArrayList<Point> getMovements(String player, int worker) throws PlayerNotFoundException {
        ArrayList<Point> legalPositions = new ArrayList<>();

        ArrayList<Point> workerPositions = getWorkerPositions();

        Point currentPos = getPlayerByUsername(player).getWorker(worker).getPos();
        int currentLevel = board.getCell(currentPos).getTowerSize();
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            int toCheckLevel = board.getCell(toCheckPos).getTowerSize();
            if(!workerPositions.contains(toCheckPos) &&
                    toCheckLevel <= currentLevel+1 &&
                    !board.getCell(toCheckPos).getIsCompleted())
                legalPositions.add(toCheckPos);
        }

        return legalPositions;
    }

    /**
     * @param player player who builds
     * @param worker index of worker who builds
     * @return an array of Points where building is possible (including dome-building)
     * @throws PlayerNotFoundException if the player given is not playing
     */
    public ArrayList<Point> getBuildable(String player, int worker) throws PlayerNotFoundException {
        ArrayList<Point> buildablePositions = new ArrayList<>();

        ArrayList<Point> workerPositions = getWorkerPositions();

        Point currentPos = getPlayerByUsername(player).getWorker(worker).getPos();
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            if(!workerPositions.contains(toCheckPos) && !board.getCell(toCheckPos).getIsCompleted())
                buildablePositions.add(toCheckPos);
        }

        return buildablePositions;
    }
}

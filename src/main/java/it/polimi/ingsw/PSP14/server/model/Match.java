package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.server.actions.Action;
import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.gods.God;

import java.util.*;
import java.util.stream.Collectors;

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
            players.put(username, new Player(username, new God()));
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

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    /**
     * Retrieve a Player object by username.
     * @param username the username of the Player
     * @return the associated Player
     * @throws PlayerNotFoundException when the player is not found
     */
    private Player getPlayerByUsername(String username){
        return players.get(username);
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Retrieve the position of all workers (of all players).
     * @return an array containing the workers' positions
     */
    public ArrayList<Point> getWorkerPositions() {
        ArrayList<Point> workerPositions = new ArrayList<>();
        for(Player p : players.values())
            for(int i = 0; i < 2; ++i)
                workerPositions.add(p.getWorker(i).getPos());

        return workerPositions;
    }

    /**
     * Retrieve a list of the possible moves a player can do.
     * @param player player to move
     * @param worker index of worker to move
     * @return an array of Points to move to
     */
    public List<MoveAction> getMovements(String player, int worker) {
        ArrayList<Point> legalPositions = new ArrayList<>();

        ArrayList<Point> workerPositions = getWorkerPositions();
        Player currPlayer = getPlayerByUsername(player);

        Point currentPos = currPlayer.getWorker(worker).getPos();
        int currentLevel = board.getCell(currentPos).getTowerSize();
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            int toCheckLevel = board.getCell(toCheckPos).getTowerSize();
            if(!workerPositions.contains(toCheckPos) && toCheckLevel <= currentLevel+1 &&
                    !board.getCell(toCheckPos).getIsCompleted() && Board.isValidPos(toCheckPos))
                legalPositions.add(toCheckPos);
        }

        players.values().forEach(p -> p.getGod().addMoves(legalPositions, currPlayer, currPlayer.getWorker(worker), this));
        //players.values().forEach(p -> p.getGod().removeMoves(legalPositions, currPlayer, this));

        List<MoveAction> legalActions = legalPositions.stream().map(p -> new MoveAction(player, currentPos, p)).collect(Collectors.toList());

        return legalActions;
    }

    /**
     * @param player player who builds
     * @param worker index of worker who builds
     * @return an array of Points where building is possible (including dome-building)
     * @throws PlayerNotFoundException if the player given is not playing
     */
    public List<BuildAction> getBuildable(String player, int worker) throws PlayerNotFoundException {
        ArrayList<Point> buildablePositions = new ArrayList<>();

        ArrayList<Point> workerPositions = getWorkerPositions();

        Point currentPos = getPlayerByUsername(player).getWorker(worker).getPos();
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            if(!workerPositions.contains(toCheckPos) && !board.getCell(toCheckPos).getIsCompleted())
                buildablePositions.add(toCheckPos);
        }

        List<BuildAction> buildActions = buildablePositions.stream().map(p -> new BuildAction(player, p, board.getCell(p).getTowerSize() == 3)).collect(Collectors.toList());

        return buildActions;
    }
}

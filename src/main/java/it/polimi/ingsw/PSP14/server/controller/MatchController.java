package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The core of the match. It exposes a series of methods that allows the game to change state.
 * Works in tandem with {@link it.polimi.ingsw.PSP14.server.model.MatchModel}.
 */
public class MatchController {
    private final List<String> users = new ArrayList<>(3);
    private final Map<String, ClientConnection> connections = new HashMap<>(3);

    /**
     * Main constructor of the class. On creation asks all clients for their username.
     *
     * @param clientConnections list of clientConnections
     * @throws IOException if a communication error occurs
     */
    public MatchController(List<ClientConnection> clientConnections) throws IOException {
        for (ClientConnection c : clientConnections)
            c.sendNotification("Game started! You will be asked to insert your username soon");
        for (ClientConnection connection : clientConnections) {
            String username = connection.getUsername();
            while (users.contains(username)) {
                connection.sendNotification("Name already chosen!");
                username = connection.getUsername();
            }
            connections.put(username, connection);
            users.add(username);
        }
    }

    /**
     * Creates a dummy instance with no clientConnections associated.
     *
     * @throws IOException if a communication error occurs
     */
    public MatchController() throws IOException {
        this(new ArrayList<>());
    }

    public List<String> getUsers() {
        return users;
    }

    /**
     * Closes all connections.
     */
    public void closeConnections() {
        for (ClientConnection c : connections.values()) {
            try {
                c.close();
            } catch (IOException e) {
                System.out.println("Error closing connection!");
            }
        }
    }

    /**
     * Asks the roomMaster for the gods to be played in the game and returns their names.
     *
     * @param roomMaster the name of the player who is roomMaster
     * @return a list of names of gods
     * @throws IOException on communication errors
     */
    public List<String> getMatchGods(String roomMaster) throws IOException {
        List<String> availableGods = GodfileParser.getGodIdList(getClass().getClassLoader().getResourceAsStream("gods/godlist.xml"), users.size());
        ClientConnection masterConnection = connections.get(roomMaster);
        for (ClientConnection c : connections.values())
            if (!c.equals(masterConnection))
                c.sendNotification(roomMaster + " (room leader) is choosing the gods of the game.");

        return masterConnection.selectGameGods(new ArrayList<>(availableGods), users.size());
    }

    /**
     * Asks a client to choose their god and returns the god's name.
     *
     * @param player        the name of the choosing player
     * @param availableGods list of available god names
     * @return the name of the chosen god
     * @throws IOException on communication errors
     */
    public String chooseGod(String player, List<String> availableGods) throws IOException {
        ClientConnection conn = connections.get(player);
        for (ClientConnection c : connections.values())
            if (!c.equals(conn)) c.sendNotification(player + " is choosing their god.");
        String chosenGod = conn.selectGod(availableGods);
        for (ClientConnection c : connections.values()) c.notifyGod(player, chosenGod);

        return chosenGod;
    }

    /**
     * Asks the roomMaster to choose who goes first among a list of player names.
     *
     * @param roomMaster the name of the room master
     * @param players    list containing all player names
     * @return the chosen first player
     * @throws IOException on communication errors
     */
    public String chooseFirstPlayer(String roomMaster, List<String> players) throws IOException {
        ClientConnection masterConnection = connections.get(roomMaster);

        for (ClientConnection c : connections.values())
            if (!c.equals(masterConnection))
                c.sendNotification(roomMaster + " (room leader) is choosing who goes first.");

        String firstPlayer = masterConnection.selectFirstPlayer(users);

        for (ClientConnection c : connections.values())
            if (!c.equals(masterConnection))
                c.sendNotification(firstPlayer + " is first!");

        return firstPlayer;
    }

    /**
     * Notifies all player that the game has started (after god and first player selection).
     *
     * @throws IOException on communication errors
     */
    public void startGame() throws IOException {
        for (ClientConnection c : connections.values()) c.notifyGameStart();
    }

    /**
     * Asks a player where to place their worker, taking into account already occupied slots.
     *
     * @param player the choosing player
     * @param busy   list of occupied cells (as points)
     * @return the chosen position
     * @throws IOException on communication errors
     */
    public Point chooseWorkerPosition(String player, List<Point> busy) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyWorkerPlacementPhase(player);
        ClientConnection conn = connections.get(player);
        Point pos = conn.placeWorker();
        while (busy.stream().anyMatch(pos::equals)) {
            conn.sendNotification("Cell busy!");
            pos = conn.placeWorker();
        }

        return pos;
    }

    /**
     * Notifies all players that a new players has registered.
     *
     * @param player the name of the new player
     * @throws IOException on communication errors
     */
    public void registerPlayer(String player) throws IOException {
        for (ClientConnection c : connections.values()) c.registerPlayer(player);
    }

    /**
     * Notifies all clients that a worker has moved.
     *
     * @param pos         the landing position
     * @param player      the moving player
     * @param workerIndex the moving worker
     * @throws IOException on communication errors
     */
    public void notifyWorkerMove(Point pos, String player, int workerIndex) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyWorkerMove(pos, player, workerIndex);
    }

    /**
     * Notifies all clients that a worker has been placed.
     *
     * @param pos         the position
     * @param player      the placing player
     * @param workerIndex the placed worker
     * @throws IOException on communication errors
     */
    public void registerWorker(Point pos, String player, int workerIndex) throws IOException {
        for (ClientConnection c : connections.values()) c.registerWorker(pos, player, workerIndex);
    }

    /**
     * Notifies all clients that a player has to be removed from the board.
     *
     * @param player the player to remove
     * @throws IOException on communication errors
     */
    public void notifyUnregisterPlayer(String player) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyUnregisterPlayer(player);
    }

    /**
     * Notifies all clients that a tower block has been built.
     *
     * @param pos the position where the building occurred
     * @throws IOException on communication errors
     */
    public void notifyBuild(Point pos) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyBuild(pos);
    }

    /**
     * Notifies all clients that a dome has been built.
     *
     * @param pos the position where the dome was built
     * @throws IOException on communication errors
     */
    public void notifyDome(Point pos) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyDome(pos);
    }

    /**
     * Notifies all players that the turn of another player has started (except for the playing player).
     *
     * @param player the playing player
     * @throws IOException on communication errors
     */
    public void startTurn(String player) throws IOException {
        for (ClientConnection c : connections.values())
            if (!c.equals(connections.get(player)))
                c.sendNotification("The turn of " + player + " has begun.");
    }

    /**
     * Asks a player to select the worker to move.
     *
     * @param player         the choosing player
     * @param movableWorkers list of integer worker indexes who can move
     * @return the chosen worker id
     * @throws IOException on communication errors
     */
    public int getWorkerIndex(String player, List<Integer> movableWorkers) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyWorkerChoicePhase(player);

        return connections.get(player).getWorkerIndex(movableWorkers);
    }

    /**
     * Asks a player to choose between a list of MoveActions
     *
     * @param player    the choosing player
     * @param movements list of possible MoveActions
     * @return the chosen MoveAction
     * @throws IOException on communication errors
     */
    public MoveAction askMove(String player, List<MoveAction> movements) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyMovePhase(player);
        List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());
        int choice = connections.get(player).askMove(moveProposals);

        return movements.get(choice);
    }

    /**
     * Asks a player to choose between a list of BuildAction
     *
     * @param player the choosing player
     * @param builds list of possible BuildAction
     * @return the chosen BuildAction
     * @throws IOException on communication errors
     */
    public BuildAction askBuild(String player, List<BuildAction> builds) throws IOException {
        for (ClientConnection c : connections.values()) c.notifyBuildPhase(player);
        List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());
        int choice = connections.get(player).askBuild(buildProposals);

        return builds.get(choice);
    }

    /**
     * Notifies all player that a player has won the match.
     *
     * @param winningPlayer the winning player
     * @throws IOException on communication errors
     */
    public void endGame(String winningPlayer) throws IOException {
        for (ClientConnection c : connections.values()) c.endGame(winningPlayer);
    }

    /**
     * Notifies all players that a player has lost the match.
     *
     * @param losingPlayer the losing player
     * @throws IOException on communication errors
     */
    public void lose(String losingPlayer) throws IOException {
        for (ClientConnection c : connections.values()) c.sendNotification(losingPlayer + " lost");
    }

    /**
     * Asks a question to a specific player. The player can respond with either yes or no.
     *
     * @param player  the player who has to respond
     * @param message the text message containing the question
     * @return a boolean indicating the answer (yes -> true, no -> false)
     * @throws IOException
     */
    public boolean askQuestion(String player, String message) throws IOException {
        return connections.get(player).askQuestion(message);
    }
}

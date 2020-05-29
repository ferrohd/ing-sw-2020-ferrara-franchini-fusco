package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.GodfileParser;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Board;
import it.polimi.ingsw.PSP14.server.model.board.Direction;
import it.polimi.ingsw.PSP14.server.model.board.Player;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.gods.God;
import it.polimi.ingsw.PSP14.server.model.gods.GodFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Model for a single match, server side.
 * The MatchModel contains references to the clients' connections.
 */
public class Match implements Runnable {
    private final Board board;
    private final List<Action> history = new ArrayList<>();

    private final List<String> users = new ArrayList<>();
    private final List<ClientConnection> clientConnections = new ArrayList<>();
    private final HashMap<String, Player> players = new HashMap<>();
    private final Map<String, ClientConnection> clients = new HashMap<>();
    private final Map<String, God> gods = new HashMap<>();

    /**
     * Constructor of Match.
     * Since players don't require any previous data except for username,
     * the order of setup doesn't matter.
     */
    public Match(List<ClientConnection> clientConnections) throws IOException {
        this.clientConnections.addAll(clientConnections);
        board = new Board(clientConnections);
    }

    /**
     * Entry point for MatchController logic.
     */
    @Override
    public void run() {
        try {
            setupGame();
            gameLoop();
        } catch (EndGameException e) {
            System.out.println("Game is over. Terminating...");
        } catch(IOException e) {
            System.out.println("Connection error!");
            for(ClientConnection c : clientConnections) {
                try {
                    c.close();
                } catch (IOException ioe) {
                    System.out.println("Could not close connection.");
                }
            }
        }

    }

    /**
     * Helper function to setup the game. Does the following things:
     * - gets usernames from players
     * - asks the room leader to choose the gods of the game
     * - asks each player to choose their god
     * - asks the room leader to choose the game leader
     * - asks each player to place their worker (twice)
     * @throws IOException
     */
    private void setupGame() throws IOException {
        List<String> availableGods;
        List<String> selectedGods;

        for (ClientConnection connection : clientConnections) {
            String username = connection.getUsername();
            while(users.contains(username)) {
                connection.sendNotification("Name already chosen!");
                username = connection.getUsername();
            }
            clients.put(username, connection);
            users.add(username);
        }


        availableGods = GodfileParser.getGodIdList("src/main/resources/gods/godlist.xml", users.size());
        ClientConnection roomMaster = clients.get(users.get(0));
        for(ClientConnection c : clientConnections) if(!c.equals(roomMaster)) c.sendNotification(users.get(0) + " (room leader) is choosing the gods of the game.");
        selectedGods = roomMaster.selectGameGods(new ArrayList<>(availableGods), users.size());

        // roomMaster is last to choose
        Collections.rotate(users, -1);
        for (String p : users) {
            ClientConnection player = clients.get(p);
            for(ClientConnection c : clientConnections) if(!c.equals(player)) c.sendNotification(p + " is choosing their god.");
            String chosenGod = player.selectGod(selectedGods);
            gods.put(p, GodFactory.getGod(chosenGod, p));
            selectedGods.remove(chosenGod);
        }
        Collections.rotate(users, 1);

        for(ClientConnection c : clientConnections) if(!c.equals(roomMaster)) c.sendNotification(users.get(0) + " (room leader) is choosing who goes first.");
        String firstPlayer = roomMaster.selectFirstPlayer(users);
        Collections.rotate(users, -users.indexOf(firstPlayer));
        for(ClientConnection c : clientConnections) if(!c.equals(roomMaster)) c.sendNotification(users.get(0) + " is first!");

        for(String p : users) {
            players.put(p, new Player(p, gods.get(p), clientConnections));
        }

        playersPlaceWorkers();
    }

    /**
     * Main gameloop function.
     *
     * Consists of an infinite loop that plays the turn indefinitely until either a connection error occurs
     * or a end game event is detected.
     */
    private void gameLoop() throws IOException, EndGameException {
        while(true)
            for(String p: users)
                turn(p);
    }

    /**
     * Helper function of setupGame. Asks all players to place their workers
     * @throws IOException a connection error occurs
     */
    private void playersPlaceWorkers() throws IOException {
        List<String> busyPositions = new ArrayList<>();

        for (String p : users) {
            for(int i = 0; i < 2; ++i) {
                ClientConnection connection = clients.get(p);
                for(ClientConnection c : clientConnections) if(!c.equals(connection)) c.sendNotification(p + " is placing their workers.");
                Point pos = connection.placeWorker();
                while(busyPositions.contains(pos.toString())) {
                    connection.sendNotification("Cell busy!");
                    pos = connection.placeWorker();
                }
                getPlayerByUsername(p).setWorker(i, pos);
                for (ClientConnection c : getClientConnections())
                    c.registerWorker(pos, i, p);
                busyPositions.add(pos.toString());
            }
        }
    }

    public List<ClientConnection> getClientConnections() {
        return new ArrayList<>(clientConnections);
    }

    public List<Action> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * @return the last action executed
     */
    public Action getLastAction() { return history.get(history.size()-1); }

    /**
     * Executes an action on the Match and adds it to the history upon completion
     * @param action the action to execute
     * @throws IOException if a connection error occurs
     */
    public void executeAction(Action action) throws IOException {
        action.execute(this);
        history.add(action);
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    private void applyBeforeTurnEffects(String player, ClientConnection client) throws IOException {
        for(Player p : getPlayers())
            p.getGod().beforeTurn(player, client, this);
    }

    private void applyAfterTurnEffects(String player, int workerIndex, ClientConnection client) throws IOException {
        for(Player p : getPlayers())
            p.getGod().afterTurn(player, workerIndex, client, this);
    }

    /**
     * Main game logic functions. Executes the following:
     * - calls the beforeTurn effects of all gods
     * - asks the player for the worker to move and build
     * - executes the move and build phases
     * - calls the afterTurn effects of all gods
     * @param player the player of the turn
     * @throws IOException if a connection error occurs
     */
    private void turn(String player) throws IOException {
        ClientConnection client = clients.get(player);
        for(ClientConnection c : clientConnections) if(!c.equals(client)) c.sendNotification("The turn of " + player + " has begun.");

        applyBeforeTurnEffects(player, client);

        List<Integer> movableWorkers = getMovableWorkers(player);
        if(movableWorkers.size() == 0) {
            lose(player);
            return;
        }
        int workerIndex = client.getWorkerIndex(movableWorkers);

        move(player, client, workerIndex);
        build(player, client, workerIndex);

        applyAfterTurnEffects(player, workerIndex, client);
    }

    private List<Integer> getMovableWorkers(String player) {
        List<Integer> workerIndexes = new ArrayList<>();
        for(int i = 0; i < 2; ++i) {
            if(getMovements(player, i).size() > 0) {
                workerIndexes.add(i);
            }
        }

        return workerIndexes;
    }

    /**
     * Function that implements all logic relative to the move phase. In order:
     * - gets all valid move actions
     * - gets the choice from the client
     * - executes the chosen action
     * - calls the afterMove effects of all gods
     * @param player the moving player
     * @param client the client relative to the moving player
     * @param workerIndex the index of the moving worker
     * @throws IOException if a connection error occurs
     */
    public void move(String player, ClientConnection client, int workerIndex) throws IOException {
        for(ClientConnection c : clientConnections) if(!c.equals(client)) c.sendNotification(player + " is moving...");
        for(Player p : getPlayers())
            p.getGod().beforeMove(player, workerIndex, client, this);

        List<MoveAction> movements = getMovements(player, workerIndex);
        List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());

        int choice = client.askMove(moveProposals);

        Action action = movements.get(choice);
        executeAction(action);

        for(Player p : getPlayers())
            p.getGod().afterMove(player, workerIndex, client, this);
    }

    /**
     * Function that implements all logic relative to the build phase. In order:
     * - gets all valid build actions
     * - gets the choice from the client
     * - executes the chosen action
     * - calls the afterBuild effects of all gods
     * @param player the building player
     * @param client the client relative to the building player
     * @param workerIndex the index of the building worker
     * @throws IOException if a connection error occurs
     */
    public void build(String player, ClientConnection client, int workerIndex) throws IOException {
        for(ClientConnection c : clientConnections) if(!c.equals(client)) c.sendNotification(player + " is building...");
        for(Player p : getPlayers())
            p.getGod().beforeBuild(player, workerIndex, client, this);

        List<BuildAction> builds = getBuildable(player, workerIndex);
        List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());

        int choice = client.askBuild(buildProposals);

        Action action = builds.get(choice);
        executeAction(action);

        for(Player p : getPlayers())
            p.getGod().afterBuild(player, workerIndex, client, this);
    }

    /**
     * Ends the game by throwing an EndGameException.
     * @param winningPlayer the name of the winning player
     * @throws EndGameException always, to notify the turn function and terminate the Match thread
     * @throws IOException if a connection error occurs
     */
    public void end(String winningPlayer) throws EndGameException, IOException {
        for(ClientConnection c : clientConnections)
            c.endGame(winningPlayer);
        System.out.println("Game ended, closing...");
        throw new EndGameException();
    }

    public void lose(String losingPlayer) throws IOException {
        for(ClientConnection c : clientConnections)
            c.sendNotification(losingPlayer + " lost");
        users.remove(losingPlayer);
        players.get(losingPlayer).clear();
        players.remove(losingPlayer);
        if(users.size() == 1) end(users.get(0));
    }

    /**
     * Retrieve a Player object by username.
     * @param username the username of the Player
     * @return the associated Player
     */
    public Player getPlayerByUsername(String username) {
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
                workerPositions.add(p.getWorkerPos(i));

        return workerPositions;
    }

    /**
     * Check if the target cell contains a worker.
     * @param position the cell you want to check
     * @return true if the cell does not contain a worker
     */
    public boolean isCellFree(Point position) {
        for (Point wp : getWorkerPositions()) {
            if (wp.equals(position))
                return false;
        }
        return true;
    }

    /**
     * Retrieve a list of the possible move actions a player can do.
     * @param playerName player to move
     * @param worker index of worker to move
     * @return an array of Points to move to
     */
    public List<MoveAction> getMovements(String playerName, int worker) {
        ArrayList<MoveAction> legalMoves = new ArrayList<>();

        Player currPlayer = getPlayerByUsername(playerName);

        Point currentPos = currPlayer.getWorkerPos(worker);
        int currentLevel = board.getTowerSize(currentPos);
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            if (Board.isValidPos(toCheckPos)
                    && !board.getIsCompleted(toCheckPos)
                    && isCellFree(toCheckPos)) {
                int toCheckLevel = board.getTowerSize(toCheckPos);
                if (toCheckLevel <= currentLevel + 1)
                    legalMoves.add(new MoveAction(playerName, currentPos, toCheckPos));
            }
        }

        for(Player p : getPlayers())
            try {
                p.getGod().addMoves(legalMoves, playerName, worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        for(Player p : getPlayers())
            try {
                p.getGod().removeMoves(legalMoves, playerName, worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return legalMoves;
    }

    /**
     * Retrieve a list of the possible build actions a player can do.
     * @param player player who builds
     * @param worker index of worker who builds
     * @return an array of Points where building is possible (including dome-building)
     */
    public List<BuildAction> getBuildable(String player, int worker) {
        ArrayList<Point> buildablePositions = new ArrayList<>();

        Point currentPos = getPlayerByUsername(player).getWorkerPos(worker);
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            if (Board.isValidPos(toCheckPos)
                    && !board.getIsCompleted(toCheckPos)
                    && isCellFree(toCheckPos)) {
                buildablePositions.add(toCheckPos);
            }
        }

        List<BuildAction> buildActions = buildablePositions
                .stream()
                .map(p -> new BuildAction(player, p, board.getTowerSize(p) == 3, 1))
                .collect(Collectors.toList());

        for(Player p : getPlayers())
            try {
                p.getGod().addBuilds(buildActions, player, worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        for(Player p : getPlayers())
            try {
                p.getGod().removeBuilds(buildActions, player, worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return buildActions;
    }
}

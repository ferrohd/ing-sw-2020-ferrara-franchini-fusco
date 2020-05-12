package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.actions.Action;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.controller.GodfileParser;
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
        } catch(IOException e) {
            System.out.println("An error has occurred while setting up the game!");
        }

        gameLoop();
    }

    private void setupGame() throws IOException {
        List<String> availableGods = null;
        List<String> selectedGods;

        for (ClientConnection connection : clientConnections) {
            clients.put(connection.getUsername(), connection);
            String username = connection.getUsername();
            while(users.contains(username)) {
                connection.sendNotification("Name already chosen!");
                username = connection.getUsername();
            }
            users.add(connection.getUsername());
        }


        availableGods = GodfileParser.getGodIdList("src/main/resources/gods/godlist.xml");
        ClientConnection roomMaster = clients.get(users.get(0));
        selectedGods = roomMaster.selectGameGods(new ArrayList<>(availableGods), users.size());

        // roomMaster is last to choose
        Collections.rotate(users, -1);
        for (String p : users) {
            ClientConnection player = clients.get(p);
            String chosenGod = player.selectGod(selectedGods);
            gods.put(p, GodFactory.getGod(chosenGod, p));
            selectedGods.remove(chosenGod);
        }
        Collections.rotate(users, 1);

        String firstPlayer = roomMaster.selectFirstPlayer(users);
        Collections.rotate(users, -users.indexOf(firstPlayer));

        for(String p : users) {
            players.put(p, new Player(p, gods.get(p), clientConnections));
        }

        playersPlaceWorkers();
    }

    private void gameLoop() {
        while(true) {
            for(String p: users) {
                try {
                    turn(p);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void playersPlaceWorkers() throws IOException {
        for(int i = 0; i < 2; ++i) {
            for (String p : users) {
                ClientConnection connection = clients.get(p);
                Point pos = connection.placeWorker();
                while(getWorkerPositions().contains(pos)) {
                    connection.sendNotification("Cell busy!");
                    pos = connection.placeWorker();
                }
                getPlayerByUsername(p).setWorker(i, pos);
                for (ClientConnection c : getClientConnections())
                    c.registerWorker(pos, i, p);
            }
        }
    }

    public List<ClientConnection> getClientConnections() {
        return clientConnections;
    }

    public List<Action> getHistory() {
        return history;
    }

    public void executeAction(Action action) throws IOException {
        action.execute(this);
        history.add(action);
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    private void turn(String player) throws IOException {
        ClientConnection client = clients.get(player);

        for(Player p : getPlayers())
            p.getGod().beforeTurn(player, client, this);

        int workerIndex = client.getWorkerIndex();

        move(player, client, workerIndex);
        build(player, client, workerIndex);

        for(Player p: getPlayers())
            p.getGod().afterTurn(player, workerIndex, client, this);
    }

    public void move(String player, ClientConnection client, int workerIndex) throws IOException {
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

    public void build(String player, ClientConnection client, int workerIndex) throws IOException {
        List<BuildAction> builds = getBuildable(player, workerIndex);
        List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());

        int choice = client.askBuild(buildProposals);

        Action action = builds.get(choice);
        executeAction(action);

        for(Player p : getPlayers())
            p.getGod().afterBuild(player, workerIndex, client, this);
    }

    public void end(String winningPlayer) {
        // TODO: end the game, notify the players
        System.out.println(winningPlayer + " won!");
        System.exit(0);
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
        int currentLevel = board.getTowerSize(currentPos);;
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
                p.getGod().addMoves(legalMoves, currPlayer, worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        for(Player p : getPlayers())
            try {
                p.getGod().removeMoves(legalMoves, currPlayer, worker, this);
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

        ArrayList<Point> workerPositions = getWorkerPositions();

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
                p.getGod().addBuilds(buildActions, players.get(player), worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        for(Player p : getPlayers())
            try {
                p.getGod().removeBuilds(buildActions, players.get(player), worker, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return buildActions;
    }
}

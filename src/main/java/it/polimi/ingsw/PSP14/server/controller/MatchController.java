package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.*;
import it.polimi.ingsw.PSP14.core.messages.updates.PlayerRegisterMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerAddMessage;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.PlayerNotFoundException;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.gods.God;
import it.polimi.ingsw.PSP14.server.model.gods.GodControllerFactory;
import it.polimi.ingsw.PSP14.server.actions.*;
import it.polimi.ingsw.PSP14.server.model.GodNotFoundException;
import it.polimi.ingsw.PSP14.server.model.Match;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for Match, supports Multi-Threading
 */
public class MatchController implements Runnable {
    // List of PlayerUsername
    private List<String> players = new ArrayList<>();

    // PlayerUsername <-> ClientConnection
    private Map<String, ClientConnection> clients = new HashMap<>();

    // PlayerUsername <--> GodController
    private Map<String, God> gods = new HashMap<>();

    // Contains data about players, board...
    private Match match;

    /**
     * Constructor of MatchController.
     * Implements Runnable interface.
     * @param clientConnections a list of connections to the clients.
     */
    public MatchController(List<ClientConnection> clientConnections) {
        // Init Connections
        try {
            for (ClientConnection connection : clientConnections)
                initializeConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Bind Model
        match = new Match(clients.keySet());
    }

    public List<ClientConnection> getClientConnections() {
        return new ArrayList<>(clients.values());
    }

    private void initializeConnection(ClientConnection connection) throws IOException {
        Message message = new UsernameMessage();
        connection.sendMessage(message);
        String username = connection.receiveString();
        clients.put(username, connection);
        players.add(username);
    }

    /**
     * Entry point for MatchController logic.
     */
    @Override
    public void run() {
        setupGame();
    }

    private void setupGame() {
        /*================ 1. GAME SETUP ==================*/
        // List of the gods that are available to play;
        List<String> availableGods = null;
        // List of the gods the players choose
        List<String> selectedGods = new ArrayList<>();

        try {
            for (String p : players)
                ClientConnection.sendAll(getClientConnections(), new PlayerRegisterMessage(p));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        // Populate the available gods list from file
        try {
            availableGods = GodfileParser.getGodIdList("src/main/resources/gods/godlist.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //TODO: handle exception
            // Ideas: load a hardcoded set of gods?
            // Try loading the class names in package?
        }

        /*================ 1.1 PLAYERS PICK GODS ==================*/
        if (availableGods != null) {
            try {
                // First player chooses the gods for the other players
                firstPlayerSelectsGods(availableGods, selectedGods);

                playersPickOwnGod(selectedGods);
                // At this point each player should have a unique binding with a god controller.
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // else play a game without gods, or with hardcoded ones?

        /*================ 1.2 PLAYER DECIDES WHO GOES FIRST ==================*/
        // The first player has to choose who goes first, including itself.

        try {
            firstPlayerSelectFirst();
            playersPlaceWorkers();
        } catch (IOException e) {
            e.printStackTrace();
        }


        while(true) {
            for(String p: players) {
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
            for (String p : players) {
                ClientConnection connection = clients.get(p);
                Message message = new WorkerInitialPositionMessage();
                connection.sendMessage(message);
                int[] coord = new int[2];
                coord[0] = connection.receiveChoice();
                coord[1] = connection.receiveChoice();
                Point newPos = new Point(coord[0], coord[1]);
                match.getPlayerByUsername(p).setWorker(i, newPos);
                ClientConnection.sendAll(getClientConnections(), new WorkerAddMessage(newPos, p, i));
            }
        }
    }

    /**
     * Receive each gods Player1 selects and add them to a list.
     * @param availableGods list of the names of the available gods.
     */
    private void firstPlayerSelectsGods(List<String> availableGods, List<String> selectedGods) throws IOException {
        // Get the first player who has to choose the gods for the other players.
        ClientConnection firstPlayer = clients.get(players.get(0));


        for (int i = 0; i < players.size(); i++) {
            List<GodProposal> godProposals = availableGods.stream().map(GodProposal::new).collect(Collectors.toList());
            GodSublistProposalMessage message = new GodSublistProposalMessage(godProposals);
            firstPlayer.sendMessage(message);
            int choice = firstPlayer.receiveChoice();
            selectedGods.add(availableGods.get(choice));
            availableGods.remove(choice);
        }
    }

    /**
     * Each player select a god starting from any but the first one.
     */
    public void playersPickOwnGod(List<String> selectedGods) throws IOException {
        for (int i = players.size() - 1; i >= 0; i--) {
            ClientConnection player = clients.get(players.get(i));
            List<GodProposal> godProposals = selectedGods.stream().map(GodProposal::new).collect(Collectors.toList());
            GodChoiceProposalMessage message = new GodChoiceProposalMessage(godProposals);
            player.sendMessage(message);
            int choice = player.receiveChoice();
            this.gods.put(players.get(i), GodControllerFactory.getController(selectedGods.get(choice), players.get(i)));
            selectedGods.remove(choice);
        }
    }

    private void firstPlayerSelectFirst() throws IOException {
        ClientConnection player = clients.get(players.get(0));
        List<PlayerProposal> playerProposals = players.stream().map(PlayerProposal::new).collect(Collectors.toList());
        FirstPlayerProposalMessage message = new FirstPlayerProposalMessage(playerProposals);

        player.sendMessage(message);
        int choice = player.receiveChoice();

        Collections.rotate(players, players.size() - choice);
    }

    private void turn(String player) throws IOException {
        ClientConnection client = clients.get(player);

        match.getPlayers().forEach(p -> p.getGod().beforeTurn(player, client, match, this));

        int workerIndex = getWorkerIndex(client);

        move(player, client, workerIndex);
        build(player, client, workerIndex);

        match.getPlayers().forEach(p -> p.getGod().afterTurn(player, workerIndex, client, match, this));
    }

    public int getWorkerIndex(ClientConnection client) throws IOException {
        Message message = new WorkerIndexMessage();
        client.sendMessage(message);

        return client.receiveChoice();
    }

    public void move(String player, ClientConnection client, int workerIndex) throws IOException {
        match.getPlayers().forEach(p -> p.getGod().beforeMove(player, workerIndex, client, match, this));

        List<MoveAction> movements = match.getMovements(player, workerIndex);
        List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());
        Message message = new MoveProposalMessage(moveProposals);
        client.sendMessage(message);

        int choice = client.receiveChoice();

        Action action = movements.get(choice);
        action.execute(match);
        match.addActionToHistory(action);
        action.updateClients(getClientConnections());

        match.getPlayers().forEach(p -> p.getGod().afterMove(player, workerIndex, client, match, this));
    }

    public void build(String player, ClientConnection client, int workerIndex) throws IOException {
        List<BuildAction> builds = match.getBuildable(player, workerIndex);
        List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());
        Message message = new BuildProposalMessage(buildProposals);
        client.sendMessage(message);
        int choice = client.receiveChoice();

        Action action = builds.get(choice);
        action.execute(match);
        match.addActionToHistory(action);
        action.updateClients(getClientConnections());

        match.getPlayers().forEach(p -> p.getGod().afterBuild(player, workerIndex, client, match, this));
    }

    public void end(String winningPlayer) {
        // TODO: end the game, notify the players
        System.out.println(winningPlayer + " won!");
        System.exit(0);
    }
}

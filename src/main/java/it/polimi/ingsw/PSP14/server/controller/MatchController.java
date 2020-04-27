package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.*;
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
            } catch (IOException | GodNotFoundException e) {
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
    public void playersPickOwnGod(List<String> selectedGods) throws IOException, GodNotFoundException {
        for (int i = players.size() - 1; i >= 0; i--) {
            ClientConnection player = clients.get(players.get(i));
            List<GodProposal> godProposals = selectedGods.stream().map(GodProposal::new).collect(Collectors.toList());
            GodChoiceProposalMessage message = new GodChoiceProposalMessage(godProposals);
            player.sendMessage(message);
            int choice = player.receiveChoice();
            this.gods.put(players.get(i), GodControllerFactory.getController(selectedGods.get(choice)));
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

    private void turn(String player) throws IOException, PlayerNotFoundException {
        ClientConnection client = clients.get(player);
        Message message = new WorkerIndexMessage();
        client.sendMessage(message);

        int choice = client.receiveChoice();

        List<MoveAction> movements = match.getMovements(player, choice);
        List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());
        message = new MoveProposalMessage(moveProposals);
        client.sendMessage(message);

        choice = client.receiveChoice();

        movements.get(choice).execute(match);
    }
}

package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.server.model.gods.God;
import it.polimi.ingsw.PSP14.server.model.gods.GodControllerFactory;
import it.polimi.ingsw.PSP14.core.actions.*;
import it.polimi.ingsw.PSP14.server.model.GodNotFoundException;
import it.polimi.ingsw.PSP14.server.model.Match;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

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
        clientConnections.forEach(connection -> {
            String username = connection.getPlayerUsername();
            clients.put(username, connection);
            players.add(username);
        });

        // Bind Model
        match = new Match(clients.keySet());
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
            availableGods = GodfileParser.getGodIdList("gods/godlist.xml");
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

                playersPickOwnGod(selectedGods, players);
                // At this point each player should have a unique binding with a god controller.
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // else play a game without gods, or with hardcoded ones?

        /*================ 1.2 PLAYER DECIDES WHO GOES FIRST ==================*/
        // The first player has to choose who goes first, including itself.

        String startingPlayer = players.get(0);

        

    }

    /**
     * Receive each gods Player1 selects and add them to a list.
     * @param availableGods list of the names of the available gods.
     */
    private void firstPlayerSelectsGods(List<String> availableGods, List<String> selectedGods)
    throws IOException {
        // Get the first player who has to choose the gods for the other players.
        ClientConnection firstPlayer = clients.get(players.get(0));

        for (int i = 0; i < players.size(); i++) {
            PickGodAction res = (PickGodAction) firstPlayer.receiveAction();
            if (res.msg != null // I have a valid action
                    && availableGods.contains(res.msg) // The god is in the available ones
                    && !selectedGods.contains(res.msg) // The god hasn't already been chosen
            ) {
                selectedGods.add(res.msg);
            }
        }
    }

    /**
     * Each player select a god starting from any but the first one.
     */
    public void playersPickOwnGod(List<String> selectedGods,
                                  List<String> players)
                                  throws IOException {
        for (int i = players.size() - 1; i >= 0; i--) {
            ClientConnection player = clients.get(players.get(i));
            PickGodAction res = (PickGodAction) player.receiveAction();
            if (res.msg != null && selectedGods.contains(res.msg)) {
                try {
                    this.gods.put(players.get(i), GodControllerFactory.getController(res.msg));
                    selectedGods.remove(res.msg);
                } catch (GodNotFoundException e) {
                    // TODO: Pick another god maybe, a random default one?
                    // Else crash the lobby.
                }
            }
        }
    }
}

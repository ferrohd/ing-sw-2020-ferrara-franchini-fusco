package it.polimi.ingsw.PSP14.server.match;

import it.polimi.ingsw.PSP14.core.controller.GodController;
import it.polimi.ingsw.PSP14.server.ClientConnection;
import it.polimi.ingsw.PSP14.server.GodfileParser;
import it.polimi.ingsw.PSP14.core.model.actions.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for Match, supports Multi-Threading
 */
public class MatchController implements Runnable {
    // List of PlayerUsername
    private ArrayList<String> players = new ArrayList<>();
    // List of Gods
    private ArrayList<String> selectedGods = new ArrayList<>();
    // PlayerUsername <-> ClientConnection
    private HashMap<String, ClientConnection> clients = new HashMap<>();
    // PlayerUsername <--> GodController
    private HashMap<String, GodController> gods = new HashMap<>();
    // Contains data about players, board...
    private MatchModel match;

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
        match = new MatchModel(clients.keySet());
    }

    /**
     * Entry point for MatchController logic.
     */
    @Override
    public void run() {
        setupGame();
    }

    private void setupGame() {
        ArrayList<String> availableGods = null;
        try {
            availableGods = GodfileParser.getGodIdList("gods/godlist.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //TODO: handle exception
            // Ideas: load an hardcoded set of gods?
            // Try loading the class names in package?
        }

        if (availableGods != null)
            letPlayersChooseGods(availableGods);
    }

    private void letPlayersChooseGods(List<String> gods) {
        // First player chooses the gods for the other players
        ClientConnection firstPlayer = clients.get(players.get(0));

        // Receive each gods Player1 selects and add them to a list
        for (int i = 0; i < players.size(); i++) {
            PickGodAction res = (PickGodAction) firstPlayer.receiveAction();
            if (res.msg != null && gods.contains(res.msg)) {
                selectedGods.add(res.msg);
            }
        }

        // Each player select a god starting from the second
        for (int i = 0; i < players.size() - 1; i++) {
            ClientConnection player = clients.get(players.get(i));
            PickGodAction res = (PickGodAction) player.receiveAction();
            if (res.msg != null && selectedGods.contains(res.msg)) {
                this.gods.put(players.get(i), /* TODO: Ref to a specific GodController */);
            }
        }
    }
}

package it.polimi.ingsw.PSP14.server;

import it.polimi.ingsw.PSP14.core.Player;
import it.polimi.ingsw.PSP14.core.controller.gods.GodController;
import it.polimi.ingsw.PSP14.core.controller.gods.GodControllerFactory;
import it.polimi.ingsw.PSP14.core.model.Direction;
import it.polimi.ingsw.PSP14.core.model.GodNotFoundException;
import it.polimi.ingsw.PSP14.core.model.PlayerNotFoundException;
import it.polimi.ingsw.PSP14.core.model.Point;
import it.polimi.ingsw.PSP14.core.model.actions.*;
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
    private Map<String, GodController> gods = new HashMap<>();
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

    /**
     * @param player player to move
     * @param worker index of worker to move
     * @return an array of Points to move to
     * @throws PlayerNotFoundException if the player given is not playing
     */
    public ArrayList<Point> getMovements(String player, int worker) throws PlayerNotFoundException {
        ArrayList<Point> legalPositions = new ArrayList<>();
        ArrayList<Player> players = match.getPlayers();

        ArrayList<Point> workerPositions = match.getWorkerPositions();

        Point currentPos = match.getPlayerByUsername(player).getWorker(worker).getPos();
        int currentLevel = match.getBoard().getCell(currentPos).getTowerSize();
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            int toCheckLevel = match.getBoard().getCell(toCheckPos).getTowerSize();
            if(!workerPositions.contains(toCheckPos) &&
                    toCheckLevel <= currentLevel+1 &&
                    !match.getBoard().getCell(toCheckPos).getIsCompleted())
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
        ArrayList<Player> players = match.getPlayers();

        ArrayList<Point> workerPositions = match.getWorkerPositions();

        Point currentPos = match.getPlayerByUsername(player).getWorker(worker).getPos();
        for(Direction dir: Direction.values()) {
            Point toCheckPos = currentPos.move(dir);
            if(!workerPositions.contains(toCheckPos) && !match.getBoard().getCell(toCheckPos).getIsCompleted())
                buildablePositions.add(toCheckPos);
        }

        return buildablePositions;
    }
}

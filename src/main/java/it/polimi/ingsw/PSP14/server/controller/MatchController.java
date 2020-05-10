package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.*;
import it.polimi.ingsw.PSP14.core.messages.updates.PlayerRegisterMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerAddMessage;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.gods.God;
import it.polimi.ingsw.PSP14.server.model.gods.GodFactory;
import it.polimi.ingsw.PSP14.server.actions.*;
import it.polimi.ingsw.PSP14.server.model.Match;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for Match, supports Multi-Threading
 */
public class MatchController implements Runnable {
    private List<String> players = new ArrayList<>();
    private Map<String, ClientConnection> clients = new HashMap<>();
    private Map<String, God> gods = new HashMap<>();
    private Match match;

    /**
     * Constructor of MatchController.
     * Implements Runnable interface.
     * @param clientConnections a list of connections to the clients.
     */
    public MatchController(List<ClientConnection> clientConnections) throws IOException {
        for (ClientConnection connection : clientConnections) {
            clients.put(connection.getUsername(), connection);
            players.add(connection.getUsername());
        }
    }

    public List<ClientConnection> getClientConnections() {
        return new ArrayList<>(clients.values());
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
        ClientConnection roomMaster = clients.get(players.get(0));

        for (ClientConnection c : getClientConnections())
            for (String p : players)
                c.registerPlayer(p);

        availableGods = GodfileParser.getGodIdList("src/main/resources/gods/godlist.xml");
        selectedGods = roomMaster.selectGameGods(new ArrayList<>(availableGods), players.size());

        // roomMaster is last to choose
        Collections.rotate(players, -1);
        for (String p : players) {
            ClientConnection player = clients.get(p);
            String chosenGod = player.selectGod(selectedGods);
            gods.put(p, GodFactory.getGod(chosenGod, p));
            selectedGods.remove(chosenGod);
        }
        Collections.rotate(players, 1);

        String firstPlayer = roomMaster.selectFirstPlayer(players);
        Collections.rotate(players, -players.indexOf(firstPlayer));

        match = new Match(clients.keySet(), gods);

        playersPlaceWorkers();
    }

    private void gameLoop() {
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
                Point pos = connection.placeWorker();
                match.getPlayerByUsername(p).setWorker(i, pos);
                for (ClientConnection c : getClientConnections())
                    c.registerWorker(pos, i, p);
            }
        }
    }

    private void turn(String player) throws IOException {
        ClientConnection client = clients.get(player);

        match.getPlayers().forEach(p -> p.getGod().beforeTurn(player, client, match, this));

        int workerIndex = client.getWorkerIndex();

        move(player, client, workerIndex);
        build(player, client, workerIndex);

        match.getPlayers().forEach(p -> p.getGod().afterTurn(player, workerIndex, client, match, this));
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

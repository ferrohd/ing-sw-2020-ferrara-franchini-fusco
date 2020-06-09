package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.model.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.model.actions.MoveAction;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.gods.God;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchController {
    private List<String> users = new ArrayList<>(3);
    private Map<String, ClientConnection> connections = new HashMap<>(3);

    public MatchController(List<ClientConnection> clientConnections) throws IOException {
        for (ClientConnection c : clientConnections) c.sendNotification("Game started! You will be asked to insert your username soon");
        for (ClientConnection connection : clientConnections) {
            String username = connection.getUsername();
            while(users.contains(username)) {
                connection.sendNotification("Name already chosen!");
                username = connection.getUsername();
            }
            connections.put(username, connection);
            users.add(username);
        }
    }

    public List<String> getUsers() {
        return users;
    }

    public void closeConnections() {
        for(ClientConnection c : connections.values()) {
            try {
                c.close();
            } catch(IOException e) {
                System.out.println("Error closing connection!");
            }
        }
    }

    public List<String> getMatchGods(String roomMaster) throws IOException {
        List<String> availableGods = GodfileParser.getGodIdList(getClass().getClassLoader().getResourceAsStream("gods/godlist.xml"), users.size());
        ClientConnection masterConnection = connections.get(roomMaster);
        for(ClientConnection c : connections.values())
            if(!c.equals(masterConnection))
                c.sendNotification(roomMaster + " (room leader) is choosing the gods of the game.");

        return masterConnection.selectGameGods(new ArrayList<>(availableGods), users.size());
    }

    public String chooseGod(String player, List<String> availableGods) throws IOException {
        ClientConnection conn = connections.get(player);
        for(ClientConnection c : connections.values()) if(!c.equals(conn)) c.sendNotification(player + " is choosing their god.");
        String chosenGod = conn.selectGod(availableGods);
        for(ClientConnection c : connections.values()) c.notifyGod(player, chosenGod);

        return chosenGod;
    }

    public String chooseFirstPlayer(String roomMaster, List<String> players) throws IOException {
        ClientConnection masterConnection = connections.get(roomMaster);

        for(ClientConnection c : connections.values())
            if(!c.equals(masterConnection))
                c.sendNotification(roomMaster + " (room leader) is choosing who goes first.");

        String firstPlayer = masterConnection.selectFirstPlayer(users);

        for(ClientConnection c : connections.values())
            if(!c.equals(masterConnection))
                c.sendNotification(firstPlayer + " is first!");

        return firstPlayer;
    }

    public void startGame() throws IOException {
        for(ClientConnection c : connections.values()) c.notifyGameStart();
    }

    public Point chooseWorkerPosition(String player, List<Point> busy) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyWorkerChoicePhase(player);
        ClientConnection conn = connections.get(player);
        Point pos = conn.placeWorker();
        while(busy.stream().anyMatch(pos::equals)) {
            conn.sendNotification("Cell busy!");
            pos = conn.placeWorker();
        }

        return pos;
    }

    public void registerPlayer(String player) throws IOException {
        for(ClientConnection c : connections.values()) c.registerPlayer(player);
    }

    public void notifyWorkerMove(Point pos, String player, int workerIndex) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyWorkerMove(pos, player, workerIndex);
    }

    public void registerWorker(Point pos, String player, int workerIndex) throws IOException {
        for(ClientConnection c : connections.values()) c.registerWorker(pos, player, workerIndex);
    }

    public void notifyUnregisterPlayer(String player) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyUnregisterPlayer(player);
    }

    public void notifyBuild(Point pos) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyBuild(pos);
    }

    public void notifyDome(Point pos) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyDome(pos);
    }

    public void startTurn(String player) throws IOException {
        for(ClientConnection c : connections.values())
            if(!c.equals(connections.get(player)))
                c.sendNotification("The turn of " + player + " has begun.");
    }

    public int getWorkerIndex(String player, List<Integer> movableWorkers) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyWorkerChoicePhase(player);

        return connections.get(player).getWorkerIndex(movableWorkers);
    }

    public int askMove(String player, List<MoveAction> movements) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyMovePhase(player);
        List<MoveProposal> moveProposals = movements.stream().map(MoveAction::getProposal).collect(Collectors.toList());

        return connections.get(player).askMove(moveProposals);
    }

    public int askBuild(String player, List<BuildAction> builds) throws IOException {
        for(ClientConnection c : connections.values()) c.notifyBuildPhase(player);
        List<BuildProposal> buildProposals = builds.stream().map(BuildAction::getProposal).collect(Collectors.toList());

        return connections.get(player).askBuild(buildProposals);
    }

    public void endGame(String winningPlayer) throws IOException {
        for(ClientConnection c : connections.values()) c.endGame(winningPlayer);
    }

    public void lose(String losingPlayer) throws IOException {
        for(ClientConnection c : connections.values()) c.sendNotification(losingPlayer + " lost");
    }
}

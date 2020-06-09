package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.server.model.board.Point;
import it.polimi.ingsw.PSP14.server.model.gods.God;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

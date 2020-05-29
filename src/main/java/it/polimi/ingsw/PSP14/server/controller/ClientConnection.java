package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.*;
import it.polimi.ingsw.PSP14.core.messages.updates.*;
import it.polimi.ingsw.PSP14.core.proposals.*;
import it.polimi.ingsw.PSP14.server.model.board.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic connection to a client.
 * Exposes the functions for bidirectional communication with a client.
 */
public abstract class ClientConnection {
    /**
     * Serialize and send a message to the client.
     */
    protected abstract void sendMessage(Message message) throws IOException;

    /**
     * Receive a message from the client.
     */
    protected abstract Message receiveMessage() throws IOException;

    public void notifyGameStart() throws IOException {
        sendMessage(new GameStartMessage());
    }

    /**
     * A request to the client to provide the name that the player has chosen.
     * @return the player username
     */
    public String getUsername() throws IOException {
        Message message = new UsernameMessage();
        sendMessage(message);
        return receiveString();
    }

    public List<String> selectGameGods(List<String> availableGods, int n) throws IOException {
        List<String> selectedGods = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<GodProposal> godProposals = availableGods.stream().map(GodProposal::new).collect(Collectors.toList());
            GodSublistProposalMessage message = new GodSublistProposalMessage(godProposals);
            sendMessage(message);
            int choice = receiveChoice();
            selectedGods.add(availableGods.get(choice));
            availableGods.remove(choice);
        }

        return selectedGods;
    }

    /**
     * Asks the user to pick the God he wants to play with
     * @param gods avaible gods to chose from
     * @return the god selected
     * @throws IOException
     */
    public String selectGod(List<String> gods) throws IOException {
        List<GodProposal> godProposals = gods.stream().map(GodProposal::new).collect(Collectors.toList());
        GodChoiceProposalMessage message = new GodChoiceProposalMessage(godProposals);
        sendMessage(message);
        int choice = receiveChoice();
        while(choice < 0 || choice >= godProposals.size()) {
            sendNotification("Out of range!");
            sendMessage(message);
            choice = receiveChoice();
        }

        return gods.get(choice);
    }

    /**
     * Ask the player owner of the lobby which player will make the first move
     * @param players list of the players
     * @return selected player
     * @throws IOException
     */
    public String selectFirstPlayer(List<String> players) throws IOException {
        List<PlayerProposal> playerProposals = players.stream().map(PlayerProposal::new).collect(Collectors.toList());
        FirstPlayerProposalMessage message = new FirstPlayerProposalMessage(playerProposals);

        sendMessage(message);
        int choice = receiveChoice();
        while(choice < 0 || choice >= playerProposals.size()) {
            sendNotification("Out of range!");
            sendMessage(message);
            choice = receiveChoice();
        }

        return players.get(choice);
    }

    public int getWorkerIndex(List<Integer> choosable) throws IOException {
        Message message = new WorkerIndexMessage(choosable);
        sendMessage(message);
        int workerIndex = receiveChoice();
        while(workerIndex != 0 && workerIndex != 1) {
            sendNotification("Out of Range!");
            sendMessage(message);
            workerIndex = receiveChoice();
        }

        return workerIndex;
    }

    /**
     * Asks the player where to place the Worker
     * @return a Point where to place the worker
     * @throws IOException
     */
    public Point placeWorker() throws IOException {
        Message message = new WorkerInitialPositionMessage();
        sendMessage(message);
        int[] coord = new int[2];
        coord[0] = receiveChoice();
        coord[1] = receiveChoice();
        while(coord[0] < 0 || coord[0] >= 5 || coord[1] < 0 || coord[1] >= 5) {
            sendNotification("Out of range!");
            sendMessage(message);
            coord[0] = receiveChoice();
            coord[1] = receiveChoice();
        }

        return new Point(coord[0], coord[1]);
    }

    public void registerPlayer(String p) throws IOException {
        sendMessage(new PlayerRegisterMessage(p));
    }

    public void registerWorker(Point pos, int workerIndex, String player) throws IOException {
        sendMessage(new WorkerAddMessage(pos, player, workerIndex));
    }

    public void notifyDome(Point p) throws IOException {
        sendMessage(new DomeBuildMessage(p));
    }

    /**
     * Notify the player a tower has been built with size amount and in position p
     * @param p position (Point) of the build
     * @param amount size of the build
     * @throws IOException
     */
    public void notifyBuild(Point p, int amount) throws IOException {
        Message message = new TowerIncrementMessage(p);
        for (int i = 0; i < amount; ++i)
            sendMessage(message);
    }

    public void notifyWorkerMove(Point p, String user, int workerId) throws IOException {
        sendMessage(new WorkerMoveMessage(p, user, workerId));
    }

    public void notifyUnregisterPlayer(String player) throws IOException {
        sendMessage(new UnregisterPlayerMessage(player));
    }

    /**
     * Send a generic choice to a player
     * @param message Message to the player
     * @param size size of the proposal range (0-size)
     * @return choice of the player (between 0 and size)
     * @throws IOException
     */
    private int askProposalMessage(ProposalMessage<? extends Proposal> message, int size) throws IOException {
        sendMessage(message);
        int choice = receiveChoice();
        while(choice < 0 || choice >= size) {
            sendNotification("Out of range!");
            sendMessage(message);
            choice = receiveChoice();
        }

        return choice;
    }

    public int askBuild(List<BuildProposal> proposals) throws IOException {
        return askProposalMessage(new BuildProposalMessage(proposals), proposals.size());
    }

    public int askMove(List<MoveProposal> proposals) throws IOException {
        return askProposalMessage(new MoveProposalMessage(proposals), proposals.size());
    }

    /**
     * Ask the player the size of the lobby to be create
     * @return
     * @throws IOException
     */
    public int askLobbySize() throws IOException {
        Message message = new LobbySizeMessage();
        sendMessage(message);
        int choice = receiveChoice();

        while(choice != 2 && choice != 3) {
            sendNotification("Error!");
            sendMessage(message);
            choice = receiveChoice();
        }

        return choice;
    }

    public void sendNotification(String s) throws IOException {
        sendMessage(new NotificationMessage(s));
    }

    public boolean askQuestion(String s) throws IOException {
        Message message = new YesNoMessage(s);
        sendMessage(message);

        int choice = receiveChoice();
        while(choice != 0 && choice != 1) {
            sendNotification("Error!");
            sendMessage(message);
            choice = receiveChoice();
        }

        return choice == 1;
    }

    /**
     * Notify the player that the game has ended with a winner
     * @param winner winner of the game
     * @throws IOException
     */
    public void endGame(String winner) throws IOException {
        sendMessage(new GameEndMessage(winner));
    }

    protected abstract int receiveChoice() throws IOException;

    protected abstract String receiveString() throws IOException;

    public abstract void close() throws IOException;
}

package it.polimi.ingsw.PSP14.server.controller;

import it.polimi.ingsw.PSP14.core.messages.*;
import it.polimi.ingsw.PSP14.core.messages.updates.*;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
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
    private String username = null;

    /**
     * Serialize and send a message to the client.
     */
    public abstract void sendMessage(Message message) throws IOException;

    /**
     * Receive a message from the client.
     */
    public abstract Message receiveMessage() throws IOException;

    /**
     * A request to the client to provide the name that the player has chosen.
     * @return the player username
     */
    public String getUsername() throws IOException {
        if (username == null) {
            Message message = new UsernameMessage();
            sendMessage(message);
            username = receiveString();
        }

        return username;
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

    public String selectGod(List<String> gods) throws IOException {
        List<GodProposal> godProposals = gods.stream().map(GodProposal::new).collect(Collectors.toList());
        GodChoiceProposalMessage message = new GodChoiceProposalMessage(godProposals);
        sendMessage(message);
        int choice = receiveChoice();

        return gods.get(choice);
    }

    public String selectFirstPlayer(List<String> players) throws IOException {
        List<PlayerProposal> playerProposals = players.stream().map(PlayerProposal::new).collect(Collectors.toList());
        FirstPlayerProposalMessage message = new FirstPlayerProposalMessage(playerProposals);

        sendMessage(message);
        int choice = receiveChoice();

        return players.get(choice);
    }

    public int getWorkerIndex() throws IOException {
        Message message = new WorkerIndexMessage();
        sendMessage(message);

        return receiveChoice();
    }

    public Point placeWorker() throws IOException {
        Message message = new WorkerInitialPositionMessage();
        sendMessage(message);
        int[] coord = new int[2];
        coord[0] = receiveChoice();
        coord[1] = receiveChoice();

        return new Point(coord[0], coord[1]);
    }

    public void registerPlayer(String p) throws IOException {
        Message message = new PlayerRegisterMessage(p);
        sendMessage(message);
    }

    public void registerWorker(Point pos, int workerIndex, String player) throws IOException {
        Message message = new WorkerAddMessage(pos, player, workerIndex);
        sendMessage(message);
    }

    public void notifyDome(Point p) throws IOException {
        Message message = new DomeBuildMessage(p);
        sendMessage(message);
    }

    public void notifyBuild(Point p, int amount) throws IOException {
        Message message = new TowerIncrementMessage(p);
        for (int i = 0; i < amount; ++i)
            sendMessage(message);
    }

    public void notifyWorkerMove(Point p, String user, int workerId) throws IOException {
        Message message = new WorkerMoveMessage(p, user, workerId);
        sendMessage(message);
    }

    public abstract int receiveChoice() throws IOException;

    public abstract String receiveString() throws IOException;

    public abstract void close() throws IOException;
}

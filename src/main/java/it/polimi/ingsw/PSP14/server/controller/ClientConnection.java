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
    protected void notifyGod(String player, String god) throws IOException {
        sendMessage(new GodUpdateMessage(player, god));
    }

    protected void ping() throws IOException {
        sendMessage(new PingMessage());
    }
    /**
     * Serialize and send a message to the client.
     * @param message the message to send
     * @throws IOException if it fails to send the message
     */
    protected abstract void sendMessage(Message message) throws IOException;

    /**
     * Receive a message from the client.
     * @return the received message
     * @throws IOException if it fails to send the message
     */
    protected abstract Message receiveMessage() throws IOException;

    protected void notifyGameStart() throws IOException {
        sendMessage(new GameStartMessage());
    }

    /**
     * A request to the client to provide the name that the player has chosen.
     * @return the player username
     * @throws IOException if there's a connection error
     */
    protected String getUsername() throws IOException {
        Message message = new UsernameMessage();
        sendMessage(message);
        return receiveString();
    }

    /**
     * Ask a player to select from a list of gods the one that will be available in the match.
     * @param availableGods the gods to select from
     * @param n the number of gods to select
     * @return a list of god names representing the chosen gods
     * @throws IOException if there's a connection error
     */
    protected List<String> selectGameGods(List<String> availableGods, int n) throws IOException {
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
     * @param gods available gods to chose from
     * @return the god selected
     * @throws IOException if there's a connection error
     */
    protected String selectGod(List<String> gods) throws IOException {
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
     * @throws IOException if there's a connection error
     */
    protected String selectFirstPlayer(List<String> players) throws IOException {
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

    /**
     * Retrieve the index of a worker specified by a player.
     * @param choices the workers' indexes
     * @return the index of the chosen worker
     * @throws IOException if there's a connection error
     */
    protected int getWorkerIndex(List<Integer> choices) throws IOException {
        Message message = new WorkerIndexMessage(choices);
        sendMessage(message);
        int workerIndex = receiveChoice();
        while(!choices.contains(workerIndex)) {
            sendNotification("Out of Range!");
            sendMessage(message);
            workerIndex = receiveChoice();
        }

        return workerIndex;
    }

    /**
     * Asks the player where to place the Worker
     * @return a Point where to place the worker
     * @throws IOException if there's a connection error
     */
    protected Point placeWorker() throws IOException {
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

    /**
     * Add a player to the match
     * @param p the name of the player
     * @throws IOException if there's a connection error
     */
    protected void registerPlayer(String p) throws IOException {
        sendMessage(new PlayerRegisterMessage(p));
    }

    /**
     * Add a worker to a match
     * @param pos where to place the worker
     * @param player the worker's owner name
     * @param workerIndex the index of the worker
     * @throws IOException if there's a connection error
     */
    protected void registerWorker(Point pos, String player, int workerIndex) throws IOException {
        sendMessage(new WorkerAddMessage(pos, player, workerIndex));
    }

    /**
     * Add a dome at the target position.
     * @param p the target position
     * @throws IOException if there's a connection error
     */
    protected void notifyDome(Point p) throws IOException {
        sendMessage(new DomeBuildMessage(p));
    }

    /**
     * Notify the client a tower has been built with size amount and in position p
     * @param p position (Point) of the build
     * @throws IOException if there's a connection error
     */
    protected void notifyBuild(Point p) throws IOException {
        sendMessage(new TowerIncrementMessage(p));
    }

    /**
     * Notify the client of that a worker has been moved
     * @param p the new position of the worker
     * @param user the worker's owner
     * @param workerId the worker's id
     * @throws IOException if there's a connection error
     */
    protected void notifyWorkerMove(Point p, String user, int workerId) throws IOException {
        sendMessage(new WorkerMoveMessage(p, user, workerId));
    }

    /**
     * Tell the client that a player has been removed
     * @param player the player to unregister
     * @throws IOException if there's a connection error
     */
    protected void notifyUnregisterPlayer(String player) throws IOException {
        sendMessage(new UnregisterPlayerMessage(player));
    }

    /**
     * Send a generic choice to the player
     * @param message Message to the player
     * @param size size of the proposal range (0-size)
     * @return choice of the player (between 0 and size)
     * @throws IOException if there's a connection error
     */
    protected int askProposalMessage(ProposalMessage<? extends Proposal> message, int size) throws IOException {
        sendMessage(message);
        int choice = receiveChoice();
        while(choice < 0 || choice >= size) {
            sendNotification("Out of range!");
            sendMessage(message);
            choice = receiveChoice();
        }

        return choice;
    }

    /**
     * Ask the player where they want to build.
     * @param proposals a list of possible moves
     * @return the index of the chosen move
     * @throws IOException if there's a connection error
     */
    protected int askBuild(List<BuildProposal> proposals) throws IOException {
        return askProposalMessage(new BuildProposalMessage(proposals), proposals.size());
    }

    /**
     * Ask the player where they want to move.
     * @param proposals a list of possible moves
     * @return the index of the chosen move
     * @throws IOException if there's a connection error
     */
    protected int askMove(List<MoveProposal> proposals) throws IOException {
        return askProposalMessage(new MoveProposalMessage(proposals), proposals.size());
    }

    /**
     * Ask the player the size of the lobby to be create
     * @return the size of the lobby
     * @throws IOException if there's a connection error
     */
    protected int askLobbySize() throws IOException {
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

    /**
     * Send a notification to the client
     * @param s the content of the notification
     * @throws IOException if there's a connection error
     */
    protected void sendNotification(String s) throws IOException {
        sendMessage(new NotificationMessage(s));
    }

    /**
     * Ask the player a close question (yes/no)
     * @param s the content of the question
     * @return the answer
     * @throws IOException if there's a connection error
     */
    protected boolean askQuestion(String s) throws IOException {
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
     * @throws IOException if there's a connectione error
     */
    protected void endGame(String winner) throws IOException {
        sendMessage(new GameEndMessage(winner));
    }

    protected abstract int receiveChoice() throws IOException;

    protected abstract String receiveString() throws IOException;

    /**
     * Notify that a player is choosing which of their workers to move
     * @param player the name of that player
     * @throws IOException if there's a connection error
     */
    protected void notifyWorkerChoicePhase(String player) throws IOException {
        sendMessage(new WorkerChoicePhaseMessage(player));
    }

    /**
     * Notify that a player is moving
     * @param player the name of that player
     * @throws IOException if there's a connection error
     */
    protected void notifyMovePhase(String player) throws IOException {
        sendMessage(new MovePhaseMessage(player));
    }

    /**
     * Notify that a player is building
     * @param player the name of that player
     * @throws IOException if there's a connection error
     */
    protected void notifyBuildPhase(String player) throws IOException {
        sendMessage(new BuildPhaseMessage(player));
    }

    /**
     * Notify that a player is placing their workers
     * @param player the name of that player
     * @throws IOException if there's a connection error
     */
    protected void notifyWorkerPlacementPhase(String player) throws IOException {
        sendMessage(new WorkerPlacementPhaseMessage(player));
    }

    /**
     * Close the connection with the client.
     * @throws IOException if there's a connection error.
     */
    protected abstract void close() throws IOException;
}

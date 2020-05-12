package it.polimi.ingsw.PSP14.client.view;

import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.client.view.GUIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class GUI extends UI {

    private final BlockingQueue<Object> reqQueue = GUIQueues.getReq();
    private final BlockingQueue<Object> resQueue = GUIQueues.getRes();

    String ip, username;

    @Override
    public void update() {

    }

    @Override
    Color getColor() {
        return null;
    }

    /**
     * Display a greeting to the player.
     */
    @Override
    public void welcome() throws InterruptedException {
        reqQueue.add("welcome");
        GUIUtils.launch(GUIUtils.class);
        this.ip = (String) this.resQueue.take();
        this.username = (String) this.resQueue.take();
    }

    /**
     * Display on the UI that a player is trying to connect to a server
     *
     * @param hostname the address of the server
     * @param port     the port of the server
     */
    @Override
    public void notifyConnection(String hostname, int port) {
        notify("Connecting to " + hostname + " at port " + port + "...");
    }

    /**
     * Ask the player for how many players should participate in a match.
     *
     * @return the size of the lobby
     */
    @Override
    public int getLobbySize() throws InterruptedException {
        reqQueue.add("lobbySize");
        return (int) resQueue.take();
    }

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    @Override
    public void notify(String s) {
        reqQueue.add("notify");
        reqQueue.add(s);
    }

    /**
     * Prompt the player to provide a username
     *
     * @return the chosen username
     */
    @Override
    public String askUsername() throws InterruptedException {
        reqQueue.add("username");
        return (String) resQueue.take();
    }

    /**
     * Prompt the player to select a god from the list of available gods.
     *
     * @param proposals Available gods
     * @return the index of the chosen god.
     */
    @Override
    public int chooseGod(List<GodProposal> proposals) {
        reqQueue.add("chooseGod");
        reqQueue.add(proposals);
        return 0;
    }

    /**
     * Ask the player to choose another player (even themselves) from a list.
     *
     * @param proposals the list of players to choose from
     * @return the index of the chosen player
     */
    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) {
        reqQueue.add("chooseFirstPlayer");
        reqQueue.add(proposals);
        return 0;
    }

    /**
     * Ask the player to choose a worker from a list
     *
     * @return the index of the chosen worker
     */
    @Override
    public int chooseWorker() {
        return 0;
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) {
        reqQueue.add("chooseAvailableGods");
        reqQueue.add(gods);
        return 0;
    }

    @Override
    public int[] chooseWorkerInitialPosition() {
        return new int[0];
    }
}
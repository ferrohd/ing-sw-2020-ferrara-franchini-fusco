package it.polimi.ingsw.PSP14.client.view.gui;

import it.polimi.ingsw.PSP14.client.model.Color;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import javafx.application.Platform;

import java.util.List;

public class GUI extends UI {


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
        new Thread(() -> GUIMain.launch(GUIMain.class)).start();
        GUIMain.getQueue().take();
        Platform.runLater(new GUIUsernameScene());
        System.out.println((String) GUIMain.getQueue().take());
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
        GUIMain.getQueue().add("lobbySize");
        return (int) GUIMain.getQueue().take();
    }

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    @Override
    public void notify(String s) {

    }

    /**
     * Prompt the player to provide a username
     *
     * @return the chosen username
     */
    @Override
    public String askUsername() throws InterruptedException {
        Platform.runLater(new GUIUsernameScene());
        return (String) GUIMain.getQueue().take();
    }

    /**
     * Prompt the player to select a god from the list of available gods.
     *
     * @param proposals Available gods
     * @return the index of the chosen god.
     */
    @Override
    public int chooseGod(List<GodProposal> proposals) {
        GUIMain.getQueue().add("chooseGod");
        GUIMain.getQueue().add(proposals);
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
        GUIMain.getQueue().add("chooseFirstPlayer");
        GUIMain.getQueue().add(proposals);
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
        GUIMain.getQueue().add("chooseAvailableGods");
        GUIMain.getQueue().add(gods);
        return 0;
    }

    @Override
    public int[] chooseWorkerInitialPosition() {
        return new int[0];
    }
}
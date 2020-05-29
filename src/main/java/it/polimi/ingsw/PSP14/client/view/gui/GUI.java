package it.polimi.ingsw.PSP14.client.view.gui;

import it.polimi.ingsw.PSP14.client.view.cli.UIColor;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.client.view.gui.scenes.*;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GUI implements UI {
    private String currentPlayerId;
    private final ArrayList<String> players = new ArrayList<>();
    private final GUIGameScene gameScene = new GUIGameScene();

    @Override
    public void registerPlayer(String player) {
        players.add(player);
    }

    @Override
    public void unregisterPlayer(String username) {
        unsetWorker(0, username);
        unsetWorker(1, username);
    }

    @Override
    public void setWorker(Point position, int workerId, String playerUsername) {
        Platform.runLater(() ->
                gameScene.getModel().addWorker(position, workerId, players.indexOf(playerUsername))
        );
    }

    private void unsetWorker(int workerId, String playerUsername) {
        Platform.runLater(() ->
                gameScene.getModel().removeWorker(workerId, players.indexOf(playerUsername))
        );
    }

    @Override
    public void moveWorker(Point newPosition, int workerId, String playerUsername) {
        Platform.runLater(() ->
                gameScene.getModel().moveWorker(players.indexOf(playerUsername), workerId, newPosition)
        );
    }

    @Override
    public void incrementCell(Point position) {
        Platform.runLater(() ->
                gameScene.getModel().incrementCell(position)
        );
    }

    @Override
    public void setDome(Point position) {
        Platform.runLater(() ->
                gameScene.getModel().putDome(position)
        );
    }

    @Override
    public void update() {

    }

    /**
     * Get a color depending on the player unique id.
     *
     * @param playerNumber an int between <code>1</code> and <code>3</code>
     * @return a color
     */
    @Override
    public UIColor getColor(int playerNumber) {
        return null;
    }


    /**
     * Display a greeting to the player.
     */
    @Override
    public void welcome() throws InterruptedException {
        new Thread(() -> GUIMain.launch(GUIMain.class)).start();
        GUIMain.getQueue().take();

        Platform.runLater(new GUIWelcomeScene());
    }

    private void startGameScene() {
        Platform.runLater(gameScene);

        showDemo();
    }

    private void showDemo() {
        new Thread(() -> {
            try {
                incrementCell(new Point(0, 0));
                players.add("pippo");
                players.add("pluto");
                incrementCell(new Point(0, 0));
                setDome(new Point(3, 3));
                for (int i = 0; i < 3; ++i)
                    incrementCell(new Point(2, 3));
                setWorker(new Point(0, 0), 0, "pippo");
                setWorker(new Point(4, 4), 0, "pluto");
                setWorker(new Point(1, 0), 1, "pippo");
                Thread.sleep(3000);
                moveWorker(new Point(0, 1), 0, "pippo");
                Thread.sleep(1500);
                incrementCell(new Point(4, 1));
                moveWorker(new Point(4, 1), 1, "pippo");
                Thread.sleep(2000);
                incrementCell(new Point(4, 1));
                Thread.sleep(1000);
                moveWorker(new Point(0, 0), 0, "pippo");
            } catch(InterruptedException e) {}
        }).start();
    }

    /**
     * Ask the player for how many players should participate in a match.
     *
     * @return the size of the lobby
     */
    @Override
    public int getLobbySize() throws InterruptedException {
        Platform.runLater(new GUILobbySizeScene());
        return  (int) GUIMain.getQueue().take();
    }

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    @Override
    public void showNotification(String s) {

    }

    /**
     * Prompt the player to provide a username
     *
     * @return the chosen username
     */
    @Override
    public String askUsername() throws InterruptedException {
        Platform.runLater(new GUIUsernameScene());
        currentPlayerId = (String) GUIMain.getQueue().take();
        return currentPlayerId;
    }

    /**
     * Prompt the player to select a god from the list of available gods.
     *
     * @param proposals Available gods
     * @return the index of the chosen god.
     */
    @Override
    public int chooseGod(List<GodProposal> proposals) throws InterruptedException {
        Platform.runLater(new GUIGodSelectScene("Choose your God", proposals.stream().map(GodProposal::getName).collect(Collectors.toList())));
        return (Integer) GUIMain.getQueue().take();
    }

    /**
     * Ask the player to choose another player (even themselves) from a list.
     *
     * @param proposals the list of players to choose from
     * @return the index of the chosen player
     */
    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) {
        return 0;
    }

    /**
     * Ask the player to choose a worker from a list
     *
     * @param choices a list of workers to choose from
     * @return the index of the chosen worker
     */
    @Override
    public int chooseWorker(List<Integer> choices) throws InterruptedException {
        gameScene.setIsSelectingWorker(true);
        gameScene.setPlayerId(players.indexOf(currentPlayerId));

        int ret = (int) GUIMain.getQueue().take();
        gameScene.setIsSelectingWorker(false);
        return ret;
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) throws InterruptedException {
        Platform.runLater(new GUIGodSelectScene("Choose available Gods", gods.stream().map(GodProposal::getName).collect(Collectors.toList())));
        return (Integer) GUIMain.getQueue().take();
    }

    @Override
    public int[] chooseWorkerInitialPosition() throws InterruptedException {
        gameScene.setIsSelectingCell(true);
        List<Point> points = new ArrayList<>();
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                points.add(new Point(x, y));
            }
        }
        gameScene.getModel().addAllSelectables(points);

        int index = (Integer) GUIMain.getQueue().take();

        Point point = points.get(index);

        return new int[]{point.getX(), point.getY()};
    }

    /**
     * Ask a player for the destination of their next move.
     *
     * @param moves a list of possible moves to choose from
     * @return the Index of the chosen move
     */
    @Override
    public int chooseMove(List<MoveProposal> moves) throws InterruptedException {
        gameScene.setIsSelectingCell(true);
        List<Point> points = moves.stream().map(MoveProposal::getPoint).collect(Collectors.toList());
        gameScene.getModel().addAllSelectables(points);

        int ret = (int) GUIMain.getQueue().take();
        gameScene.setIsSelectingCell(false);

        return ret;
    }

    /**
     * Ask a player for the destination of their next build action,
     * where the next tower block will be built if possible.
     *
     * @param moves a list of options to choose from
     * @return the Index of the chosen option
     */
    @Override
    public int chooseBuild(List<BuildProposal> moves) throws InterruptedException {
        gameScene.setIsSelectingCell(true);
        List<Point> points = moves.stream().map(BuildProposal::getPoint).collect(Collectors.toList());
        gameScene.getModel().addAllSelectables(points);

        int ret = (int) GUIMain.getQueue().take();
        gameScene.setIsSelectingCell(false);

        return ret;
    }

    /**
     * Ask the player if they want to perform a certain action.
     * This handles only the confirm (positive or negative).
     *
     * @param message the text to display
     * @return 0 = no, 1 = yes
     */
    @Override
    public int chooseYesNo(String message) {
        return 0;
    }
}
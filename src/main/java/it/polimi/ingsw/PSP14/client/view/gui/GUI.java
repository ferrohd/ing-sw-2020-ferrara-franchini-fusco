package it.polimi.ingsw.PSP14.client.view.gui;

import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.client.view.cli.UIColor;
import it.polimi.ingsw.PSP14.client.view.gui.scenes.*;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;
import it.polimi.ingsw.PSP14.server.model.board.Point;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class GUI implements UI {
    private String currentPlayerId;
    private final ArrayList<String> players = new ArrayList<>();
    private final HashMap<String, String> gods = new HashMap<>();

    private final GUIGameScene gameScene = new GUIGameScene();

    @Override
    public void registerPlayer(String player) {
        players.add(player);
    }

    @Override
    public void unregisterPlayer(String username) throws InterruptedException {
        unsetWorker(0, username);
        unsetWorker(1, username);
    }

    @Override
    public void setWorker(Point position, int workerId, String playerUsername) throws InterruptedException {
        runLaterSynchronized(() ->
                gameScene.getModel().addWorker(position, workerId, players.indexOf(playerUsername))
        );
    }

    private void unsetWorker(int workerId, String playerUsername) throws InterruptedException {
        runLaterSynchronized(() -> gameScene.getModel().removeWorker(workerId, players.indexOf(playerUsername)));
    }

    @Override
    public void moveWorker(Point newPosition, int workerId, String playerUsername) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        runLaterSynchronized(() -> gameScene.getModel().moveWorker(players.indexOf(playerUsername), workerId, newPosition, latch));
        latch.await();
    }

    @Override
    public void incrementCell(Point position) throws InterruptedException {
        runLaterSynchronized(() -> gameScene.getModel().incrementCell(position));
    }

    @Override
    public void setDome(Point position) throws InterruptedException {
        runLaterSynchronized(() -> gameScene.getModel().putDome(position));
    }

    @Override
    public void update() {

    }

    @Override
    public void showVictory(String winner) throws InterruptedException {
        runLaterSynchronized(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, winner + " won!", ButtonType.OK);
            alert.setTitle("Victory");
            alert.setGraphic(null);
            alert.setHeaderText(null);

            alert.showAndWait();
        });
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
        new Thread(() -> {
            GUIMain.launch(GUIMain.class);
        }).start();
        GUIMain.getQueue().take();

        runLaterSynchronized(new GUIWelcomeScene());
        //runLaterSynchronized(new GUIGameScene());
    }

    @Override
    public void gameStart() throws InterruptedException {
        runLaterSynchronized(gameScene);

        // Display gods info for each player
        for (String player : players) {
            runLaterSynchronized( () ->
                    gameScene.getInfoPanelModel().registerPlayerInfo(player, gods.get(player)));
        }

        //showDemo();
    }

    private void showDemo() throws InterruptedException {
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
            } catch(InterruptedException ignored) {}
        }).start();
    }

    @Override
    public void startWorkerChoice(String player) throws InterruptedException {
        if(player.equals(currentPlayerId)) {
            GUIMain.getInfoText().setText("Choose the worker you would like to move");
        } else {
            GUIMain.getInfoText().setText(player + " is choosing the worker to move");
        }
    }

    @Override
    public void startMove(String player) throws InterruptedException {
        if(player.equals(currentPlayerId)) {
            GUIMain.getInfoText().setText("Choose where to move");
        } else {
            GUIMain.getInfoText().setText(player + " is choosing where to move");
        }
    }

    @Override
    public void startBuild(String player) throws InterruptedException {
        if(player.equals(currentPlayerId)) {
            GUIMain.getInfoText().setText("Choose where to build");
        } else {
            GUIMain.getInfoText().setText(player + " is choosing where to build");
        }
    }

    @Override
    public void startWorkerPlacement(String player) throws InterruptedException {
        if(player.equals(currentPlayerId)) {
            GUIMain.getInfoText().setText("Choose where to place your workers");
        } else {
            GUIMain.getInfoText().setText(player + " is choosing where to place their workers");
        }
    }

    /**
     * Ask the player for how many players should participate in a match.
     *
     * @return the size of the lobby
     */
    @Override
    public int getLobbySize() throws InterruptedException {
        runLaterSynchronized(new GUILobbySizeScene());
        return  (int) GUIMain.getQueue().take();
    }

    /**
     * Display a notification to the player.
     *
     * @param s the content of the notification
     */
    @Override
    public void showNotification(String s) {
        try {
            runLaterSynchronized(new NotificationScene(s));
        } catch(InterruptedException ignored) {}
    }

    /**
     * Prompt the player to provide a username
     *
     * @return the chosen username
     */
    @Override
    public String askUsername() throws InterruptedException {
        runLaterSynchronized(new GUIUsernameScene());
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
        runLaterSynchronized(new GUIGodSelectScene("Choose your God", proposals.stream().map(GodProposal::getName).collect(Collectors.toList())));
        return (Integer) GUIMain.getQueue().take();
    }

    /**
     * Ask the player to choose another player (even themselves) from a list.
     *
     * @param proposals the list of players to choose from
     * @return the index of the chosen player
     */
    @Override
    public int chooseFirstPlayer(List<PlayerProposal> proposals) throws InterruptedException {
        runLaterSynchronized(new GUIFirstPlayerScene(proposals.stream().map(PlayerProposal::getName).collect(Collectors.toList())));
        return (Integer) GUIMain.getQueue().take();
    }

    /**
     * Ask the player to choose a worker from a list
     *
     * @param choices a list of workers to choose from
     * @return the index of the chosen worker
     */
    @Override
    public int chooseWorker(List<Integer> choices) throws InterruptedException {
        gameScene.setIsSelectingCell(true);
        List<Point> points = gameScene.getModel().getAllPlayerWorkers(players.indexOf(currentPlayerId));
        gameScene.setPlayerId(players.indexOf(currentPlayerId));

        runLaterSynchronized(() -> gameScene.getModel().addAllSelectables(points));

        int ret = (int) GUIMain.getQueue().take();
        gameScene.setIsSelectingCell(false);
        runLaterSynchronized(() -> gameScene.getModel().removeAllSelectables());

        return ret;
    }

    @Override
    public int chooseAvailableGods(List<GodProposal> gods) throws InterruptedException {
        runLaterSynchronized(new GUIGodSelectScene("Choose available Gods", gods.stream().map(GodProposal::getName).collect(Collectors.toList())));
        return (Integer) GUIMain.getQueue().take();
    }

    @Override
    public int[] chooseWorkerInitialPosition() throws InterruptedException {
        gameScene.setIsSelectingCell(true);
        List<Point> invalid = gameScene.getModel().getWorkerPositions();
        List<Point> points = new ArrayList<>();
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                Point newPoint = new Point(x, y);
                boolean flag = true;
                for(Point p : invalid)
                    if(p.equals(newPoint)) {
                        flag = false;
                        break;
                    }
                if (flag)
                    points.add(newPoint);
            }
        }
        runLaterSynchronized(() -> gameScene.getModel().addAllSelectables(points));

        int index = (Integer) GUIMain.getQueue().take();

        Point point = points.get(index);

        gameScene.setIsSelectingCell(false);
        runLaterSynchronized(() -> gameScene.getModel().removeAllSelectables());

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

        runLaterSynchronized(() -> gameScene.getModel().addAllSelectables(points));

        int ret = (int) GUIMain.getQueue().take();
        gameScene.setIsSelectingCell(false);
        runLaterSynchronized(() -> gameScene.getModel().removeAllSelectables());

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

        runLaterSynchronized(() -> gameScene.getModel().addAllSelectables(points));

        int ret = (int) GUIMain.getQueue().take();
        gameScene.setIsSelectingCell(false);
        runLaterSynchronized(() -> gameScene.getModel().removeAllSelectables());

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
    public int chooseYesNo(String message) throws InterruptedException {
        runLaterSynchronized(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation Dialog");
            alert.setGraphic(null);
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES){
                System.out.println("Clicked on Yes");
                GUIMain.getQueue().add(1);
            } else {
                System.out.println("Clicked on No");
                GUIMain.getQueue().add(0);
            }
        });
        int ret = (int) GUIMain.getQueue().take();

        return ret;
    }

    @Override
    public void updateGod(String player, String god) {
        gods.put(player, god);
    }

    private void runLaterSynchronized(Runnable runnable) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            runnable.run();
            latch.countDown();
        });
        latch.await();
    }
}
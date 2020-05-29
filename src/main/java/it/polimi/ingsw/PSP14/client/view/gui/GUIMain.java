package it.polimi.ingsw.PSP14.client.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * JavaFX Thread
 */
public class GUIMain extends Application {

    private static final BlockingQueue<Object> queue = new LinkedBlockingDeque<>();
    public static BlockingQueue<Object> getQueue() {
        return queue;
    }

    private static Stage initStage;
    public static Stage getStage() {
        return initStage;
    }

    /**
     * Entry point of JavaFX
     * @param primaryStage First Window created by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        initStage = primaryStage;
        primaryStage.setResizable(false);
        initStage.setTitle("SANTORINI");
        initStage.show();
        queue.add(new Object());
    }

    public static NotificationPane getMainPane() {
        return (NotificationPane) initStage.getScene().getRoot();
    }

    /**
     * Used by other threads to change the scene of the stage (Window)
     * @param scene new scene to be used
     */
    static public void updateScene() {
        initStage.setAlwaysOnTop(true);
        initStage.setAlwaysOnTop(false);
    }
}